package net.hdavid.vaadinjeeexample.ejb;

import lombok.SneakyThrows;
import net.hdavid.vaadinjeeexample.Batcher;
import net.hdavid.vaadinjeeexample.entity.Product;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class EntityAutoCreation {

	@PersistenceContext
	private EntityManager em;

    private static volatile long idgen = 1;

    @SneakyThrows
    public static EntityAutoCreation locate() {
        return (EntityAutoCreation) new InitialContext().lookup("java:module/EntityAutoCreation");
    }

	public void reloadRequiredEntities() {
        em.createQuery("delete from Product").executeUpdate();

        System.out.println("Loading entities");
        for (int i = 0;i<25000;i++) {
            Product p = new Product();
            p.setId(idgen++);
            p.setName(NameGenerator.generateName());
            p.setDescription(NameGenerator.generateName()+" "+ NameGenerator.generateName());

            em.persist(p);
            Batcher.manageBatch(i, em);
        }

        System.out.println("END Loading entities");

	}
	
	
	
}
