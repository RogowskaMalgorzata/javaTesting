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


public class TabiTest {

	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void driverSetup() {
		// ChromeDrirver, FireforxDriver, ...
		System.setProperty("webdriver.chrome.driver", "E:/projekty/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@Test
	public void mainPage(){
		driver.get("http://localhost:3000/#/");
		element = driver.findElement(By.id("topPlaces"));
		
		assertNotNull(element);
	}
	
	@Test
	public void registerPage(){
		driver.get("http://localhost:3000/#/");
		
		driver.findElement(By.id("signIn")).click();
		driver.findElement(By.className("registerLink")).click();
		driver.findElement(By.id("loginR")).sendKeys("nowyLogin");
		driver.findElement(By.id("email")).sendKeys("nowyLogin@test.com");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.id("repeatPassword")).sendKeys("password");
		driver.findElement(By.id("registerButton")).click();
	
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("../../../tmp/tabi_registered.png"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void loginPage() {
		driver.get("http://localhost:3000/#/");
		
		driver.findElement(By.id("signIn")).click();
		driver.findElement(By.id("login")).sendKeys("nowyLogin");
		driver.findElement(By.id("passwordL")).sendKeys("password");
		driver.findElement(By.id("signInButton")).click();
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("../../../tmp/tabi_loggedIn.png"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void tokyoPage() {
		driver.get("http://localhost:3000/#/place/Tokyo");
		
		element = driver.findElement(By.cssSelector("ul > li:nth-of-type(3)"));
		assertNotNull(element);
	}
	
	@AfterClass
	public static void cleanp() {
		driver.quit();
	}
}
