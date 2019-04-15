package seedu.giatros.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.testutil.TypicalAccounts.BABA;
import static seedu.giatros.testutil.TypicalAccounts.MANAGER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.model.account.exceptions.AccountNotFoundException;
import seedu.giatros.model.account.exceptions.DuplicateAccountException;

public class UniqueAccountListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueAccountList uniqueAccountList = new UniqueAccountList();

    @Test
    public void contains_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.contains(null);
    }

    @Test
    public void contains_accountNotInList_returnsFalse() {
        assertFalse(uniqueAccountList.contains(BABA));
    }

    @Test
    public void contains_accountInList_returnsTrue() {
        uniqueAccountList.add(BABA);
        assertTrue(uniqueAccountList.contains(BABA));
    }

    @Test
    public void add_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.add(null);
    }

    @Test
    public void add_duplicateAccount_throwsDuplicateAccountException() {
        uniqueAccountList.add(BABA);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.add(BABA);
    }

    @Test
    public void remove_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.remove(null);
    }

    @Test
    public void remove_accountDoesNotExist_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        uniqueAccountList.remove(BABA);
    }

    @Test
    public void remove_existingAccount_removesAccount() {
        uniqueAccountList.add(BABA);
        uniqueAccountList.remove(BABA);
        UniqueAccountList expectedUniqueAccountList = new UniqueAccountList();
        assertEquals(expectedUniqueAccountList, uniqueAccountList);
    }

    @Test
    public void setAccounts_nullUniqueAccountList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.setAccounts((UniqueAccountList) null);
    }

    @Test
    public void setAccounts_uniqueAccountList_replacesOwnListWithProvidedUniqueAccountList() {
        uniqueAccountList.add(BABA);
        UniqueAccountList expectedUniqueAccountList = new UniqueAccountList();
        expectedUniqueAccountList.add(MANAGER);
        uniqueAccountList.setAccounts(expectedUniqueAccountList);
        assertEquals(expectedUniqueAccountList, uniqueAccountList);
    }

    @Test
    public void setAccounts_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.setAccounts((List<Account>) null);
    }

    @Test
    public void setAccounts_list_replacesOwnListWithProvidedList() {
        uniqueAccountList.add(BABA);
        List<Account> accountList = Collections.singletonList(MANAGER);
        uniqueAccountList.setAccounts(accountList);
        UniqueAccountList expectedUniqueAccountList = new UniqueAccountList();
        expectedUniqueAccountList.add(MANAGER);
        assertEquals(expectedUniqueAccountList, uniqueAccountList);
    }

    @Test
    public void setAccounts_listWithDuplicateAccounts_throwsDuplicateAccountException() {
        List<Account> listWithDuplicateAccounts = Arrays.asList(BABA, BABA);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.setAccounts(listWithDuplicateAccounts);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        uniqueAccountList.asUnmodifiableObservableList().remove(0);
    }
}
