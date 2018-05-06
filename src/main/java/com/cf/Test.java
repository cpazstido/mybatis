package com.cf;

import com.cf.service.Calculator;
import com.cf.service.Impl.CalculatorImpl;
import com.cf.utils.CalculatorHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
//        Test test = new Test();
//        test.resourcesTest("com/cf");
        String w = "asdf";

        Integer a = 911;
        Integer b = 712;
        System.out.println("before: a="+a+",b="+b);
        swap(a,b);
        System.out.println("after: a="+a+",b="+b);

        int cc = 1;
        int dd = 2;
        System.out.println("before: a="+cc+",b="+dd);
        swap1(cc,dd);
        System.out.println("after: a="+cc+",b="+dd);

        String aa = new String("1");
        String bb = new String("2");
        System.out.println("before: aa="+aa+",bb="+bb);
        swapString(aa,bb);
        System.out.println("after: aa="+aa+",bb="+bb);
    }

    private static void swap1(int num1,int num2){
        int temp = num1;
        num1 = num2;
        num2 = temp;
    }

    private static void swap(Integer num1,Integer num2){
//        Integer temp = num1;
//        num1 = num2;
//        num2 = temp;

        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int temp = num1.intValue();
            field.set(num1,num2);
            field.set(num2,temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void swapString(String num1,String num2){
        String temp = num1;
        temp = num1;
        num1 = num2;
        num2 = temp;
    }



    public void classForName(){
        try {
            Class classs = Class.forName("com.cf.App");
            App app = (App) classs.newInstance();
            app.print("hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resourcesTest(String packages) throws Exception{
        List<URL> urls =  Collections.list(Thread.currentThread().getContextClassLoader().getResources(packages));
        for(URL url:urls){
            System.out.println("===="+url+"====");
            InputStream inputStream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String line; (line = reader.readLine()) != null;) {
                if(line.contains(".")){
                    System.out.println(line);
                }else{
                    resourcesTest(packages+"/"+line);
                }

            }
        }
    }

    public void proxyTest(){
        String a = new String("a");
        a=null;
        System.gc();
        System.out.println(a);
        CalculatorImpl calculatorImpl = new CalculatorImpl();
        CalculatorHandler calculatorHandler = new CalculatorHandler(calculatorImpl);
        Calculator calculator = (Calculator) Proxy.newProxyInstance(calculatorImpl.getClass().getClassLoader(),calculatorImpl.getClass().getInterfaces(),calculatorHandler);
        System.out.println(calculator.add(1,2));
        System.out.println(calculator.minus(1, 2));
    }
}
