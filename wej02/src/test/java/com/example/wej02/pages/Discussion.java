package com.example.wej02.pages;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Discussion extends WebDriverPage{

	private WebDriver driver; 
	
	public Discussion(WebDriverProvider driverProvider) {
		super(driverProvider);
		driver = driverProvider.get();
	}
	
	public void isItShown() {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("../../tmp/discussionPage.png"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
