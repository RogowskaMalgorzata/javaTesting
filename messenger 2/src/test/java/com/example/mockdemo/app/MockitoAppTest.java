package com.example.mockdemo.app;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class MockitoAppTest {
	private static final String VALID_SERVER = "inf.ug.edu.pl";
	private static final String INVALID_SERVER = "inf.ug.edu.eu";

	private static final String VALID_MESSAGE = "some message";
	private static final String INVALID_MESSAGE = "ab";
	
	private Messenger messenger;
	private MessageService mock;
	
	@Before
	public void setUP() {
		 mock = Mockito.mock(MessageService.class);
		 messenger = new Messenger(mock);
	}

	@Test
	public void checkTestConnection() {
		when(mock.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
		assertThat(messenger.testConnection(VALID_SERVER), equalTo(0));
		
		when(mock.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		assertThat(messenger.testConnection(INVALID_SERVER), equalTo(1));
		

		verify(mock, atLeastOnce()).checkConnection(VALID_SERVER);
		verify(mock, atMost(1)).checkConnection(INVALID_SERVER);
	}
	
	@Test
	public void checkSendMessage() throws MalformedRecipientException {
		when(mock.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		assertThat(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE),either(equalTo(0)).or(equalTo(1)));
		
		when(mock.send(INVALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		assertEquals(1, messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE));
		
		when(mock.send(VALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		assertEquals(2, messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE));
		
		verify(mock, atLeastOnce()).send(VALID_SERVER, VALID_MESSAGE);
		verify(mock).send(INVALID_SERVER, VALID_MESSAGE);
		verify(mock, atLeastOnce()).send(VALID_SERVER, INVALID_MESSAGE);
	
	}
	
	@Test
	public void sendingConnectionStatus() {
		ArgumentCaptor<String> capturedServer = ArgumentCaptor.forClass(String.class);
		
		when(mock.checkConnection(capturedServer.capture())).thenReturn(ConnectionStatus.FAILURE);
		assertEquals(1, messenger.testConnection(INVALID_SERVER));
		assertEquals(INVALID_SERVER, capturedServer.getValue());
		
		verify(mock).checkConnection(INVALID_SERVER);
	}
	
	@After
	public void validate() {
	    validateMockitoUsage();
	}
	
}
