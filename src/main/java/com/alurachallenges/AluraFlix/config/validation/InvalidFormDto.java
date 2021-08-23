package com.alurachallenges.AluraFlix.config.validation;

public class InvalidFormDto {

	private String field;
	private String errorType;

	public InvalidFormDto(String field, String message) {
		this.field = field;
		this.errorType = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
}
