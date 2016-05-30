package net.hdavid.vaadinjeeexample.ejb;

import lombok.Cleanup;
import lombok.SneakyThrows;
import net.hdavid.vaadinjeeexample.entity.Product;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class Service {

    @PersistenceContext
    EntityManager em;

    @SneakyThrows
    public static Service locate() {
        return (Service) new InitialContext().lookup("java:module/Service");
    }

    public List<Product> getAllEntities(int firstRow, int pageSize, boolean sortAscending, String property) {
        return em.createQuery("select p from Product p order by "+property+" "+ (sortAscending ? "ASC" : "DESC") )
                .setMaxResults(pageSize)
                .setFirstResult(firstRow)
                .getResultList();
    }
    public int getAllEntitiesCount() {
        return new Long((Long) em.createQuery("select new java.lang.Long(count(*)) from Product p").getSingleResult()).intValue();
    }

}
