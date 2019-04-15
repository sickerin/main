package seedu.giatros.model.account;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an account. Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private Name name;
    private Username username;
    private Password password;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password, Name name) {
        requireAllNonNull(username, password, name);
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    /**
     * Only username is required.
     */
    public Account(Username username) {
        requireAllNonNull(username);
        this.username = username;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns true if both account have the same username. There is no other fields that is stronger than a username
     * which is guaranteed to be unique in the entire system.
     */
    public boolean isSameUsername(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }
        return otherAccount != null && otherAccount.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both account have the same identity and data fields. This defines a stronger notion of equality
     * between two account.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        if (name == null || otherAccount.name == null) { // name will be null in login situation
            return username.equals(otherAccount.username) && password.equals(otherAccount.password);
        } else {
            return username.equals(otherAccount.username)
                    && password.equals(otherAccount.password)
                    && name.equals(otherAccount.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name); // Returns zero (0) for NULL attributes
    }

    @Override
    public String toString() {
        return username.toString();
    }
}
