package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {

        try (var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var uDAO = new UserDAO(connection);

            uDAO.create();

            var user1 = new User("Jack", "89015657811");
            var user2 = new User("Mila", "89015333811");
            var user3 = new User("Susi", "89015678811");
            var user4 = new User("Frank", "89013457811");

            uDAO.save(user1);
            uDAO.save(user2);
            uDAO.save(user3);
            uDAO.save(user4);

            uDAO.print();

            uDAO.delete(user1);
            System.out.println();

            uDAO.print();
        }
    }
}
