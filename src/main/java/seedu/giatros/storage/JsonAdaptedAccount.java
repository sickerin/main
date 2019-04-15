package seedu.giatros.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

/**
 * Jackson-friendly version of {@link Account}.
 */
public class JsonAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String name;
    private final String username;
    private final String password;

    /**
     * Constructs a {@code JsonAdaptedAccount} with the given account details.
     */
    @JsonCreator
    public JsonAdaptedAccount(@JsonProperty("name") String name, @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given {@code Account} into this class for Jackson use.
     */
    public JsonAdaptedAccount(Account source) {
        name = source.getName().toString();
        username = source.getUsername().toString();
        password = source.getPassword().toString();
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient.
     */
    public Account toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINT);
        }
        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINT);
        }
        final Password modelPassword = new Password(password);

        return new Account(modelUsername, modelPassword, modelName);
    }

}
