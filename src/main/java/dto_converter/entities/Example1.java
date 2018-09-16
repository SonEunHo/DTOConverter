package dto_converter.entities;

public class Example1 {
    private String name;
    private int age;
    private String testColumn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTestColumn() {
        return testColumn;
    }

    public void setTestColumn(String testColumn) {
        this.testColumn = testColumn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Example1{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", testColumn='").append(testColumn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
