package com.java4;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    /**
     * Optional.of(T t):创建一个Optianal实例，t必须非空；
     * Optional.empty():创建一个空的Optional实例
     * Optional.ofNullable(T t):t可以为null
     */
    @Test
    public void test1(){
        Girl girl = new Girl();
//        girl = null;
        //of(T t):保证t是非空的
        Optional<Girl> optionalGril = Optional.of(girl);
    }

    @Test
    public void test2(){
        Girl girl = new Girl();
        girl = null;
        //ofNullable(T t):t可以为null
        Optional<Girl> optionalGril = Optional.ofNullable(girl);
        System.out.println(optionalGril);
    }



}
