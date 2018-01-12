package com.example.demo;

import com.example.demo.util.Driver;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by liuxing on 2018/1/8.
 */
public class TestDriver {

    @Test
    public void demo01(){
        //System.out.println(System.getProperty("user.dir").replaceAll("\\\\", "/"));
        ChromeDriver chromeDriverInstance = Driver.getChromeDriverInstance("");
        chromeDriverInstance.get("http://www.baidu.com");
        Driver.destory(chromeDriverInstance);
    }

}
