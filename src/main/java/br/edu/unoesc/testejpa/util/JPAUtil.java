package br.edu.unoesc.testejpa.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	
	private static final EntityManagerFactory EMF;
	
	static {
		try {
			EMF = Persistence.createEntityManagerFactory("jpapersistencia");	
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JPAUtil() {
	}

	public static EntityManager getEntityManager() {
		return EMF.createEntityManager();
	}

	public static void closeEntityManagerFactory() {
		EMF.close();
	}
}
