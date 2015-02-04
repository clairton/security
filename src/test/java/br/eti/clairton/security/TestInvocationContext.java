package br.eti.clairton.security;

import java.lang.reflect.Method;
import java.util.Map;

import javax.interceptor.InvocationContext;

class TestInvocationContext implements InvocationContext {
	private final Object[] parameters;
	private final Object target;
	private final Method method;

	public TestInvocationContext(Object[] parameters, Object target,
			Method method) {
		super();
		this.parameters = parameters;
		this.target = target;
		this.method = method;
	}

	@Override
	public void setParameters(Object[] params) {

	}

	@Override
	public Object proceed() throws Exception {
		return method.invoke(target, parameters);
	}

	@Override
	public Object getTimer() {
		return null;
	}

	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public Object[] getParameters() {
		return parameters;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Map<String, Object> getContextData() {
		return null;
	}
}