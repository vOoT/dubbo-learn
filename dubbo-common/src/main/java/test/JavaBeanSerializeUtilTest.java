package test;

import dubbo.JavaBeanAccessor;
import dubbo.JavaBeanDescriptor;
import dubbo.JavaBeanSerializeUtil;

/**
 * Created by zhuqi on 2017/11/2.
 */
public class JavaBeanSerializeUtilTest {

    public static void main(String[] args) {
        test1();
    }


    /**
     * 对象
     */
    public static void test1() {
        User user = new User();
        user.setId(1L);
        user.setAge(44);
        user.setClazz(User.class);
        user.setSex(Sex.MAN);

       // JavaBeanDescriptor serialize = JavaBeanSerializeUtil.serialize(user);

        JavaBeanDescriptor serialize1 = JavaBeanSerializeUtil.serialize(user, JavaBeanAccessor.METHOD);
        System.out.println(serialize1);

       // System.out.println(serialize);


    }
}
