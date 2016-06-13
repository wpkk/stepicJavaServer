package com.wpkk.stepicJavaServer06.resources1;

public class TestResource1 {
    private String name;
    private int age;

    public TestResource1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public TestResource1() {
        this.name = "";
        this.age = 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
