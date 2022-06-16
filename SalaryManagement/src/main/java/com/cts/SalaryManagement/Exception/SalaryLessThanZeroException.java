package com.cts.SalaryManagement.Exception;

public class SalaryLessThanZeroException extends RuntimeException {

	public SalaryLessThanZeroException(String msg) {
		super(msg);
	}
}
