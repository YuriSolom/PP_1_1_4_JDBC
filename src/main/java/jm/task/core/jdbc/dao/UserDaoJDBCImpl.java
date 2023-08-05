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
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT" +
                ",name VARCHAR(45) NOT NULL" +
                ", lastName VARCHAR(45) NOT NULL" +
                ", age INT NULL)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            try {
                connection.commit();
                System.out.println("Таблица пользователей успешно создана!");
            } catch (SQLException e) {
                System.out.println("Не удалось создать таблицу пользователей!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            try {
                connection.commit();
                System.out.println("Таблица пользователей удалена!");
            } catch (SQLException e) {
                System.out.println("Не удалость удалить таблицу пользователей!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            try {
                connection.commit();
                System.out.println("Пользователь " + name + " " + lastName + " успешно добавлен!");
            } catch (SQLException e) {
                System.out.println("Не удалось добавить пользователя!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            try {
                connection.commit();
                System.out.println("Пользователь с id " + id + " удален!");
            } catch (SQLException e) {
                System.out.println("Не удалось удалить пользователя!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM my_db.users";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                try {
                    connection.commit();
                    System.out.println("Cоздан список пользователей!");
                } catch (SQLException e) {
                    System.out.println("Не удалось получить список пользователей!");
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            try {
                connection.commit();
                System.out.println("Таблица очищена!");
            } catch (SQLException e) {
                System.out.println("Не удалось очистить таблицу!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
