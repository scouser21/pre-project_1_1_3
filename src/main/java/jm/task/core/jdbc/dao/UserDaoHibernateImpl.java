package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);

            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();

            String tableName = "users";
            String sql = "DELETE FROM " + tableName;
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        List<User> list = null;
        try {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Util.closeFactory(session);
        }
    }
}
