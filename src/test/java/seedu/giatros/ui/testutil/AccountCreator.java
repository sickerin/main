package seedu.giatros.ui.testutil;

import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

public class AccountCreator {
    public static final String DEFAULT_USERNAME = "ces";
    public static final String DEFAULT_PASSWORD = "1122qq";
    public static final String DEFAULT_NAME = "ces";

    private Username username;
    private Password password;
    private Name name;

    public AccountCreator() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the AccountBuilder with the data of {@code accountToCopy}.
     */
    public AccountCreator(Account accountToCopy) {
        username = accountToCopy.getUsername();
        password = accountToCopy.getPassword();
        name = accountToCopy.getName();
    }

    public Account build() {
        return new Account(username, password, name);
    }
}
