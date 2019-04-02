package seedu.giatros.model.account.exceptions;


/**
 * Signals that the operation will result in duplicate {@code Account}. {@code Account}s are considered duplicate if
 * they have the same username).
 */
public class DuplicateAccountException extends AccountException {}
