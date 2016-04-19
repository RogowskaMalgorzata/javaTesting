package com.example.wej02.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Home extends WebDriverPage {
	
	public Home(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	private final static String LOGIN_LINK = "Zaloguj się";
	private final static String SEARCH_ID = "searchInput";

	public void open() {
		get("https://pl.wikipedia.org/");
		manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public void clickLoginLink() {
		findElement(By.linkText(LOGIN_LINK)).click();
	}
	
	public void search(String name) {
		WebElement element = findElement(By.id(SEARCH_ID));
		element.sendKeys(name);
		element.sendKeys(Keys.ENTER);
	}
}
