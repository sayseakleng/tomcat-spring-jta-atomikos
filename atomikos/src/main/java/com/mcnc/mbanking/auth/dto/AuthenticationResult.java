package com.mcnc.mbanking.auth.dto;

public class AuthenticationResult {
	private String publicKey;
	private String salt;
	
	public AuthenticationResult(String publicKey, String salt) {
		this.publicKey = publicKey;
		this.salt = salt;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "AuthenticationResult [publicKey=" + publicKey + ", salt=" + salt + "]";
	}
}
