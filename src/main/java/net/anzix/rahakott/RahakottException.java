package net.anzix.rahakott;

public class RahakottException extends RuntimeException {

	public RahakottException() {
		super();

	}

	public RahakottException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public RahakottException(String message, Throwable cause) {
		super(message, cause);

	}

	public RahakottException(String message) {
		super(message);

	}

	public RahakottException(Throwable cause) {
		super(cause);

	}

}
