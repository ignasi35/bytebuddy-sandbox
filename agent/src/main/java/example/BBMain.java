package example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeVariableToken;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.Transformer;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.security.ProtectionDomain;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class BBMain {
  public static void premain(String arguments, Instrumentation instrumentation) throws IOException {
    System.out.println("in the agent");

    new AgentBuilder.Default()
        .type(ElementMatchers.isAnnotatedWith(WithTracking.class))
        .transform((builder, type, classLoader, module, protectionDomain) ->
            builder.method(ElementMatchers.named("copy"))
                .intercept(MethodDelegation.to(ContextualCopyInterceptor.class))
        ).installOn(instrumentation);

    new AgentBuilder.Default()
        .type(ElementMatchers.nameEndsWith("Timed"))
        .transform((builder, type, classLoader, module, protectionDomain) ->
            builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TimingInterceptor.class))
        ).installOn(instrumentation);


  }

}
