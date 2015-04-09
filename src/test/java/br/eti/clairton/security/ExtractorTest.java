package br.eti.clairton.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExtractorTest {
	private final Extractor extractor = new Extractor();

	@Test
	public void testGetOperation() {
		extractor.getResource(new AplicacaoController());
	}

	@Test
	public void testTypeWithMethodAnotated() {
		assertEquals("aplicacao",
				extractor.getResource(new AplicacaoController()));
	}
	@Test
	public void testByType() {
		assertEquals("sale", extractor.getResource(SaleController.class));
	}

	@Test
	public void testTypeAnotated() {
		assertEquals("sale", extractor.getResource(new SaleController()));
	}

	@Test(expected = IllegalStateException.class)
	public void testTypeAnotatedIncorret() {
		extractor.getResource(new IncorrectTypeAnnotatedController());
	}

	@Test(expected = IllegalStateException.class)
	public void testMethodWithReturnNotString() {
		extractor.getResource(new IncorrectMethodAnnotatedController());
	}

}

@Resource("sale")
class SaleController {

}

@Resource()
class IncorrectTypeAnnotatedController {

}

class IncorrectMethodAnnotatedController {

	@Resource
	public SaleController test() {
		return new SaleController();
	}
}
