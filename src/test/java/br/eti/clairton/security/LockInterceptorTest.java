package br.eti.clairton.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;

public class LockInterceptorTest {
	private GateInterceptor interceptor;
	private InvocationContext context;
	private Gate authorizator;
	private final String usuario = "jose";
	private final String app = "test";

	@Before
	public void setUp() throws Exception {
		authorizator = mock(Gate.class);
		interceptor = new GateInterceptor(app, usuario, authorizator, new Extractor());
		final Object[] parameters = {};
		final AplicacaoController target = new AplicacaoController();
		final Method method = target.getClass().getMethod("test");
		context = new TestInvocationContext(parameters, target, method);
	}

	@Test(expected = UnauthorizedException.class)
	public void testInvokeUnauthorized() throws Throwable {
		interceptor.invoke(context);
	}

	@Test
	public void testInvokeAuthorized() throws Throwable {
		when(
				authorizator.isOpen(anyString(), anyString(), anyString(),
						anyString())).thenReturn(Boolean.TRUE);
		final Object expected = "çegjweargihjjhjsfkhgsdlçgjusdayjicodtaiotiow";
		final InvocationContext spy = spy(context);
		when(spy.proceed()).thenReturn(expected);
		assertEquals(expected, interceptor.invoke(spy));
	}

	@Test(expected = TestException.class)
	public void testOriginalException() throws Throwable {
		when(
				authorizator.isOpen(anyString(), anyString(), anyString(),
						anyString())).thenThrow(new TestException());
		interceptor.invoke(context);
	}
}