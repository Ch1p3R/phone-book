package com.phonebook.service.exceptions;

public class ExceptionUtil {
	private ExceptionUtil() {
	}

	public static void checkNotFoundBoolWithId(boolean found, int id) {
		checkNotFound(found, "id=" + id);
	}

	public static <T> T checkNotFoundObjWithId(T object, int id) {
		return checkNotFound(object, "id=" + id);
	}

	public static <T> T checkNotFound(T object, String msg) {
		checkNotFound(object != null, msg);
		return object;
	}

	public static void checkNotFound(boolean found, String msg) {
		if (!found) {
			throw new NotFoundException("Not found entity with " + msg);
		}
	}
}
