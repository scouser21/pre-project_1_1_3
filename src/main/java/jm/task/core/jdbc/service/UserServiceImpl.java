package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import javax.persistence.Column;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static int NEXT_ID = 1;


    public void createUsersTable() {
        Connection connection = getConnection();

        if (connection != null) {
            try(Statement stmt = connection.createStatement()) {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet resultSet = dbmd.getTables("", "", "USERS", null);

                if (!resultSet.next()){
                    String sql = "CREATE TABLE users (id INTEGER, name VARCHAR(255), lastName VARCHAR(255), age int)";
                    stmt.executeUpdate(sql);
                    System.out.println("Created table in given database...");
                } else {
                    System.out.println("Table 'users' already exists");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Connection connection = getConnection();

        if (connection != null) {
            try(Statement stmt = connection.createStatement()) {
                String sql = "DROP TABLE IF EXISTS USERS;";
                stmt.executeUpdate(sql);
                System.out.println("Deleting table 'USERS' from database...");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = getConnection();

        try( Statement stmt = connection.createStatement()) {
            String sql = "INSERT INTO USERS VALUES (" + NEXT_ID + ", '" + name + "', '" + lastName + "'," + age + ");";
            stmt.executeUpdate(sql);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            NEXT_ID++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        Connection connection = getConnection();

        try( Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM USERS " +
                    "WHERE id = " + id;
            stmt.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection connection = getConnection();

        try( Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM USERS;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                list.add(new User(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    public void cleanUsersTable() {
        List<User> list = getAllUsers();
        for (User user : list){
            removeUserById(user.getId());
        }

    }

    private Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
