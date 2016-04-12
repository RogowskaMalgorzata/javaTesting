package com.example.messenger;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
	
	@Then("sending message should return $result or $result2")
	public void shouldSend2(int result, int result2) {
		assertThat(messenger.sendMessage(server, message),either(equalTo(result)).or(equalTo(result2)));
	}
	
	@Then("testing connection should return $result")
	public void shouldTest(int result) {
		assertEquals(result, messenger.testConnection(server));
	}
}
