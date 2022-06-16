package com.cts.SalaryManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	@NonNull
	private String id;
	@Column(name = "LOGIN")
	@NonNull
	private String login;
	@Column(name = "Name")
	@NonNull
	private String name;
	@Column(name = "SALARY")
	@NonNull
	private double salary;
}
