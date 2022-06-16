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

import com.cts.SalaryManagement.Exception.IncorrectNumberOfColumnsException;
import com.cts.SalaryManagement.Exception.SalaryLessThanZeroException;
import com.cts.SalaryManagement.model.User;

public class CSVHelper {

	private static String TYPE = "text/csv";
	private final static String[] headers = { "id", "login", "name", "salary" };

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
			for(CSVRecord record: csvRecords) {
				String user_id = record.get("id");
				Double user_salary = Double.parseDouble(record.get("salary"));
				if (user_id.startsWith("#")) {
					continue;
				} else if(record.size() != headers.length) {
					throw new IncorrectNumberOfColumnsException("Too many/few columns are detected!");
				} else if(user_salary < 0 ) {
					throw new SalaryLessThanZeroException("Data row(s) with salary less than 0 detected!");
				}
				else {
					User user = new User();
					user.setId(user_id);
					user.setName(record.get("name"));
					user.setLogin(record.get("login"));
					user.setSalary(user_salary);
					users.add(user);
				}
			}
			return users;
			
		} catch (IncorrectNumberOfColumnsException e1) {
			throw new RuntimeException(e1.getMessage());
		} catch (SalaryLessThanZeroException e2) {
			throw new RuntimeException(e2.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse CSV file: "+ e.getMessage());
		}
	}
}
