package org.needle4j.injection.constuctor;

import javax.inject.Inject;

import org.needle4j.db.User;
import org.needle4j.injection.CurrentUser;

public class UserDao {

    private final User user;

    private final User currentUser;

    @Inject
    public UserDao(User user, @CurrentUser User currentUser) {
        super();
        this.user = user;
        this.currentUser = currentUser;
    }

    public User getUser() {
        return user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
