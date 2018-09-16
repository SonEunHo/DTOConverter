import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito.*;
import dto_converter.DTOConverter;
import dto_converter.DTOConverter.Callback;
import dto_converter.entities.Example1;
import dto_converter.entities.Example2;

public class DTOConverterTest {
    @Test
    public void convertWithoutCallback() {
        Example1 example1 = new Example1();
        example1.setAge(10);
        example1.setName("name");
        example1.setTestColumn("hello");

        Example2 example2 = DTOConverter.convert(example1, Example2.class);
        assert ((Integer)example1.getAge()).equals(example2.getAge());
        assert example1.getName().equals(example2.getName());
        assert example1.getTestColumn().equals(example2.getTestColumn());

        example2.setAge(-1);
        example2.setName("nothing");
        example2.setTestColumn("nothing column");

        example1 = DTOConverter.convert(example2, example1);
        assert ((Integer)example1.getAge()).equals(example2.getAge());
        assert example1.getName().equals(example2.getName());
        assert example1.getTestColumn().equals(example2.getTestColumn());
    }

    @Test
    public void convertWithCallback() {
        Example1 example1 = new Example1();
        example1.setAge(10);
        example1.setName("name");
        example1.setTestColumn("hello");

        Example2 example2 = DTOConverter.convert(example1, Example2.class, new Callback<Example1, Example2>() {
            @Override
            public void beforeConvert(Example1 from, Example2 to) { }

            @Override
            public void afterConvert(Example1 from, Example2 to) {
                to.setAge(to.getAge()+1);
            }
        });
        assert ((Integer)(example1.getAge()+1)).equals(example2.getAge());
        assert example1.getName().equals(example2.getName());
        assert example1.getTestColumn().equals(example2.getTestColumn());
    }

    @Test
    public void callback() {
        int testAge = 10;
        Example1 example1 = mock(Example1.class);
        Example2 example2 = new Example2();

        DTOConverter.Callback callback = mock(DTOConverter.Callback.class);
        when(example1.getAge()).thenReturn(testAge);

        doNothing().when(callback).beforeConvert(example1, example2);
        doAnswer(invocation -> {
            Example2 e2 = invocation.getArgument(1);

            e2.setAge(e2.getAge()+1);
            return null;
        }).when(callback).afterConvert(example1, example2);

        example2 = DTOConverter.convert(example1, example2, callback);

        assert ((Integer)(example2.getAge()-1)).equals(testAge);
        verify(callback).beforeConvert(example1, example2);
        verify(callback).afterConvert(example1, example2);
    }
}
