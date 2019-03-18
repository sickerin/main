package seedu.giatros.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.giatros.testutil.TypicalPersons.ALICE;
import static seedu.giatros.testutil.TypicalPersons.getTypicalGiatrosBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.giatros.model.person.Person;
import seedu.giatros.model.person.exceptions.DuplicatePersonException;
import seedu.giatros.testutil.PersonBuilder;

public class GiatrosBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GiatrosBook giatrosBook = new GiatrosBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), giatrosBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        giatrosBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyGiatrosBook_replacesData() {
        GiatrosBook newData = getTypicalGiatrosBook();
        giatrosBook.resetData(newData);
        assertEquals(newData, giatrosBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        GiatrosBookStub newData = new GiatrosBookStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        giatrosBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        giatrosBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInGiatrosBook_returnsFalse() {
        assertFalse(giatrosBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInGiatrosBook_returnsTrue() {
        giatrosBook.addPerson(ALICE);
        assertTrue(giatrosBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInGiatrosBook_returnsTrue() {
        giatrosBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(giatrosBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        giatrosBook.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        giatrosBook.addListener(listener);
        giatrosBook.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        giatrosBook.addListener(listener);
        giatrosBook.removeListener(listener);
        giatrosBook.addPerson(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyGiatrosBook whose persons list can violate interface constraints.
     */
    private static class GiatrosBookStub implements ReadOnlyGiatrosBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        GiatrosBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
