package com.cirs.util;

import com.cirs.entities.Complaint.Status;
import static com.cirs.entities.Complaint.Status.*;

public class ActionStatusMapper {

	public static Status getNewStatus(String action, Status status) {

		switch (status) {
		case PENDING:
			switch (action) {
			case "Approve":
				return INPROGRESS;
			case "Reject":
				return REJECTED;
			case "Duplicate":
				return DUPLICATE;
			}
		case INPROGRESS:
			switch (action) {
			case "Complete":
				return COMPLETE;
			case "Duplicate":
				return DUPLICATE;
			}
		default:
			throw new AssertionError("cannot map " + action + " with " + status);
		}
	}
}
