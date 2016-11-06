package com.mcnc.commons.dbcp;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import com.mcnc.commons.dbcp.security.CipherProvider;
import com.zaxxer.hikari.HikariJNDIFactory;

public class EncryptedHikariBeanFactory extends HikariJNDIFactory {
	
	private CipherProvider cipherProvider;
	
	public EncryptedHikariBeanFactory() {
		cipherProvider = CipherProvider.instance();
	}

	
	@Override
	synchronized public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
			throws Exception {
		
		 if (obj instanceof Reference) {
	            setUsername((Reference) obj);
	            setPassword((Reference) obj);
	        }
		 return super.getObjectInstance(obj, name, nameCtx, environment);
	}
	
	
	private void setUsername(Reference ref) throws Exception {
		
		boolean findDecryptAndReplace = findDecryptAndReplace("dataSource.user", ref);
		
		if(!findDecryptAndReplace) {
			findDecryptAndReplace = findDecryptAndReplace("username", ref);
		}
		
		if(!findDecryptAndReplace) {
			String error = String.format("The \"username\" name/value pair was not found in the Reference object [%s]",
					ref.toString());

			throw new Exception(error);
		}
	
	}

	private void setPassword(Reference ref) throws Exception {
		
		boolean findDecryptAndReplace = findDecryptAndReplace("dataSource.password", ref);
		if(!findDecryptAndReplace) {
			findDecryptAndReplace = findDecryptAndReplace("password", ref);
		}
		
		if(!findDecryptAndReplace) {
			String error = String.format("The \"password\" name/value pair was not found in the Reference object [%s]",
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
