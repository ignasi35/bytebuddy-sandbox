package example;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ContextualInterceptor {


//  @RuntimeType
//  static Object intercept(
//      @AllArguments Object[] allArguments,
//      @Origin Class<?> clazz,
//      @Origin Constructor<?> constructor
//  ) throws Exception {
//    return constructor.newInstance(allArguments);
//  }


//  @RuntimeType
//  static Object intercept(
//      @AllArguments Object[] allArguments,
//      @Origin Class<?> clazz,
//      @Origin Constructor<?> constructor
//  ) throws Exception {
//    var result = constructor.newInstance(allArguments);
//    String stack =
//        Arrays.stream(
//                new RuntimeException()
//                    .getStackTrace())
//            // must be at least 3: this interceptor, the decorating class, and the actual
//            // class. But could be longer to gain more context
//            .limit(5)
//            .map(x -> x.getFileName() + ":" + x.getLineNumber())
//            .collect(Collectors.joining(" / "));
//
//    String classDetails = clazz.getName();
//
//    var args =
//        Arrays.stream(allArguments)
//            .map(Object::toString)
//            .collect(Collectors.toList());
//    var fields =
//        Arrays
//            .stream(clazz.getDeclaredFields())
//            .map(Field::getName)
//            .collect(Collectors.toList());
//    List<String> arguments = new ArrayList<String>(args.size());
//    for (int i = 0; i < args.size(); i++) {
//      arguments.add(fields.get(i) + "=" + args.get(i));
//    }
//
//    var namedArguments = String.join(", ", arguments);
//    System.out.println("Intercepted: " + classDetails + "#<init>\n  Values: " + namedArguments + "\n   Stack: " + stack);
//    return result;
//  }


  // This was my attempt but looks wrong. See below.
//  @RuntimeType
//  static Object intercept(
//      @AllArguments Object[] allArguments,
//      @Origin Class<?> clazz,
//      @Origin Method method,
//      @SuperCall Callable<?> zuper
//  ) throws Exception {

  // As seen in http://mydailyjava.blogspot.com/2022/02/using-byte-buddy-for-proxy-creation.html
    @RuntimeType
    public static Object intercept(
        @This Object self,
        @Origin Method method,
        @AllArguments Object[] allArguments,
        @SuperMethod Method superMethod) throws Throwable {
      var clazz  = self.getClass();

    String stack =
        Arrays.stream(
                new RuntimeException()
                    .getStackTrace())
            // must be at least 3: this interceptor, the decorating class, and the actual
            // class. But could be longer to gain more context
            .limit(5)
            .map(x -> x.getFileName() + ":" + x.getLineNumber())
            .collect(Collectors.joining(" / "));

    String classDetails = clazz.getName();
    String methodDetails = method.getName();
    var args =
        Arrays.stream(allArguments)
            .map(Object::toString)
            .collect(Collectors.toList());
    var fields =
        Arrays
            .stream(clazz.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toList());
    List<String> arguments = new ArrayList<String>(args.size());
    for (int i = 0; i < args.size(); i++) {
      arguments.add(fields.get(i) + "=" + args.get(i));
    }


    var namedArguments = String.join(", ", arguments);
    System.out.println("Intercepted: " + classDetails + "#" + methodDetails + "\n  Values: " + namedArguments + "\n   Stack: " + stack);
    return superMethod.invoke(self, allArguments);
//    return zuper.call();
  }

}
