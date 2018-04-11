package com.cf;

import com.cf.service.Calculator;
import com.cf.service.Impl.CalculatorImpl;
import com.cf.utils.CalculatorHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class ProxyTest {
    @Test
    public void dynamicProxyTest(){
        Object obj = null;
        CalculatorImpl calculatorImpl = new CalculatorImpl();
        CalculatorHandler calculatorHandler = new CalculatorHandler(calculatorImpl);
        Calculator calculator = (Calculator)Proxy.newProxyInstance(calculatorImpl.getClass().getClassLoader(),calculatorImpl.getClass().getInterfaces(),calculatorHandler);
        System.out.println(calculator.add(1,2));
        System.out.println(calculator.minus(1, 2));
    }
}
