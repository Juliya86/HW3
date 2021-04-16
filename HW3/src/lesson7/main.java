package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        processing (TestClass.class);
    }
    public static void processing (Class c) throws InvocationTargetException, IllegalAccessException {
        Method () methods = c.getDeclaredMethods();
        List <Method> list = new Arraylist <~> ();
        for (Method o: methods) {
            if (o.isAnnotationPresent (Test.class)) {
                int prio = o.getAnnotation (Test.class).priority ();
                if (prio <1 || prio > 10) throw new RuntimeException("Priority exception1");
                list.add (o);
            }
        }

        list.sort (new Comparator<Method>() {
            @Override
            public int compare (Method o1, Method o2) {
                return o2.getAnnotation (Test.class).priority () - oi.getAnnotation (Test.class).priority();
            }
        });
        Main prosessing ();
    }
}
