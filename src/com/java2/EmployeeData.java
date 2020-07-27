package com.java2;

import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
    public static List<Employee> getEmployees(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1001,"马化腾",34,6000.38));
        list.add(new Employee(1002,"马云",12,9600.38));
        list.add(new Employee(1003,"刘强东",33,6700.38));
        list.add(new Employee(1004,"雷军",26,7600.38));
        list.add(new Employee(1005,"李彦宏",65,6700.38));
        list.add(new Employee(1006,"比尔盖茨",42,4500.38));
        list.add(new Employee(1007,"任正非",26,8600.38));
        list.add(new Employee(1008,"扎克伯格",35,2500.38));
        return list;
    }
}
