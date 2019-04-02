package seedu.giatros.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.util.AppUtil.checkArgument;

/**
 * Represents an {@code Account}'s password. Is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    /*
     * In actual practice, it is recommended not to restrict the max password length. In the case
     * of this module, let us assume that the average user's password length of between 6 to 20 characters.
     */
    public static final String MESSAGE_PASSWORD_CONSTRAINT =
            "Password should not contain spaces, and must be between length 6 and 20";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String PASSWORD_VALIDATION_REGEX = "[\\p{ASCII}&&[\\S]]{6,20}";

    private String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINT);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password.
     *
     * @param password Password to validate.
     */
    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles null as well
        if (!(other instanceof Password)) {
            return false;
        }

        return password.equals(((Password) other).password); // always return false
    }
}
