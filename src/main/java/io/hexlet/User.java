package io.hexlet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;
    private String phone;

    User(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }
}
