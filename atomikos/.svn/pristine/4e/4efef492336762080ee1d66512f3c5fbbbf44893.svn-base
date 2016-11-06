package com.mcnc.mbanking.auth.domain;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAPairKeyAuthentication implements Serializable {
	
	private static final long serialVersionUID = 4718460739775671924L;

	private PrivateKey privateKey;
	
	private transient PublicKey publicKey;
	
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "RSAPairKeyAuthentication [privateKey=" + privateKey + "]";
	}
}
