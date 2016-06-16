package br.eti.clairton.security;

import java.util.HashMap;

public class Repository extends HashMap<String, String> {
	private static final long serialVersionUID = 1L;
	private static final Repository instance = new Repository() {
		private static final long serialVersionUID = 1L;
		{
			put("admin", "123456");
			put("maria", "123456");
		}
	};

	private Repository() {
	}

	public static Repository getRepository() {
		return instance;
	}
}
