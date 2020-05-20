package com.sap.ibso.ato.training.tools.controller;

public class ObjectNotFoundException extends Exception {

	private Class<?> objectType;
	private Object id;

	public ObjectNotFoundException(Class<?> objectType, Object id) {
		this.objectType = objectType;
		this.id = id;
	}

	@Override
	public String getMessage() {
		return String.format("Object of type %s with id %s not found", objectType.getCanonicalName(), id);
	}
}
