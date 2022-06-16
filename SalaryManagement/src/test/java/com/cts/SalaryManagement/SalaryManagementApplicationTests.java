package com.cts.SalaryManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.SalaryManagement.Controller.CSVController;
import com.cts.SalaryManagement.Repository.UserRepository;
import com.cts.SalaryManagement.Service.CSVService;
import com.cts.SalaryManagement.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CSVController.class)
class SalaryManagementApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	CSVService csvService;
	
	User user_1 = new User("e0001","amy","Amy Pang",1000.50);
	User user_2 = new User("e0002","barry","Barry Strong",8000.60);
	User user_3 = new User("e0003","cat","Catherina",600.20);

	@Test
	public void getAllRecords_success() throws Exception {
		List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3));
	    
	    Mockito.when(userRepository.findAll()).thenReturn(users);
	            
	    List<User> resultList = userRepository.findAll();
	    assertThat(resultList.size()).isEqualTo(3);
	    assertThat(resultList.get(0).getName()).isEqualTo(user_1.getName());
	    assertThat(resultList.get(1).getId()).isEqualTo(user_2.getId());
	    assertThat(resultList.get(2).getSalary()).isEqualTo(user_3.getSalary());
	}
}
