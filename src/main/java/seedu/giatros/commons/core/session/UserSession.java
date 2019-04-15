package seedu.giatros.commons.core.session;

import seedu.giatros.model.account.Account;

/**
 * Keeps track of the current user session
 */
public class UserSession {

    private static boolean isAuthenticated = false;
    private static Account account;

    /**
     * Stores this {@code Account} info as part of this session.
     *
     * @param acc logged in for this session.
     */
    public static void create(Account acc) {
        if (!isAuthenticated) {
            isAuthenticated = true;
            account = acc;
        }
    }

    /**
     * Logs out of this account which releases this session.
     */
    public static void destroy() {
        isAuthenticated = false;
        account = null;
    }

    /**
     * Updates the session with the new account info, such as updating of account password.
     *
     * @param acc that has been updated.
     */
    public static void update(Account acc) {
        if (isAuthenticated) {
            account = acc;
        }
    }

    /**
     * Checks if this session exists.
     *
     * @return true if this session exists. Otherwise, false.
     */
    public static boolean isAuthenticated() {
        return isAuthenticated && account != null;
    }

    /**
     * Gets the account that is logged in for this session.
     *
     * @return a {@code Account} object.
     */
    public static Account getAccount() {
        return account;
    }
}
