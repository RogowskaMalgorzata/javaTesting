package com.example.wej02.pages;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Olsztyn extends WebDriverPage {
	
	private final static String MUSEUM_LINK_XPATH = "//*[@id='toc']/ul/li[11]/ul/li[1]/a/span[2]";
	
	private WebDriverProvider driver; 
	
	public Olsztyn(WebDriverProvider driverProvider) {
		super(driverProvider);
		driver = driverProvider;
	}
	
	public void open() {
		get("https://pl.wikipedia.org/wiki/Olsztyn");
		manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public void clickOnMuseumLink() {
		findElement(By.xpath(MUSEUM_LINK_XPATH)).click();
	}
	

}
