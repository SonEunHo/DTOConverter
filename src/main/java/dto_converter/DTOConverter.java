package dto_converter;

import java.lang.reflect.Method;
import java.util.Objects;

public class DTOConverter {
    public static <F,T> T convert(F from, Class<T> to) throws Exception {
        T result = to.newInstance();

        return convert(from, result);
    }

    public static <F,T> T convert(F from, T to) throws Exception {
        Method[] methodsOfFromClass = from.getClass().getDeclaredMethods();

        for(Method method : methodsOfFromClass) {
            if(!method.getName().contains("get"))
                continue;

            //필드를 우선으로 보고 같은 타입, 이름의 필드가 있으면 getter, setter 활용할까
            Method setter = to.getClass().getDeclaredMethod(method.getName().replace("get", "set"), method.getReturnType());
            if(Objects.isNull(setter))
                continue;
            setter.invoke(to, method.invoke(from));
        }

        return to;
    }
}
