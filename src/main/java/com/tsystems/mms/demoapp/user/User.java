package com.tsystems.mms.demoapp.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tsystems.mms.demoapp.organisational_unit.OrganisationalUnit;

import java.io.Serializable;

@Entity
@Table(name = "demo_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1715994813284718249L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	protected Long id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "sur_name", nullable = false)
	private String surName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private GenderEnum gender;

	@ManyToOne
	@JoinColumn(name = "organisational_unit_id")
	private OrganisationalUnit organisationalUnit;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	
	

}
