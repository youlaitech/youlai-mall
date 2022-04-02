package com.youlai.laboratory.spring;

import java.util.Objects;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/2/18 0018 20:50
 */
//@Component
public class Bean {

    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Bean() {
    }

    public Bean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return age == bean.age && Objects.equals(name, bean.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
