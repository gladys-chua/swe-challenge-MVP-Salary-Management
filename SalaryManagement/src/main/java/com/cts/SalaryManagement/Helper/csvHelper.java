package com.cts.SalaryManagement.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.cts.SalaryManagement.model.User;

public class csvHelper {
	
	public static String TYPE = "text/csv";
	static String[] headers = {"id","login","name","salary"};
	
	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<User> csvToUsers(InputStream inputStream) {
		try (
				BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			) {
			List<User> users = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for(CSVRecord r: csvRecords) {
				User u = new User();
				u.setId(r.get("id"));
				u.setName(r.get("name"));
				u.setLogin(r.get("login"));
				u.setSalary(Double.parseDouble(r.get("salary")));
				users.add(u);
			}
			return users;
			
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV file: "+ e.getMessage());
		}
	}
}
