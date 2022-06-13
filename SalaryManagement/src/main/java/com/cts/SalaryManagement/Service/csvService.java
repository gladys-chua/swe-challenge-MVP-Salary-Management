package com.cts.SalaryManagement.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.SalaryManagement.Helper.csvHelper;
import com.cts.SalaryManagement.Repository.UserRepo;
import com.cts.SalaryManagement.model.User;

@Service
public class csvService {

	@Autowired
	UserRepo userRepo;
	
	public void save(MultipartFile file) {
		try {
			List<User> userList = csvHelper.csvToUsers(file.getInputStream());
			userRepo.saveAll(userList);
			
		} catch (IOException e) {
			throw new RuntimeException("Failed to store csv data: "+e.getMessage());
		}
	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	@Transactional
	public List<User> getFilteredUsers(double minS, double maxS){
		return userRepo.getFirst30FilteredUsers(minS, maxS);
	}
	
	@Transactional
	public void deleteById(String id) {
		userRepo.deleteById(id);
	}
	
	@Transactional
	public Optional<User> findById(String id) {
		return userRepo.findById(id);
	}
	
}
