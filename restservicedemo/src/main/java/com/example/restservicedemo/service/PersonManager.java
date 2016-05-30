package com.example.restservicedemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.restservicedemo.domain.Person;

public class PersonManager {

	private Connection connection;

	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private static final String CREATE_TABLE_PERSON = "CREATE TABLE Person(p_id bigint GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1), "
			+ "name varchar(20), yob integer, PRIMARY KEY(p_id))";

	private PreparedStatement addPersonStmt;
	private PreparedStatement deleteAllPersonsStmt;
	private PreparedStatement getAllPersonsStmt;
	private PreparedStatement getPersonByIdStmt;
	private PreparedStatement dropPersonTableStmt;

	private Statement statement;

	public PersonManager() {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Person".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_PERSON);

			addPersonStmt = connection
					.prepareStatement("INSERT INTO Person (name, yob) VALUES (?, ?)");
			deleteAllPersonsStmt = connection
					.prepareStatement("DELETE FROM Person");
			getAllPersonsStmt = connection
					.prepareStatement("SELECT p_id, name, yob FROM Person");
			getPersonByIdStmt = connection
					.prepareStatement("SELECT p_id, name, yob FROM Person where p_id = ?");
			dropPersonTableStmt = connection
					.prepareStatement("DROP TABLE Person");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	public void clearPersons() {
		try {
			deleteAllPersonsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addPerson(Person person) {
		int count = 0;
		try {
			addPersonStmt.setString(1, person.getFirstName());
			addPersonStmt.setInt(2, person.getYob());

			count = addPersonStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Person> getAllPersons() {
		List<Person> persons = new ArrayList<Person>();

		try {
			ResultSet rs = getAllPersonsStmt.executeQuery();

			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getLong("p_id"));
				p.setFirstName(rs.getString("name"));
				p.setYob(rs.getInt("yob"));
				persons.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}

	public Person getPerson(Long id) {
		Person p = new Person();
		try {
			getPersonByIdStmt.setLong(1, id);
			ResultSet rs = getPersonByIdStmt.executeQuery();
			
			while (rs.next()) {
				p.setId(rs.getLong("p_id"));
				p.setFirstName(rs.getString("name"));
				p.setYob(rs.getInt("yob"));
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	public void deletePersonTable() {
		try {
			dropPersonTableStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
