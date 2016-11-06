package com.mcnc.commons.dbcp;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import com.atomikos.tomcat.EnhancedTomcatAtomikosBeanFactory;
import com.mcnc.commons.dbcp.security.CipherProvider;

public class EncryptedtAtomikosBeanFactory extends EnhancedTomcatAtomikosBeanFactory {
	
	private CipherProvider cipherProvider;
	
	public EncryptedtAtomikosBeanFactory() {
		cipherProvider = CipherProvider.instance();
	}
	
	@Override
	public Object getObjectInstance(Object obj, Name name, Context content, Hashtable<?, ?> environment) throws NamingException {
	
		if (obj instanceof Reference) {
	        try {
				setUsername((Reference) obj);
				setPassword((Reference) obj);
			} catch (Exception e) {
				throw new NamingException(e.getMessage());
			}
	    }
		
		return super.getObjectInstance(obj, name, content, environment);
	}
	
	
	private void setUsername(Reference ref) throws Exception {
		
		boolean findDecryptAndReplace = findDecryptAndReplace("xaProperties.user", ref);
		
		if(!findDecryptAndReplace) {
			String error = String.format("The \"xaProperties.user\" name/value pair was not found in the Reference object [%s]",
					ref.toString());

			throw new Exception(error);
		}
	
	}

	private void setPassword(Reference ref) throws Exception {
		
		boolean findDecryptAndReplace = findDecryptAndReplace("xaProperties.password", ref);
		
		if(!findDecryptAndReplace) {
			String error = String.format("The \"xaProperties.password\" name/value pair was not found in the Reference object [%s]",
					ref.toString());

			throw new Exception(error);
		}
		
	}
	

	private boolean findDecryptAndReplace(String refName, Reference ref) throws Exception {
		boolean isFind = false;
		
		int idx = findIndexByRefName(refName, ref);
		if(idx != -1) {
			String decrypted = decrypt(idx, ref);
			replace(idx, refName, decrypted, ref);
			isFind = true;
		}
		
		return isFind;
	}
	
	

	private void replace(int idx, String refName, String newValue, Reference ref) {
		ref.remove(idx);
		ref.add(idx, new StringRefAddr(refName, newValue));
	}

	
	private String decrypt(int idx, Reference ref) throws Exception {
		String encrypted = ref.get(idx).getContent().toString();
		return cipherProvider.decrypt(encrypted);
	}

	
	private int findIndexByRefName(String refName, Reference ref) {
		Enumeration<RefAddr> enu = ref.getAll();
		for (int i = 0; enu.hasMoreElements(); i++) {
			RefAddr addr = enu.nextElement();
			if (addr.getType().compareTo(refName) == 0)
				return i;
		}
		
		return -1;
	}
}
