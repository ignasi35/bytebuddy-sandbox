package example;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContextualInterceptor {

  // As seen in http://mydailyjava.blogspot.com/2022/02/using-byte-buddy-for-proxy-creation.html
  @RuntimeType
  public static Object intercept(
      @This Object self,
      @Origin Method method,
      @AllArguments Object[] allArguments,
      @SuperMethod Method superMethod) throws Throwable {
    var clazz = self.getClass();

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


    List<String> changes = new ArrayList<String>(args.size());
    for (int i = 0; i < args.size(); i++) {
      String fieldName = fields.get(i);
      String oldValue = clazz.getDeclaredMethod(fieldName).invoke(self).toString();
      String newValue = args.get(i);
      if (!oldValue.equals(newValue)) {
        var s = fieldName;
        s += ": ";
        s += oldValue;
        s += " => ";
        s += newValue;
        s += ",";
        changes.add(s);
      }
    }

    var namedArguments = String.join(", ", changes);
//    System.out.println("Intercepted: " + classDetails + "#" + methodDetails + "\n  Values: " + namedArguments + "\n   Stack: " + stack);

    

    return superMethod.invoke(self, allArguments);
  }

}
