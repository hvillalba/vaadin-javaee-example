package net.hdavid.vaadinjeeexample.ejb;

import javax.annotation.PostConstruct; 
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupAndShutdown {

	@EJB
	EntityAutoCreation eac;
	
	@PostConstruct
	private void atStartup() {
		eac.reloadRequiredEntities();
	}
	
	@PreDestroy
	private void atShutdown() {

	}
}
