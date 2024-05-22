package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    // Нужно указывать базовое исключение,
    // потому что выполнение запросов может привести к исключениям
    public static void main(String[] args) throws SQLException {
        // Создаем соединение с базой в памяти
        // База создается прямо во время выполнения этой строчки
        // Здесь hexlet_test — это имя базы данных
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            // Чтобы выполнить запрос, создадим объект statement
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES ('Tommy', '89636541234')";
            var sql21 = "INSERT INTO users (username, phone) VALUES ('Jimmi', '89995654774')";
            var sql22 = "INSERT INTO users (username, phone) VALUES ('Tinni', '89066541232')";
            try (var statement2 = conn.createStatement()) {
                statement2.executeUpdate(sql2);
                statement2.executeUpdate(sql21);
                statement2.executeUpdate(sql22);
            }

            var sql3 = "SELECT * FROM users";
            try(var statement3 = conn.createStatement()) {
                // Здесь вы видите указатель на набор данных в памяти СУБД
                var resultSet = statement3.executeQuery(sql3);
                // Набор данных — это итератор
                // Мы перемещаемся по нему с помощью next() и каждый раз получаем новые значения
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username") + " - " + resultSet.getString("phone"));
                }
            }
        }
    }
}
