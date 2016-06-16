package com.example.springhibernatedemo.service;

import java.util.List;
import java.util.Set;

import com.example.springhibernatedemo.domain.Candy;
import com.example.springhibernatedemo.domain.Person;


public interface SellingManager {
	void addPerson(Person person);
	List<Person> getAllPersons();
	void deletePerson(Person person);
	Person findPersonByPesel(String pesel);
	
	Long addCandy(Candy candy);
	List<Candy> getAvailableCandies();
	void eatCandy(Person person, Candy candy);
	Candy findCandyById(Long id);
	Set<Candy> getCandiesByPerson(Person person);
	void buyCandy(Long personId, Long candyId);

}
