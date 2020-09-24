package demo;

import java.io.Serializable;

/**
 * @Description
 * @Author 74716
 * @Date 2019/11/23 12:42
 **/
public class DemoObj implements Serializable
{
    private String name;
    private int age;

    public DemoObj() {
    }

    public DemoObj(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"age\":")
                .append(age);
        sb.append('}');
        return sb.toString();
    }
}
