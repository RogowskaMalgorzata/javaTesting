package com.example.messenger;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.mockdemo.app.Messenger;
import com.example.mockdemo.messenger.MessageServiceSimpleImpl;

public class MessengerSteps {
	private Messenger messenger;
	private String server;
	private String message;
	
	@Given("a messenger")
	public void messengerSetup(){
		messenger = new Messenger(new MessageServiceSimpleImpl());
	}
	
	@When("set server to $s and message to $m")
	public void setArguments(String s, String m) {
		server = s;
		message = m;
	}
	
	@Then("sending message should return $result")
	public void shouldSend(int result) {
		assertEquals(result, messenger.sendMessage(server, message));
	}
	
	@Then("testing connection should return $result")
	public void shouldTest(int result) {
		assertEquals(result, messenger.testConnection(server));
	}
}
