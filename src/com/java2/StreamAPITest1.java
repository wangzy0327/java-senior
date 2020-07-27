package com.java2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Stream的中间操作
public class StreamAPITest1 {

    // 1-筛选与切片
    @Test
    public void test1(){
        List<Employee> list = EmployeeData.getEmployees();
        // filter(Predicate p) --接收Lambda,从流中排除某些元素
        Stream<Employee> stream = list.stream();
        //查询员工表中薪资大于7000的员工信息
        stream.filter( e -> e.getSalary() > 7000).forEach(System.out::println);

        System.out.println();
        // limit(n) — 截断流，使其元素不超过给定数量
        list.stream().limit(3).forEach(System.out::println);

        System.out.println();

        // skip(n) — 跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补
        list.stream().skip(3).forEach(System.out::println);

        System.out.println();
        // distinct() — 筛选，通过流所生成元素的hashCode()和equals()去除重复元素
        list.add(new Employee(1010,"刘强东",40,8000d));
        list.add(new Employee(1010,"刘强东",40,8000d));
        list.add(new Employee(1010,"刘强东",40,8000d));
        list.add(new Employee(1010,"刘强东",40,8000d));

        list.stream().distinct().forEach(System.out::println);

    }

    //映射
    @Test
    public void test2(){
        // map(Function f) — 接收一个函数作为参数，将元素转为其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素
        List<String> list = Arrays.asList("aa","bb","cc","dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

        //练习：获取员工姓名长度大于3的员工的姓名。
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<String> nameStream = employees.stream().map(Employee::getName);
        nameStream.filter(name -> name.length() > 3).forEach(System.out::println);

        System.out.println();

        //练习2：
        Stream<Stream<Character>> streamStream = list.stream().map(StreamAPITest1::fromStringToStream);
        streamStream.forEach(s -> {
            s.forEach(System.out::println);
        });

        System.out.println("___________________flatMap__________________");
        // flatMap(Function f) — 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
        list.stream().flatMap(StreamAPITest1::fromStringToStream).forEach(System.out::println);

    }

    //将字符串中的多个字符构成的集合转换为对应的Stream实例
    public static Stream<Character> fromStringToStream(String str){  //aa
        ArrayList<Character> list = new ArrayList<>();
        for(Character c : str.toCharArray())
            list.add(c);
        return list.stream();
    }


    //3-排序
    @Test
    public void test4(){

        // sorted() — 自然排序
        List<Integer> integers = Arrays.asList(12, 43, 65, 34, 87, 0, -98, 7);
        integers.stream().sorted().forEach(System.out::println);

        //抛异常，原因Employee没有实现Comparable接口
//        List<Employee> employees = EmployeeData.getEmployees();
//        employees.stream().sorted().forEach(System.out::println);

        //sorted(Comparator com) — 定制排序
        List<Employee> employees = EmployeeData.getEmployees();
        employees.stream().sorted((e1,e2) -> {
            int ageValue = Integer.compare(e1.getAge(),e2.getAge());
            if(ageValue != 0)
                return ageValue;
            else
                return -Double.compare(e1.getSalary(),e2.getSalary());
        }).forEach(System.out::println);
    }

    private static void handler(final List<String> list){
        list.stream()
                .parallel()
                .map(str -> str.split("\\s+"))
                .flatMap(strs -> {
                    List<String> strArr = new ArrayList<>();
                    for (String str : strs)
                        strArr.add(str);
                    return strArr.stream();
                }).filter(s -> !s.matches("[0-9]*"))
                .map(s -> s.replaceAll("[^a-z|^A-Z]", ""))
                .map(s -> s.toLowerCase())
                .filter(s -> {
                    s = s.trim();
                    return !s.isEmpty();
                })
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list =  Stream.of("projected eBooks hello","adaboost duanPeiQi Liu","CRLF UTF-8 GB2312").collect(Collectors.toList());
        int paraNum = 256;
        while(scanner.hasNext()) {
            for(int i = 0;;i++) {
                String line = scanner.nextLine();
                if(line.equals("exit"))
                    break;
                if(line  != null && i % paraNum != 0){
                    list.add(line);
                }
                else if(i%paraNum == 0 && line != null){
                    list.add(line);
                    handler(list);
                    list.clear();
                }
            }
        }
    }

    @Test
    public void test5(){
        List<String> list = Arrays.asList("projected eBooks hello","adaboost duanPeiQi Liu","CRLF UTF-8 GB2312");
        list.stream()
                .parallel()
                .map(str -> str.split("\\s+|\\r+|\\n+"))
                .flatMap(strs -> {
                    List<String> strArr = new ArrayList<>();
                    for (String str : strs)
                        strArr.add(str);
                    return strArr.stream();
                }).filter(s -> !s.matches("[0-9]*"))
                .map(s -> s.replaceAll("[^a-z|^A-Z]", ""))
                .map(s -> s.toLowerCase())
                .filter(s -> {
                    s = s.trim();
                    return !s.isEmpty();
                })
                .forEach(System.out::println);
    }
}

