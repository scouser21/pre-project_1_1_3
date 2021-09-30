package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static long NEXT_ID = 0;


    // For JDBC

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static long getNextID(){
        return NEXT_ID++;
    }

    // For Hibernate

    public static Session getSession(){
        Properties prop= new Properties();

        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("dialect", "org.hibernate.dialect.MySQL");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
        prop.setProperty("hibernate.connection.username", USER);
        prop.setProperty("hibernate.connection.password", PASSWORD);

        SessionFactory factory = new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
        return factory.openSession();
    }

    public static void closeFactory(Session session){
        session.getSessionFactory().close();
    }
}
