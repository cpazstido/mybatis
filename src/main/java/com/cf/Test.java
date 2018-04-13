package com.cf;

import com.cf.service.Calculator;
import com.cf.service.Impl.CalculatorImpl;
import com.cf.utils.CalculatorHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.resourcesTest("com/cf");
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
