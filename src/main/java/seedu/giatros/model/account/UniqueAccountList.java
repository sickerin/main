package seedu.giatros.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.giatros.model.account.exceptions.AccountNotFoundException;
import seedu.giatros.model.account.exceptions.DuplicateAccountException;

/**
 * A list of account that enforces uniqueness between its elements and does not allow nulls. An account is considered
 * unique by comparing using {@code Account#isSameUsername(Account)}. As such, adding and updating of account uses
 * Account#isSameUsername(Account) for equality so as to ensure that the account being added or updated is unique in
 * terms of identity in the UniqueAccountList. However, the removal of an account uses Account#equals(Object) so as to
 * ensure that the account with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Account#isSameUsername(Account)
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameUsername);
    }

    /**
     * Adds an account to the list. The account must not already exist in the list.
     */
    public void add(Account toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        toAdd.getPassword();
        internalList.add(toAdd);
    }

    /**
     * Retrieves an account from the list of the given {@code account}'s username.
     */
    public Account get(Account account) {
        requireNonNull(account);
        if (!contains(account)) {
            throw new AccountNotFoundException();
        }
        return internalList.stream().filter(acc -> acc.isSameUsername(account)).findFirst().get();
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}. {@code target} must exist in the
     * list. The account identity of {@code editedAccount} must not be the same as another existing account in the
     * list.
     */
    public void update(Account target, Account editedAccount) {
        requireAllNonNull(target, editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccountNotFoundException();
        }

        if (!target.isSameUsername(editedAccount) && contains(editedAccount)) {
            throw new DuplicateAccountException();
        }

        editedAccount.getPassword();
        internalList.set(index, editedAccount);
    }

    /**
     * Removes the equivalent account from the list. The account must exist in the list.
     */
    public void remove(Account toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AccountNotFoundException();
        }
    }

    public void setAccounts(UniqueAccountList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code account}. {@code account} must not contain duplicate account.
     */
    public void setAccounts(List<Account> accounts) {
        requireAllNonNull(accounts);
        if (!accountsAreUnique(accounts)) {
            throw new DuplicateAccountException();
        }

        internalList.setAll(accounts);
    }

    /**
     * Returns true if {@code account} contains only unique account.
     */
    private boolean accountsAreUnique(List<Account> accounts) {
        for (int i = 0; i < accounts.size() - 1; i++) {
            for (int j = i + 1; j < accounts.size(); j++) {
                if (accounts.get(i).isSameUsername(accounts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Account> asUnmodifiableObservableList() {
        return internalList;
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccountList // instanceof handles nulls
                && internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
