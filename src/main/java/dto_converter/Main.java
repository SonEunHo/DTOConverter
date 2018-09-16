package dto_converter;

import dto_converter.entities.Example1;
import dto_converter.entities.Example2;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("hellott world");
        Example1 example1 = new Example1();
        example1.setAge(10);
        example1.setName("name");
        example1.setTestColumn("hello");

        Example2 example2 = DTOConverter.convert(example1, Example2.class);
        System.out.println(example2.toString());
    }
}
