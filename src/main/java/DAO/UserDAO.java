package DAO;

import Entities.User;
import JDBC.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {
    public boolean add(User entity){
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
    public boolean update (int id, String password){
        boolean isAdded = false;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User entity = session.get(User.class, id);
            entity.setPassword(password);
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
            User user = session.get(User.class, id);
            session.remove(user);
            tx.commit();
            isAdded = true;
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return isAdded;
    }

    public User fetchById(int id){
        User user = null;
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            user = session.get(User.class, id);
            tx.commit();
        }
        catch (NoClassDefFoundError e) {
            System.out.println("Exception!" + e);
        }
        return user;
    }
}
