import org.junit.Test;

import dto_converter.DTOConverter;
import dto_converter.entities.Example1;
import dto_converter.entities.Example2;

public class DTOConverterTest {
    @Test
    public void testConvertWithoutCallback() {
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
}
