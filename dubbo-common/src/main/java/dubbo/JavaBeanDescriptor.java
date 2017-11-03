/*
 * Copyright 1999-2012 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dubbo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:gang.lvg@taobao.com">kimi</a>
 *         java bean 描述对象，实现 序列化和迭代接口
 */
public final class JavaBeanDescriptor implements Serializable, Iterable<Map.Entry<Object, Object>> {

    public static final int TYPE_CLASS = 1;  //类类型
    public static final int TYPE_ENUM = 2;  //枚举类型
    public static final int TYPE_COLLECTION = 3; //集合类型
    public static final int TYPE_MAP = 4; //map 类型
    public static final int TYPE_ARRAY = 5; //数组类型
    /**
     * @see com.alibaba.dubbo.common.utils.ReflectUtils#isPrimitive(Class)
     */
    public static final int TYPE_PRIMITIVE = 6;
    public static final int TYPE_BEAN = 7;
    private static final long serialVersionUID = -8505586483570518029L;
    private static final String ENUM_PROPERTY_NAME = "name";

    private static final String CLASS_PROPERTY_NAME = "name";

    private static final String PRIMITIVE_PROPERTY_VALUE = "value";

    /**
     * Used to define a type is valid.
     * 验证有效类型，因为从上面得知 有7种自定义类型，值最大为TYPE_BEAN
     *
     * @see #isValidType(int)
     */
    private static final int TYPE_MAX = TYPE_BEAN;

    /**
     * Used to define a type is valid.
     * 验证有效类型，因为从上面得知 有7种自定义类型，值最小为TYPE_CLASS
     *
     * @see #isValidType(int)
     */
    private static final int TYPE_MIN = TYPE_CLASS;

    private String className;

    private int type;

    private Map<Object, Object> properties = new LinkedHashMap<Object, Object>();

    @Override
    public String toString() {
        return "JavaBeanDescriptor{" +
                "className='" + className + '\'' +
                ", type=" + type +
                ", properties=" + properties +
                '}';
    }

    public JavaBeanDescriptor() {
    }


    /**
     * 传入className 和 type 构造 JavaBeanDescriptor ，验证不通过抛异常
     *
     * @param className
     * @param type
     */
    public JavaBeanDescriptor(String className, int type) {
        notEmpty(className, "class name is empty");
        if (!isValidType(type)) {
            throw new IllegalArgumentException(
                    new StringBuilder(16).append("type [ ")
                            .append(type).append(" ] is unsupported").toString());
        }

        this.className = className;
        this.type = type;
    }


    /**
     * 传入类型判断
     */
    public boolean isClassType() {
        return TYPE_CLASS == type;
    }

    public boolean isEnumType() {
        return TYPE_ENUM == type;
    }

    public boolean isCollectionType() {
        return TYPE_COLLECTION == type;
    }

    public boolean isMapType() {
        return TYPE_MAP == type;
    }

    public boolean isArrayType() {
        return TYPE_ARRAY == type;
    }

    public boolean isPrimitiveType() {
        return TYPE_PRIMITIVE == type;
    }

    public boolean isBeanType() {
        return TYPE_BEAN == type;
    }
    ///////////////////////////////////////////////

    /**
     * getter setter 方法
     */

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    ///////////////////////////////////////////////


    /**
     * 添加参数
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    public Object setProperty(Object propertyName, Object propertyValue) {
        notNull(propertyName, "Property name is null");

        Object oldValue = properties.put(propertyName, propertyValue);
        return oldValue;
    }


    /**
     * 首先该描述是描述枚举类型，否则抛异常
     * 接着通过setProperty ，把名称name 放入 map 里
     *
     * @param name
     * @return
     */
    public String setEnumNameProperty(String name) {
        if (isEnumType()) {
            Object result = setProperty(ENUM_PROPERTY_NAME, name);
            return result == null ? null : result.toString();
        }
        throw new IllegalStateException("The instance is not a enum wrapper");
    }


    /**
     * 获取枚举参数名，首先保证该java bean 描述的是枚举 ,否则抛异常
     * 可以返回null
     *
     * @return
     */
    public String getEnumPropertyName() {
        if (isEnumType()) {
            Object result = getProperty(ENUM_PROPERTY_NAME).toString();
            return result == null ? null : result.toString();
        }
        throw new IllegalStateException("The instance is not a enum wrapper");
    }


    /**
     * 首先该描述是描述类类型，否则抛异常
     * 接着通过setProperty ，把名称name 放入 map 里
     *
     * @param name
     * @return
     */
    public String setClassNameProperty(String name) {
        if (isClassType()) {
            Object result = setProperty(CLASS_PROPERTY_NAME, name);
            return result == null ? null : result.toString();
        }
        throw new IllegalStateException("The instance is not a class wrapper");
    }


    /**
     * 获取类类型参数名，首先保证该java bean 描述的是类类型 ,否则抛异常
     * 可以返回null
     *
     * @return
     */
    public String getClassNameProperty() {
        if (isClassType()) {
            Object result = getProperty(CLASS_PROPERTY_NAME);
            return result == null ? null : result.toString();
        }
        throw new IllegalStateException("The instance is not a class wrapper");
    }


    /**
     * 首先该描述是原生类型，否则抛异常
     * 接着通过setProperty ，把原生类型 放入 map 里
     *
     * @param primitiveValue
     * @return
     */
    public Object setPrimitiveProperty(Object primitiveValue) {
        if (isPrimitiveType()) {
            return setProperty(PRIMITIVE_PROPERTY_VALUE, primitiveValue);
        }
        throw new IllegalStateException("The instance is not a primitive type wrapper");
    }

    /**
     * 获取原生类型参数
     *
     * @return
     */
    public Object getPrimitiveProperty() {
        if (isPrimitiveType()) {
            return getProperty(PRIMITIVE_PROPERTY_VALUE);
        }
        throw new IllegalStateException("The instance is not a primitive type wrapper");
    }


    /**
     * 通过参数名获取参数 参数名 ：PRIMITIVE_PROPERTY_VALUE 、CLASS_PROPERTY_NAME、ENUM_PROPERTY_NAME  map 方法封装
     *
     * @param propertyName
     * @return
     */
    public Object getProperty(Object propertyName) {
        notNull(propertyName, "Property name is null");
        Object propertyValue = properties.get(propertyName);
        return propertyValue;
    }

    /**
     * 是否包含该参数 map 方法封装
     *
     * @param propertyName
     * @return
     */
    public boolean containsProperty(Object propertyName) {
        notNull(propertyName, "Property name is null");
        return properties.containsKey(propertyName);
    }


    /**
     * 得到迭代器
     *
     * @return
     */
    public Iterator<Map.Entry<Object, Object>> iterator() {
        return properties.entrySet().iterator();
    }


    /**
     * 参数大小
     *
     * @return
     */
    public int propertySize() {
        return properties.size();
    }

    /**
     * 验证类型 1-7
     *
     * @param type
     * @return
     */
    private boolean isValidType(int type) {
        return TYPE_MIN <= type && type <= TYPE_MAX;
    }

    /**
     * 判断不为 null,否则抛异常message
     *
     * @param obj
     * @param message
     */
    private void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * 如果 string 为空，抛异常message
     *
     * @param string
     * @param message
     */
    private void notEmpty(String string, String message) {
        if (isEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断字符串是否空
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }
}
