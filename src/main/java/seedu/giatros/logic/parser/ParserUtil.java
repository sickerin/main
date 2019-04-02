package seedu.giatros.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.util.StringUtil;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String allergy} into a {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Allergy parseAllergy(String allergy) throws ParseException {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();
        if (!Allergy.isValidAllergyName(trimmedAllergy)) {
            throw new ParseException(Allergy.MESSAGE_CONSTRAINTS);
        }
        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses {@code Collection<String> allergies} into a {@code Set<Allergy>}.
     */
    public static Set<Allergy> parseAllergies(Collection<String> allergies) throws ParseException {
        requireNonNull(allergies);
        final Set<Allergy> allergySet = new HashSet<>();
        for (String allergyName : allergies) {
            allergySet.add(parseAllergy(allergyName));
        }
        return allergySet;
    }
}
