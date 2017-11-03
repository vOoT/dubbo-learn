package test;

/**
 * Created by zhuqi on 2017/11/2.
 */
public class User {
    private Long id;

    private String name;

    private int age;

    private String[] propertiy;

    private Class<User> clazz;

    private Sex sex;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String[] getPropertiy() {
        return propertiy;
    }

    public void setPropertiy(String[] propertiy) {
        this.propertiy = propertiy;
    }

    public Class<User> getClazz() {
        return clazz;
    }

    public void setClazz(Class<User> clazz) {
        this.clazz = clazz;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
