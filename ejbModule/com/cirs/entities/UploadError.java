package com.cirs.entities;

public class UploadError {
	private int rowNumber;
	private String message;

	public UploadError(int rowNumber, String message) {
		super();
		this.rowNumber = rowNumber;
		this.message = message;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
