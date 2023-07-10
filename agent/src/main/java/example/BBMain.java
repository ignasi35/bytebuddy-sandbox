package example;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class BBMain {
  public static void premain(String arguments, Instrumentation instrumentation) throws IOException {
    new AgentBuilder.Default()
        .with(AgentBuilder.Listener.StreamWriting.toSystemError().withTransformationsOnly())
        .with(AgentBuilder.InstallationListener.StreamWriting.toSystemError())
        .type(
            ElementMatchers.isAnnotatedWith(WithTracking.class)
        )
        .transform((builder, type, classLoader, module, protectionDomain) ->
            builder.method(ElementMatchers.named("copy"))
                .intercept(MethodDelegation.to(ContextualInterceptor.class))
        )
        .transform((builder, type, classLoader, module, protectionDomain) ->
            builder.visit(
                Advice
                    .to(MyVisitor.class)
                    .on(
                        ElementMatchers.isConstructor()
                    )
            )
        )
        .installOn(instrumentation);
  }
}
