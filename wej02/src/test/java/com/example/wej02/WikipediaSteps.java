package com.example.wej02;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.wej02.Pages;

public class WikipediaSteps {
	private final Pages pages;

	public WikipediaSteps(Pages pages) {
		this.pages = pages;
	}
	
	@Given("user is on Home page")
    public void userIsOnHomePage() {        
        pages.home().open();        
    }
 
    @When("user clicks Zaloguj się")
    public void userClicksOnLoginLink() {        
        pages.home().clickLoginLink();
    }
 
    @Then("Zaloguj się page is shown")
    public void loginPageIsShown(){
       assertEquals("Zaloguj się – Wikipedia, wolna encyklopedia", pages.login().getTitle());
    }	
    
    @Given("user is on Zaloguj się page")
    public void userIsOnLoginPage() {        
        pages.login().open();        
    }
    
    @When("user gives $login and $password")
    public void userGivesLoginDetails(String login, String password) {        
        pages.login().giveLoginDetails(login, password);
    }
    
    @When("clicks Zaloguj się button")
    public void userLogsIn() {        
        pages.login().logIn();
    }
    
    @Then("Home page is shown")
    public void homePageIsShown(){
       assertEquals("Wikipedia, wolna encyklopedia", pages.home().getTitle());
    }
    
    @When("user searches $name")
    public void searchOlsztyn(String name) {        
        pages.home().search(name);
    }
    
    @Then("Olsztyn page is shown") 
    public void olsztynPageIsShown() {
    	assertEquals("Olsztyn – Wikipedia, wolna encyklopedia", pages.olsztyn().getTitle());
    }
    
    @Given("user is on Olsztyn page")
    public void userIsOnOlsztynPage() {
    	pages.olsztyn().open();
    }
    
    @When("user clicks on Muzea link")
    public void userclicksOnMuseumLink() {
    	pages.olsztyn().clickOnMuseumLink();
    }
    
    @Then("Muzea content is shown")
    public void museumContentIsShown() {
    	assertEquals("https://pl.wikipedia.org/wiki/Olsztyn#Muzea", pages.olsztyn().getCurrentUrl());
    }
}
