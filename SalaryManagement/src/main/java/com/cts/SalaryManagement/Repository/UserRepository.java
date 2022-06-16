package com.cts.SalaryManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.SalaryManagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select u from User u where u.salary>=:minS and u.salary<=:maxS")
	List<User> getFirst30FilteredUsers(@Param("minS") double minS, @Param("maxS") double maxS);
	
	
}
