package seedu.giatros.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.ui.testutil.AccountCreator;

public class UsernameContainsKeywordPredicateTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        UsernameContainsKeywordPredicate firstPredicate = new UsernameContainsKeywordPredicate(
                firstPredicateKeyword);
        UsernameContainsKeywordPredicate secondPredicate = new UsernameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UsernameContainsKeywordPredicate firstPredicateCopy = new UsernameContainsKeywordPredicate(
                firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword only since account username cannot have spaces
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("MANAGER");
        assertTrue(predicate.test(new AccountCreator("manager").withUsername("MANAGER").build()));

        // Only one matching keyword
        predicate = new UsernameContainsKeywordPredicate("ces");
        assertTrue(predicate.test(new AccountCreator("manager").withUsername("ces").build()));

        // Mixed-case keywords
        predicate = new UsernameContainsKeywordPredicate("MANAGER");
        assertTrue(predicate.test(new AccountCreator("manager").withUsername("MANAGER").build()));
    }

    @Test
    public void test_usernameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("zhikai");
        assertFalse(predicate.test(new AccountCreator("manager").withUsername("MANAGER").build()));

        // Non-matching keyword with symbols
        predicate = new UsernameContainsKeywordPredicate("kai^");
        assertFalse(predicate.test(new AccountCreator("manager").withUsername("zhikai").build()));
    }

    @Test
    public void test_emptyKeyword_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("");
        predicate.test(new AccountCreator("manager").withUsername("MANAGER").build());
    }

    @Test
    public void test_spaceOnlyKeyword_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate(" ");
        predicate.test(new AccountCreator("manager").withUsername("MANAGER").build());
    }

    @Test
    public void test_multipleKeyword_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("MANAGER root1 root3");
        predicate.test(new AccountCreator("manager").withUsername("MANAGER").build());
    }
}
