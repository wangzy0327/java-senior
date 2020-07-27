package com.java2;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Stream的终止操作
public class StreamAPITest2 {

    @Test
    public void test1(){

        List<Employee> employees = EmployeeData.getEmployees();
        // allMatch(Predicate p) — 检查是够匹配所有元素
        // 练习：是否所有的员工的年龄是否大于18
        boolean allMatch = employees.stream().allMatch(e -> e.getAge() > 18);
        System.out.println(allMatch);

        // anyMatch(Predicate p) — 检查是否至少匹配一个元素
        //  练习：是否存在员工的工资大于10000
        boolean anyMatch = employees.stream().anyMatch(e -> e.getSalary() > 10000);
        System.out.println(anyMatch);

        // noneMatch(Predicate p) — 检查是否没有匹配的元素。
        // 练习： 是否存在员工姓"雷"
        boolean noneMatch = employees.stream().noneMatch(e -> e.getName().startsWith("雷"));
        System.out.println(noneMatch);
        
        //findFirst — 返回第一个元素
        Optional<Employee> first = employees.stream().findFirst();
        System.out.println(first);

        //findAny — 返回当前流中的任意一个
        Optional<Employee> any = employees.parallelStream().findAny();
        System.out.println(any);

    }

    @Test
    public void test2(){
        List<Employee> employees = EmployeeData.getEmployees();
        //count — 返回流中的元素的总个数
        long count = employees.stream().filter(e -> e.getSalary() > 5000).count();
        System.out.println(count);

        //max(Comparator c) — 返回流中的最大值
//        练习：返回最高的工资
        Stream<Double> salaryStream = employees.stream().map(e -> e.getSalary());
        Optional<Double> max  = salaryStream.max(Double::compare);
        System.out.println(max);

        //min(Comparator c) - 返回流中的最小值
        //练习：返回最低工资的员工
        Optional<Employee> min = employees.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(min);

        System.out.println();
        System.out.println("_____________Stream流的遍历操作___________");
        //forEach(Comsumer c) - 内部迭代  遍历流中的元素
        employees.stream().forEach(System.out::println);

        System.out.println("_____________集合的遍历操作___________");
        //使用集合的遍历操作
        employees.forEach(System.out::println);
    }

    @Test
    public void test3(){
        //reduce(T identity,BinaryOperator)— 可以将流中元素反复结合起来，得到一个值。返回
        // 练习1：计算1-10的自然数的和
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        //reduce(BinaryOperator) — 可以将流中元素反复结合起来，得到一个值，返回Optional<T>
        //练习2：计算公司所有员工工资的总和

        List<Employee> employees = EmployeeData.getEmployees();
        Stream<Double> salaryStream = employees.stream().map(Employee::getSalary);
//        Optional<Double> salarySum = salaryStream.reduce((d1, d2) -> d1 + d2);
        Optional<Double> salarySum = salaryStream.reduce(Double::sum);
        System.out.println(salarySum);

    }

    @Test
    public void test4(){
        //收集
        //collect(Collector c) — 将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法

        //练习1：查找工资大于6000的员工，结果返回一个List或Set

        List<Employee> employees = EmployeeData.getEmployees();
        List<Employee> employeeList = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toList());
        employeeList.forEach(System.out::println);

        System.out.println();
        Set<Employee> employeeSet = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toSet());
        employeeSet.forEach(System.out::println);

    }

    @Test
    public void test5(){
        List<Employee> employees = EmployeeData.getEmployees();
        Map<String, Double> collect = employees.parallelStream().collect(Collectors.toMap(e -> e.getName(), e -> e.getSalary()));
        for(Map.Entry<String,Double> entry:collect.entrySet())
            System.out.println(entry.getKey()+" : "+entry.getValue());
        employees.add(new Employee(1008,"扎克伯格",35,2500.38));
        employees.add(new Employee(1008,"扎克伯格",35,2500.38));

        Set<Employee> set = employees.stream().distinct().collect(Collectors.toSet());
        Map<String,Double> collect2 = new HashMap<>();
        Double salary = employees.parallelStream()
                .filter(e->e.getName().equals("扎克伯格"))
                .map(e->e.getSalary())
                .reduce(0.0d,Double::sum);
        System.out.println(salary);

    }

}
