package seedu.giatros.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {
    public static final Account MANAGER = new Account(new Username("MANAGER"), new Password("1122qq"),
            new Name("MANAGER"));
    public static final Account BABA = new Account(new Username("baba"), new Password("1122qq"), new Name("baba"));

    /**
     * Returns an {@code GiatrosBook} with all the typical patients.
     */
    public static GiatrosBook getTypicalGiatrosBook() {
        GiatrosBook ab = new GiatrosBook();
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        return ab;
    }

    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(MANAGER, BABA));
    }
}
