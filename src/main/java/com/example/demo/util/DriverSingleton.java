package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 生成chrome driver单例
 * @author Administrator
 *https://chromedriver.storage.googleapis.com/index.html?path=2.29/
 */
public class DriverSingleton {
	
	private static Logger log = Logger.getLogger(DriverSingleton.class);
	private DriverSingleton(){};
	private static ChromeDriver driver = null;
	private static HtmlUnitDriver driverHtml = null;
	private static PhantomJSDriver phantomjs = null;
	
	
	//驱动路径
	private static final String DRIVERPATH = "./chrome_driver/chromedriver.exe";
	
	//配置运行驱动
	/**
	 * 生成驱动实例
	 * @return
	 */
	public static ChromeDriver getInstance(){
		System.setProperty("webdriver.chrome.driver", DRIVERPATH);
		if(driver == null){
			synchronized(DriverSingleton.class){
                if(driver == null){
                	driver = new ChromeDriver(setWebDriverProperty());
                	//将隐式等,将等待5秒前抛出异常
                	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                }
            }
		}
		return driver;
	}
	
	public static HtmlUnitDriver getHtmlInstance(){
		if(driverHtml == null){
			synchronized(DriverSingleton.class){
                if(driverHtml == null){
                	driverHtml = new HtmlUnitDriver(setWebDriverProperty());
                	//将隐式等,将等待5秒前抛出异常
                	driverHtml.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                }
            }
		}
		return driverHtml;
	}
	
	public static PhantomJSDriver getPhantomjsDriver(){
		System.setProperty("phantomjs.binary.path", "./phantomjs-2.1.1-windows/bin/phantomjs.exe");
		if(phantomjs == null){
			synchronized(DriverSingleton.class){
                if(phantomjs == null){
                	phantomjs = new PhantomJSDriver(setWebDriverProperty());
                	//将隐式等,将等待5秒前抛出异常
                	phantomjs.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                }
            }
		}
		return phantomjs;
	}
	
	private static DesiredCapabilities setWebDriverProperty(){
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    	ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.prompt_for_download", false);
		prefs.put("download.directory_upgrade", true);
		prefs.put("download.default_directory", Property.getProjectPath());
		options.setExperimentalOption("prefs", prefs);
        options.addArguments("test-type");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
	}
	
	/**
	 * 关闭浏览器
	 * 停止服务
	 */
	public static void destory(){
		if(driverHtml != null){
			driverHtml.quit();
		}
		if(driver != null){
			driver.quit();
		}
		if(phantomjs != null){
			phantomjs.quit();
		}
		log.info("关闭chrome浏览器");
	}
	
}
