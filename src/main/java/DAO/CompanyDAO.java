package DAO;

import Entities.Company;
import JDBC.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompanyDAO {
    public boolean add(Company entity){
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
    public boolean update (int id, String name, String address){
        boolean isAdded = false;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Company entity = session.get(Company.class, id);
            entity.setName(name);
            entity.setAddress(address);
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
            Company company = session.get(Company.class, id);
            session.remove(company);
            tx.commit();
            isAdded = true;
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return isAdded;
    }

    public Company fetchById(int id){
        Company company = null;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            company = session.get(Company.class, id);
            tx.commit();
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return company;
    }
}
