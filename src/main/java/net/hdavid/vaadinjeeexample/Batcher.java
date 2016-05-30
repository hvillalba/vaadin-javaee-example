package net.hdavid.vaadinjeeexample;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

public class Batcher {
	private static final Logger log = Logger.getLogger(Batcher.class.getName());
	private static final int logEvery = 10000;
	public static int manageBatch(int cbatch, EntityManager em) {
		if (cbatch % WarPersistenceXmlInfo.batchSize == 0) {
			em.flush();
			em.clear();
		}
		
		if (cbatch % logEvery == 0) {
			log.info("Batcher at: "+ cbatch);
		}
		
		return cbatch +1;
	}

	
}
