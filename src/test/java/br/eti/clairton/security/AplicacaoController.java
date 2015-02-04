package br.eti.clairton.security;

class AplicacaoController {

	@Protected
	public void test() {

	}

	@Resource
	public String getResource() {
		return "aplicacao";
	}
}