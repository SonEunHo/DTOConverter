package dto_converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class DTOConverter {
    public static <F,T> T convert(F from, Class<T> to) {
        return convert(from, to, (Callback<F, T>) null);
    }

    public static <F,T> T convert(F from, Class<T> to, Callback<F, T> callback) {
        try {
            T result = to.newInstance();
            return convert(from, result, callback);
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
        return convert(from, to, null);
    }

    public static <F,T> T convert(F from, T to, Callback<F, T> callback) {
        if(Objects.isNull(from) || Objects.isNull(to))
            throw new IllegalArgumentException("convert obejct must not be null");

        if(Objects.nonNull(callback))
            callback.beforeConvert(from, to);

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

        if(Objects.nonNull(callback))
            callback.afterConvert(from, to);

        return to;
    }

    public interface Callback <F,T> {
        void beforeConvert(F from, T to);
        void afterConvert(F from, T to);
    }
}
