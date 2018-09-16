package dto_converter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class DTOConverter {
    public static <F,T> T convert(F from, Class<T> to) throws Exception{
        Method[] methods = from.getClass().getDeclaredMethods();

        T result = to.newInstance();

        for(Method method : methods) {
            if(!method.getName().contains("get"))
                continue;
            //타입체크 안하는 문제..!
            //필드를 우선으로 보고 같은 타입, 이름의 필드가 있으면 getter, setter 활용할까
            Method setter = to.getDeclaredMethod(method.getName().replace("get", "set"), method.getReturnType());
            if(Objects.isNull(setter))
                continue;
            setter.invoke(result, method.invoke(from));
        }

        return result;
    }
}
