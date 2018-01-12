package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 生成chrome driver单例
 * @author Administrator
 *https://chromedriver.storage.googleapis.com/index.html?path=2.29/
 */
public class Driver {
	
	private static Logger log = Logger.getLogger(Driver.class);
	private static ChromeDriverService service = null;
	private Driver(){}
	
	//驱动路径
	private static final String DRIVERPATH = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/chrome_driver/chromedriver.exe";
	
	//配置运行驱动
	static{
		try {
			/*Runtime.getRuntime().exec("taskkill /f /t /im chrome.exe");*/
			System.setProperty("webdriver.chrome.driver", DRIVERPATH);
			service = new ChromeDriverService.Builder().usingDriverExecutable(new File(DRIVERPATH)).usingAnyFreePort().build();
			service.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成驱动实例
	 * @return
	 */
	public static ChromeDriver getChromeDriverInstance(String downloadPath){
		ChromeDriver driver = new ChromeDriver(service,setWebDriverProperty(downloadPath));
    	//将隐式等,将等待5秒前抛出异常
    	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    	log.info("获取浏览器实例");
		return driver;
	}
	
	private static DesiredCapabilities setWebDriverProperty(String downloadPath){
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--user-data-dir=C:/Users/"+ System.getProperty("user.name") +"/AppData/Local/Google/Chrome/User Data");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.prompt_for_download", false);
		prefs.put("download.directory_upgrade", true);
		prefs.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", prefs);
        options.addArguments("test-type");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
	}
	
	/**
	 * 关闭浏览器
	 * 停止服务
	 */
	public static void destory(WebDriver driver){
		if(driver != null){
			driver.quit();
		}
		log.info("关闭chrome浏览器");
	}
	
	/*public static void main(String[] a) throws IOException{
		ChromeDriver driver = Driver.getChromeDriverInstance("");
		Driver.destory(driver);
		ChromeDriver driver1 = Driver.getChromeDriverInstance("");
		Driver.destory(driver1);
	}*/
	
}
