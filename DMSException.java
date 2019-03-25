package org.dms.DMS.exceptions;

import org.dms.DMS.util.Constants;

public class DMSException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorCode = Constants.ErrorCodes.GENERAL_EXCEPTION_KEY;

	public DMSException() {
		super();
	}

	public DMSException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public DMSException(String message, Throwable exception) {
		super(message, exception);
	}

	public DMSException(String message) {
		super(message);
	}

	public DMSException(Throwable exception) {
		super(exception);
	}

	public DMSException(Throwable exception, String errorCode) {
		super(exception);
		this.errorCode = errorCode;
	}

	/**
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param String
	 *            errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
