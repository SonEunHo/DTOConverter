package dto_converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class DTOConverter {
    public static <F,T> T convert(F from, Class<T> to) {
        try {
            T result = to.newInstance();
            return convert(from, result);
        } catch (InstantiationException | IllegalAccessException e) {
            //use logger
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            //use logger
            return null;
        }
    }

    public static <F,T> T convert(F from, T to) {
        if(Objects.isNull(from) || Objects.isNull(to))
            throw new IllegalArgumentException("convert obejct must not be null");

        Method[] methodsOfFromClass = from.getClass().getDeclaredMethods();

        for(Method method : methodsOfFromClass) {
            if(!method.getName().contains("get"))
                continue;
            Method setter = null;
            try {
                setter = to.getClass().getDeclaredMethod(
                        method.getName().replace("get", "set"),
                        method.getReturnType()
                );
            } catch (NoSuchMethodException e) {
                //use logger
            }

            if(Objects.isNull(setter))
                continue;
            try {
                setter.invoke(to, method.invoke(from));
            } catch (IllegalAccessException | InvocationTargetException e) {
                //use logger
            }
        }

        return to;
    }
}
