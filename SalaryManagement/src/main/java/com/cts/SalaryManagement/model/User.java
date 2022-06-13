package com.cts.SalaryManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
public class User {

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "LOGIN")
	private String login;
	@Column(name = "Name")
	private String name;
	@Column(name = "SALARY")
	private double salary;
}
