package com.example.restservicedemo.domain;

public class Purchase {
	private Person person;
	private Car car;
	
	public Purchase() {	
	}
	
	public Purchase(Person person, Car car) {
		super();
		this.person = person;
		this.car = car;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	
	
}
