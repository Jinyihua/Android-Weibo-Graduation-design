package com.jyh.sinaweibo.model;

/**
 * Created by cheng on 2017/1/5.
 * 实体类
 */
public class Student extends Entity
{
    public String name;
    public Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
