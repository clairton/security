package br.eti.clairton.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServiceInMemoryTest {
	final Lock lock = new LockInMemory();
	final Service service = new ServiceInMemory(lock);
	
	@Test
	public void testUpdatePassword() {
		assertTrue(service.update("maria","123456", "123"));
		assertTrue(lock.isValid("maria", "123"));
		assertFalse(lock.isValid("maria", "123456"));
	}

	@Test
	public void testResetPassword() {
		assertTrue(service.create("joana", "123456"));
		assertTrue(service.reset("joana", "123"));
		assertTrue(lock.isValid("joana", "123"));
		assertFalse(lock.isValid("joana", "123456"));
	}
}
