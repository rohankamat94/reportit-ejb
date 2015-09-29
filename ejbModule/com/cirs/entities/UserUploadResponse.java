package com.cirs.entities;

import java.util.List;

public class UserUploadResponse {
	private int entitiesCreated;
	private List<UploadError> errors;

	public UserUploadResponse(int entitiesCreated, List<UploadError> errors) {
		super();
		this.entitiesCreated = entitiesCreated;
		this.errors = errors;
	}

	public int getEntitiesCreated() {
		return entitiesCreated;
	}

	public void setEntitiesCreated(int entitiesCreated) {
		this.entitiesCreated = entitiesCreated;
	}

	public List<UploadError> getErrors() {
		return errors;
	}

	public void setErrors(List<UploadError> errors) {
		this.errors = errors;
	}
}
