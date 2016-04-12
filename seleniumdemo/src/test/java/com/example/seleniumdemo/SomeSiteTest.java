package com.example.seleniumdemo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SomeSiteTest {

	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void driverSetup() {
		// ChromeDrirver, FireforxDriver, ...
		System.setProperty("webdriver.chrome.driver", "../../../chromeDriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@Test
	public void loginPage(){
		driver.get("http://www.nowedemo.mbank.pl/login.html");
		element = driver.findElement(By.className("btn"));
		
		assertNotNull(element);
	}
	
	@Test
	public void paymentsPage(){
		driver.get("http://www.nowedemo.mbank.pl/login.html");
		
		driver.findElement(By.className("btn")).click();
		driver.findElement(By.className("close-button")).click();
		driver.findElement(By.linkText("Historia")).click();
//		driver.get("http://www.wp.pl/");
//		driver.findElement(By.linkText("Żużel")).click();
//		element = driver.findElement(By.linkText("Statystyki"));
//		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	    assertNotNull(screenshot);
//
//		try {
//			FileUtils.copyFile(screenshot, new File("../../../tmp/wp.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//			assertTrue(false);
//		}
		
	}

	@AfterClass
	public static void cleanp() {
		driver.quit();
	}
}
