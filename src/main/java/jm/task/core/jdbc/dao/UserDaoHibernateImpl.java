package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL PRIMARY KEY auto_increment, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age TINYINT NOT NULL)";
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();

        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {

            String sql = "DROP TABLE IF EXISTS Users";
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();

        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String hql = "FROM User";

            userList = session.createQuery(hql, User.class).list();
            session.getTransaction().commit();

        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }

        return userList;

    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            String sql = "TRUNCATE TABLE Users";

            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException ie) {
            ie.printStackTrace();
        }

    }
}
