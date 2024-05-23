package io.hexlet;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@AllArgsConstructor
public class UserDAO {
    private Connection connection;

    public void save(User user) throws SQLException {
        if (user.getId() == null) {
            var sql = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPhone());
                preparedStatement.executeUpdate();
                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB can't return any key after saving this entity :( ");
                }
            }
        } else {
            var sql1 = "UPDATE users SET username = ?, phone = ? where id = ?";
            try (var preparedStatement1 = connection.prepareStatement(sql1)) {
                preparedStatement1.setString(1, user.getUsername());
                preparedStatement1.setString(2, user.getPhone());
                preparedStatement1.setLong(3, user.getId());
                preparedStatement1.executeUpdate();
            }
        }
    }

    public Optional<User> find(Long id) throws SQLException {
        var sql2 = "SELECT * FROM users WHERE id = ?";
        try (var preparedStatement2 = connection.prepareStatement(sql2)) {
            preparedStatement2.setLong(1, id);
            var resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                var username = resultSet.getString("username");
                var phone = resultSet.getString("phone");
                var user = new User(username, phone);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public void delete(User user) throws SQLException {
        var id = user.getId();
        var sql3 = "DELETE FROM users WHERE id = ?";
        try (var preparedStatement3 = connection.prepareStatement(sql3)) {
            preparedStatement3.setLong(1, id);
            preparedStatement3.executeUpdate();
            user.setId(null);
        }
    }

    public void print() throws SQLException {
        var sql3 = "SELECT * FROM users";
        try(var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id")
                        + " - " + resultSet.getString("username")
                        + " - " + resultSet.getString("phone"));
            }
        }
    }

    public void create() throws SQLException {
        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
