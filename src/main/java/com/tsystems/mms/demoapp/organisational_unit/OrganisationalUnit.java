package com.tsystems.mms.demoapp.organisational_unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tsystems.mms.demoapp.user.User;

@Entity
@Table(name = "organisational_unit")
public class OrganisationalUnit implements Serializable {

	private static final long serialVersionUID = 4218482171321243259L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	protected Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "organisationalUnit")
	private List<User> users = new ArrayList<User>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
