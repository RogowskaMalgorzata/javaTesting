package com.example.restservicedemo.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Car {
	
	private long id;
	private String make;
	private String model;	
	private int yop;
	private Person owner;
	
	public Car(String make, String model, int yop) {
		this.make = make;
		this.model = model;
		this.yop = yop;
	}
	
	public Car(long id, String make, String model, int yop) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.yop = yop;
	}
	
	public Car() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYop() {
		return yop;
	}
	public void setYop(int yop) {
		this.yop = yop;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
}
