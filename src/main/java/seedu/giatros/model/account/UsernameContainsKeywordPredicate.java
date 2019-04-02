package seedu.giatros.model.account;

import java.util.function.Predicate;

import seedu.giatros.commons.util.StringUtil;



/**
 * Tests that a {@code Account}'s {@code Username} contains the keyword given.
 */
public class UsernameContainsKeywordPredicate implements Predicate<Account> {

    private final String keyword;

    public UsernameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Account account) {
        return StringUtil.containsSubstringIgnoreCase(account.getUsername().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernameContainsKeywordPredicate // instanceof handles nulls
                    && keyword.equals(((UsernameContainsKeywordPredicate) other).keyword)); // state check
    }
}
