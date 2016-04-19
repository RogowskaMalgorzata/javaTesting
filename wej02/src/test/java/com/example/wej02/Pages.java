package com.example.wej02;

import org.jbehave.web.selenium.WebDriverProvider;

import com.example.wej02.pages.Home;
import com.example.wej02.pages.Olsztyn;
import com.example.wej02.pages.Login;

public class Pages {
	
private WebDriverProvider driverProvider;
	
	//Pages
	private Home home;
	private Login login;
	private Olsztyn olsztyn;


	public Pages(WebDriverProvider driverProvider) {
		super();
		this.driverProvider = driverProvider;
	}

	public Home home() {
		if (home == null) {
			home = new Home(driverProvider);
		}
		return home;
	}
	
	public Login login() {
		if (login == null) {
			login = new Login(driverProvider);
		}
		return login;
	}
	
	public Olsztyn olsztyn(){
		if (olsztyn == null) {
			olsztyn = new Olsztyn(driverProvider);
		}
		return olsztyn;
	}
}
