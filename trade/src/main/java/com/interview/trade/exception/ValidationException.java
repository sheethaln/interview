package com.interview.trade.exception;

import lombok.Getter;

@Getter
public final class ValidationException extends RuntimeException {
	private static final long serialVersionUID = -6684372369504097002L;

	public ValidationException(String message) {
        super(message);
    }
}
