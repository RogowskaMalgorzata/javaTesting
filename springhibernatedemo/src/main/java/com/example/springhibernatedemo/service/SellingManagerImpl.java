package com.example.springhibernatedemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.springhibernatedemo.domain.Candy;
import com.example.springhibernatedemo.domain.Person;

@Component
@Transactional
public class SellingManagerImpl implements SellingManager{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addPerson(Person person) {
		person.setpId(null);
		sessionFactory.getCurrentSession().persist(person);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPersons() {
		return sessionFactory.getCurrentSession().getNamedQuery("person.all").list();
	}

	@Override
	public void deletePerson(Person person) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getpId());
		
		for (Candy candy : person.getCandies()) {
			candy.setSold(false);
			sessionFactory.getCurrentSession().update(candy);
		}
		sessionFactory.getCurrentSession().delete(person);	
	}

	@Override
	public Person findPersonByPesel(String pesel) {
		return (Person) sessionFactory.getCurrentSession().getNamedQuery("person.byPesel")
				.setString("pesel", pesel).uniqueResult();
	}

	@Override
	public Long addCandy(Candy candy) {
		candy.setcId(null);
		return (Long) sessionFactory.getCurrentSession().save(candy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Candy> getAvailableCandies() {
		return sessionFactory.getCurrentSession().getNamedQuery("candy.unsold").list();
	}

	@Override
	public void eatCandy(Person person, Candy candy) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getpId());
		candy = (Candy) sessionFactory.getCurrentSession().get(Candy.class,
				candy.getcId());

		Candy toBeEaten = null;
		
		for (Candy aCandy : person.getCandies())
			if (aCandy.getcId().compareTo(candy.getcId()) == 0) {
				toBeEaten = aCandy;
				break;
			}

		if (toBeEaten != null)
			person.getCandies().remove(toBeEaten);

		sessionFactory.getCurrentSession().delete(candy);
	}

	@Override
	public Candy findCandyById(Long id) {
		return (Candy) sessionFactory.getCurrentSession().get(Candy.class, id);
	}

	@Override
	public List<Candy> getCandiesByPerson(Person person) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getpId());
		List<Candy> candies = new ArrayList<Candy>(person.getCandies());
		return candies;
	}

	@Override
	public void buyCandy(Long personId, Long candyId) {
		Person person = (Person) sessionFactory.getCurrentSession().get(
				Person.class, personId);
		Candy candy = (Candy) sessionFactory.getCurrentSession()
				.get(Candy.class, candyId);
		candy.setSold(true);
		person.getCandies().add(candy);
		
	}

}
