package com.akiradata.orca;

public class ApplicationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 5546384871409643453L;

	public ApplicationRuntimeException() {
	}

	public ApplicationRuntimeException(String arg0) {
		super(arg0);
	}

	public ApplicationRuntimeException(Throwable arg0) {
		super(arg0);
	}

	public ApplicationRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApplicationRuntimeException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
