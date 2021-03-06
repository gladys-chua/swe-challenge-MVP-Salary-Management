package com.cts.SalaryManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.SalaryManagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select u from User u where u.salary>=:minSalary and u.salary<=:maxSalary")
	List<User> getFirst30FilteredUsers(@Param("minSalary") double minSalary, @Param("maxSalary") double maxSalary);
	
	
}
