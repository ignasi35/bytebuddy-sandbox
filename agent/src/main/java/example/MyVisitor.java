package example;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.AllArguments;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

final class MyVisitor {

  @Advice.OnMethodEnter
  public static void intercept(
      @Advice.Origin Executable methodOrConstructor,
      @Advice.AllArguments Object[] allArguments
  ) {
    var clazz = methodOrConstructor.getDeclaringClass();
    Parameter[] parameters = methodOrConstructor.getParameters();
    var nameArgs = "";
    for (int i = 0; i < parameters.length; i++) {
      nameArgs += parameters[i].getName();
      nameArgs += ": ";
      nameArgs += parameters[i].getType().getName();
      nameArgs += " = ";
      nameArgs += allArguments[i].toString();
      if (i != parameters.length - 1)
        nameArgs += ", ";
    }

    System.out.println("Intercepted: " + clazz.getName() + "#" + methodOrConstructor.getName() + " - Values: " + nameArgs);
  }

}
