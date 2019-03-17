package seedu.giatros.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.person.Person;

/**
 * An Immutable GiatrosBook that is serializable to JSON format.
 */
@JsonRootName(value = "giatrosbook")
class JsonSerializableGiatrosBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGiatrosBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGiatrosBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyGiatrosBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGiatrosBook}.
     */
    public JsonSerializableGiatrosBook(ReadOnlyGiatrosBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Giatros book into the model's {@code GiatrosBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GiatrosBook toModelType() throws IllegalValueException {
        GiatrosBook giatrosBook = new GiatrosBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (giatrosBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            giatrosBook.addPerson(person);
        }
        return giatrosBook;
    }

}
