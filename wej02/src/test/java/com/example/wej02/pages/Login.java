package com.example.wej02.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class Login extends WebDriverPage{
	
	private final static String LOGIN_ID = "wpName1";
	private final static String PASS_ID = "wpPassword1";
	private final static String LOGIN_BUTTON_ID = "wpLoginAttempt";
	
	public Login(WebDriverProvider driverProvider) {
		super(driverProvider);
	}
	
	public void open() {
		get("https://pl.wikipedia.org/w/index.php?title=Specjalna:Zaloguj&returnto=Wikipedia%3AStrona+g%C5%82%C3%B3wna");
		manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public void giveLoginDetails(String login, String password) {
		findElement(By.id(LOGIN_ID)).sendKeys(login);
		findElement(By.id(PASS_ID)).sendKeys(password);
	}
	
	public void logIn() {
		findElement(By.id(LOGIN_BUTTON_ID)).click();
	}
}
