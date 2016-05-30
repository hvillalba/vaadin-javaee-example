package net.hdavid.vaadinjeeexample;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WarPersistenceXmlInfo {

	private static final Logger log = Logger.getLogger(WarPersistenceXmlInfo.class.getName());
	public static final Integer batchSize = getHibernateBatchSize();
	public static final String defaultJtaDatasource = getPersistenceXmlDefaultDs();
	private static int getHibernateBatchSize() {
		for (String s : WarResources.getResourceLines("/META-INF/persistence.xml"))
			if (s.contains("hibernate.jdbc.batch_size")) 
				return Integer.parseInt(s.replaceAll("[^0-9]",""));
		
		log.log(Level.WARNING, "Couln't read perssitence.xml batch value");
		return 500;
	}
	private static String getPersistenceXmlDefaultDs() {
		// <jta-data-source>java:/jboss/datasources/sgsdscore</jta-data-source>
		
		for (String s : WarResources.getResourceLines("/META-INF/persistence.xml")) {
			if (s.contains("jta-data-source"))  {
				s = s.trim().replaceAll(" ", "");
				s = s.replace("<jta-data-source>", "");
				s = s.replace("</jta-data-source>", "");
				return s;
			}
		}
		
		log.log(Level.WARNING, "Couln't read perssitence.xml default jta datasource");
		return null;
	}
}
