package com.java2;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 一、构造器引用
 *     和方法引用类似，函数式接口的抽象方法的形参列表和构造器的形参列表一致
 *     抽象方法的返回值类型即为构造器所属类的类型
 *
 *
 * 二、数组引用
 *      大家可以把数组看做是一个特殊的类，则写法与构造器引用一致
 *
 */
public class ConstructorRefTest {
    //构造器引用
    //Supplier中的T get()
    //Employee的空参构造器：Employee()
    @Test
    public void test1(){
        Supplier<Employee> sup1 = () -> new Employee();
        System.out.println(sup1.get());
        System.out.println("*****************************");
        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }

    //Function中的R apply(T t)
    //Employee的构造器：Employee(id)
    @Test
    public void test2(){
        Function<Integer,Employee> func1 = id -> new Employee(id);
        System.out.println(func1.apply(12));
        System.out.println("*****************************");
        Function<Integer,Employee> func2 = Employee::new;
        System.out.println(func2.apply(12));
    }

    //BiFunction 中的R apply（T t,U u）
    @Test
    public void test3(){
        BiFunction<Integer,String,Employee> func1 = (id,name) -> new Employee(id,name);
        System.out.println(func1.apply(1001,"Tom"));
        System.out.println("*****************************");
        BiFunction<Integer,String,Employee> func2 = Employee :: new;
        System.out.println(func2.apply(1002,"Tom"));

    }

    //数组引用
    //Function中的R apply(T t)
    @Test
    public void test4(){
        Function<Integer,String[]> func1 = length -> new String[length];
        System.out.println(Arrays.toString(func1.apply(5)));
        Function<Integer,String[]> func2 = String[] :: new;
        System.out.println(Arrays.toString(func2.apply(5)));
    }

    @Test
    public void test5(){
        List<Integer> togethor = Stream.of(Arrays.asList(1,2),Arrays.asList(3,4)).
                flatMap(numbers -> numbers.stream()).
                collect(Collectors.toList());
        togethor.stream().forEach(System.out::println);
    }

}
