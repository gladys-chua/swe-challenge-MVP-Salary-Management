package com.cts.SalaryManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.SalaryManagement.Exception.EmptyFileUploadException;
import com.cts.SalaryManagement.Helper.CSVHelper;
import com.cts.SalaryManagement.Message.ResponseMessage;
import com.cts.SalaryManagement.Service.CSVService;
import com.cts.SalaryManagement.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CSVController {

	@Autowired
	private CSVService csvService;

	// User Story 1: Upload CSV file to Database
	@PostMapping("/users/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String msg;
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				csvService.save(file);
				msg = "File uploaded successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(msg));
			} catch (EmptyFileUploadException e) {
				msg = e.getMessage();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(msg));
			} catch (Exception e) {
				msg = "Could not upload file: " + file.getOriginalFilename() + "!" + " " + e.getMessage();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(msg));
			} 
		}
		msg = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(msg));
	}

	// User Story 2: To retrieve all users for display
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = csvService.getAllUsers();
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// method for testing
	@GetMapping("/usersTest")
	public List<User> getAllUsersTest() {
		return csvService.getAllUsers();
	}

	// api to filter users from salary range given
	@GetMapping("/users/{minSalary}/{maxSalary}/{offset}/{limit}")
	public ResponseEntity<List<User>> getAllUsers(@PathVariable("minSalary") Double minSalary,
			@PathVariable("maxSalary") Double maxSalary, @PathVariable("offset") Integer offset,
			@PathVariable("limit") Integer limit) {
		try {
			List<User> users = csvService.getFilteredUsers(minSalary, maxSalary);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// api to delete user
	@DeleteMapping("/users/{id}")
	public ResponseEntity<List<User>> deleteUser(@PathVariable("id") String id) {
		try {
			csvService.deleteById(id);
			List<User> users = csvService.getAllUsers();
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// User Story 3: Edit user information
	@GetMapping("/users/update/{id}/{login}/{name}/{salary}")
	public ResponseEntity<User> UpdateUser(@PathVariable("id") String id,
			@PathVariable("login") String login, @PathVariable("name") String name,
			@PathVariable("salary") Double salary) {
		try {
			
			User user=csvService.updateUser(id, login, name, salary);
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
