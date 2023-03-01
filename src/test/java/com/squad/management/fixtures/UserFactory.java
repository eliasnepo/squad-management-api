package com.squad.management.fixtures;

import com.squad.management.users.User;

public class UserFactory {

    public static User getUser() {
        return new User(
                1L,
                "John",
                "Doe",
                "jdoe",
                "https://github.com/jdoe/profile.png",
                "United States"
        );
    }
}
