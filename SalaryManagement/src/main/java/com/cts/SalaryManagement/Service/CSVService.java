package com.cts.SalaryManagement.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.ormlearn.model.Country;
import com.cts.SalaryManagement.Exception.EmptyFileUploadException;
import com.cts.SalaryManagement.Helper.CSVHelper;
import com.cts.SalaryManagement.Repository.UserRepository;
import com.cts.SalaryManagement.model.User;

@Service
public class CSVService {

	@Autowired
	private UserRepository userRepo;
	
	public void save(MultipartFile file) {
		try {
			List<User> userList = CSVHelper.csvToUsers(file.getInputStream());
			if (userList.isEmpty()) {
				throw new EmptyFileUploadException("Empty file detected: "+ file.getOriginalFilename() + "! Please upload file with entries.");				
			}
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
	public User updateUser(String id, String login, String name, Double salary) {
		Optional<User> result = userRepo.findById(id);
		User user = null;
		if (result.isPresent()) {
			user = result.get();
			user.setLogin(login);
			user.setName(name);
			user.setSalary(salary);
			userRepo.save(user);
		} 
		return user;
		
	}
	
}
