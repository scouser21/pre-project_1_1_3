package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();

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
        Connection connection = Util.getConnection();

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
        Connection connection = Util.getConnection();

        try( Statement stmt = connection.createStatement()) {
            String sql = "INSERT INTO USERS VALUES (" + Util.getNextID() + ", '" + name + "', '" + lastName + "'," + age + ");";
            stmt.executeUpdate(sql);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
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
        Connection connection = Util.getConnection();

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

    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        Connection connection = Util.getConnection();

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
}
