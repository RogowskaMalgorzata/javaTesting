package com.example.restservicedemo;


import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.example.restservicedemo.service.CarManager;
import com.example.restservicedemo.service.PersonManager;


public class PageTest {

	private static WebDriver driver;
	WebElement element;
	private static CarManager cm = new CarManager();
	private static PersonManager pm = new PersonManager();

	@BeforeClass
	public static void driverSetup() {
		// ChromeDrirver, FireforxDriver, ...
		System.setProperty("webdriver.chrome.driver", "/home/PJWSTK/s11803/chromeDriver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@Test
	public void mainPagePerson(){
		driver.get("http://localhost:8080/restservicedemo/index.html");
		driver.findElement(By.id("name")).sendKeys("Henio");
		driver.findElement(By.id("yob")).sendKeys("1899");
		driver.findElement(By.id("add-person")).click();
		
		element = driver.findElement(By.className("m"));
		assertNotNull(element);
	}
	
	@Test
	public void mainPageCar(){
		driver.get("http://localhost:8080/restservicedemo/index.html");
		driver.findElement(By.id("make")).sendKeys("Honda");
		driver.findElement(By.id("model")).sendKeys("Accord");
		driver.findElement(By.id("yop")).sendKeys("1899");
		driver.findElement(By.id("add-car")).click();
		
		element = driver.findElement(By.className("m"));
		assertNotNull(element);	
	}
	
	@AfterClass
	public static void cleanup() {
		cm.deleteCarTable();
		pm.deletePersonTable();
		driver.quit();
	}
}