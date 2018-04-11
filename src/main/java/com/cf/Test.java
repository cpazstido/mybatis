package com.cf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
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
}
