package seedu.giatros.ui.testutil;

import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

/**
 * Properly sets up an account
 */
public class AccountCreator {
    public static final String DEFAULT_MANAGER_USERNAME = "MANAGER";
    public static final String DEFAULT_MANAGER_PASSWORD = "1122qq";
    public static final String DEFAULT_MANAGER_NAME = "MANAGER";

    public static final String DEFAULT_STAFF_USERNAME = "STAFF";
    public static final String DEFAULT_STAFF_PASSWORD = "1122qq";
    public static final String DEFAULT_STAFF_NAME = "STAFF";

    private Username username;
    private Password password;
    private Name name;

    public AccountCreator(String user) {
        if (user.equals("manager")) {
            username = new Username(DEFAULT_MANAGER_USERNAME);
            password = new Password(DEFAULT_MANAGER_PASSWORD);
            name = new Name(DEFAULT_MANAGER_NAME);
        } else {
            username = new Username(DEFAULT_STAFF_USERNAME);
            password = new Password(DEFAULT_STAFF_PASSWORD);
            name = new Name(DEFAULT_STAFF_NAME);
        }
    }

    /**
     * Initializes the AccountBuilder with the data of {@code accountToCopy}.
     */
    public AccountCreator(Account accountToCopy) {
        username = accountToCopy.getUsername();
        password = accountToCopy.getPassword();
        name = accountToCopy.getName();
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountCreator withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Account} that we are building.
     */
    public AccountCreator withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Account} that we are building.
     */
    public AccountCreator withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Build an Account with the username and password given
     * @return
     */
    public Account build() {
        return new Account(username, password, name);
    }
}
