package com.example.springhibernatedemo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "person.all", query = "Select p FROM Person p"),
	@NamedQuery(name = "person.byPesel", query = "Select p from Person p where p.pesel = :pesel")
})
public class Person {
	private Long pId;
	private String pName;
	private String pesel;
	private Set<Candy> candies = new HashSet<Candy>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	@Column(unique = true, nullable = false)
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="person")
	public Set<Candy> getCandies() {
		return candies;
	}
	public void setCandies(Set<Candy> candies) {
		this.candies = candies;
	}
	
	
}
