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
 
    @Then("$title login page is shown")
    public void loginPageIsShown(String title){
       assertEquals(title, pages.login().getTitle());
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
    
    @When("user clicks Dyskusja link")
    public void userclicksDyskusja() {
    	pages.home().clickDiscussionLink();
    }
    
    @Then("Dyskusja page is shown")
    public void discussionPageIsShown(){
    	pages.discussion().isItShown();
    }
    
    @When("user searches $name")
    public void searchOlsztyn(String name) {        
        pages.home().search(name);
    }
    
    @Then("$title olsztyn page is shown") 
    public void olsztynPageIsShown(String title) {
    	assertEquals(title, pages.olsztyn().getTitle());
    }
    
    @Given("user is on Olsztyn page")
    public void userIsOnOlsztynPage() {
    	pages.olsztyn().open();
    }
    
    @When("user clicks on Muzea link")
    public void userclicksOnMuseumLink() {
    	pages.olsztyn().clickOnMuseumLink();
    }
    
    @Then("$url museum page is shown")
    public void museumContentIsShown(String url) {
    	assertEquals(url, pages.olsztyn().getCurrentUrl());
    }
    
    @When("user clicks Wyloguj")
    public void userLogsOut() {
    	pages.home().logOut();
    }
    
    @Then("$title logout page is shown")
    public void logoutPageIsShown(String title) {
    	assertEquals(title, pages.logout().getTitle());
    }
    
    @Then("error is shown")
    public void errorIsShown() {
    	pages.login().showError();
    }
}
