package DAO;

import Entities.Company;
import Entities.Product;
import JDBC.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDAO {
    public boolean add(Product entity){
    boolean isAdded = false;
    try(Session session = Connection.getSessionFactory().openSession()) {
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        isAdded = true;
    }
    catch (NoClassDefFoundError e) {
        System.out.println("Exception!" + e);
    }
    return isAdded;
    }
    public boolean update (int id, String name, int price, Company company){
        boolean isAdded = false;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Product entity = session.get(Product.class, id);
            entity.setName(name);
            entity.setPrice(price);
            entity.setCompany(company);
            session.merge(entity);
            tx.commit();
            isAdded = true;
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return isAdded;
    }
    public boolean delete(int id){
        boolean isAdded = false;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.remove(product);
            tx.commit();
            isAdded = true;
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return isAdded;
    }

    public Product fetchById(int id){
        Product product = null;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            product = session.get(Product.class, id);
            tx.commit();
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return product;
    }
}
