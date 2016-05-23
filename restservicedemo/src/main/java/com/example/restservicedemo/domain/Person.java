package com.example.restservicedemo.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	
	private long id;
	
	private String firstName;
	private int yob;
	
	public Person() {
	}

	public Person(String firstName, int yob) {
		this.firstName = firstName;
		this.yob = yob;
	}
	
	public Person(long id, String firstName, int yob) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.yob = yob;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getYob() {
		return yob;
	}
	public void setYob(int yob) {
		this.yob = yob;
	}
}
