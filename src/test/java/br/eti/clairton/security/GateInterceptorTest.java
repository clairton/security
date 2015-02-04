package br.eti.clairton.security;

import static org.mockito.Mockito.mock;

import javax.inject.Inject;
import javax.interceptor.InvocationContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.eti.clairton.cdi.test.CdiJUnit4Runner;

@RunWith(CdiJUnit4Runner.class)
public class GateInterceptorTest {
	private LockInterceptor interceptor;
	private InvocationContext context = mock(InvocationContext.class);
	private @Inject Locksmith lockSmith;

	@Test
	public void testInvoke() throws Throwable {
		final String token = lockSmith.create("admin", "123456");
		interceptor = new LockInterceptor(lockSmith, token);
		interceptor.invoke(context);
	}

	@Test(expected = UnauthenticatedException.class)
	public void testInvokeWithInvalidHash() throws Throwable {
		interceptor = new LockInterceptor(lockSmith, "hash_nao_valido");
		interceptor.invoke(context);
	}
}
