package seedu.giatros.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.giatros.testutil.TypicalPatients.AMY;
import static seedu.giatros.testutil.TypicalPatients.BOB;
import static seedu.giatros.testutil.TypicalPatients.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.giatros.testutil.GiatrosBookBuilder;

public class VersionedGiatrosBookTest {

    private final ReadOnlyGiatrosBook giatrosBookWithAmy = new GiatrosBookBuilder().withPatient(AMY).build();
    private final ReadOnlyGiatrosBook giatrosBookWithBob = new GiatrosBookBuilder().withPatient(BOB).build();
    private final ReadOnlyGiatrosBook giatrosBookWithCarl = new GiatrosBookBuilder().withPatient(CARL).build();
    private final ReadOnlyGiatrosBook emptyGiatrosBook = new GiatrosBookBuilder().build();

    @Test
    public void commit_singleGiatrosBook_noStatesRemovedCurrentStateSaved() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(emptyGiatrosBook);

        versionedGiatrosBook.commit();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Collections.singletonList(emptyGiatrosBook),
                emptyGiatrosBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleGiatrosBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);

        versionedGiatrosBook.commit();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Arrays.asList(emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob),
                giatrosBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleGiatrosBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 2);

        versionedGiatrosBook.commit();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Collections.singletonList(emptyGiatrosBook),
                emptyGiatrosBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleGiatrosBookPointerAtEndOfStateList_returnsTrue() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);

        assertTrue(versionedGiatrosBook.canUndo());
    }

    @Test
    public void canUndo_multipleGiatrosBookPointerAtStartOfStateList_returnsTrue() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 1);

        assertTrue(versionedGiatrosBook.canUndo());
    }

    @Test
    public void canUndo_singleGiatrosBook_returnsFalse() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(emptyGiatrosBook);

        assertFalse(versionedGiatrosBook.canUndo());
    }

    @Test
    public void canUndo_multipleGiatrosBookPointerAtStartOfStateList_returnsFalse() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 2);

        assertFalse(versionedGiatrosBook.canUndo());
    }

    @Test
    public void canRedo_multipleGiatrosBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 1);

        assertTrue(versionedGiatrosBook.canRedo());
    }

    @Test
    public void canRedo_multipleGiatrosBookPointerAtStartOfStateList_returnsTrue() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 2);

        assertTrue(versionedGiatrosBook.canRedo());
    }

    @Test
    public void canRedo_singleGiatrosBook_returnsFalse() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(emptyGiatrosBook);

        assertFalse(versionedGiatrosBook.canRedo());
    }

    @Test
    public void canRedo_multipleGiatrosBookPointerAtEndOfStateList_returnsFalse() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);

        assertFalse(versionedGiatrosBook.canRedo());
    }

    @Test
    public void undo_multipleGiatrosBookPointerAtEndOfStateList_success() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);

        versionedGiatrosBook.undo();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Collections.singletonList(emptyGiatrosBook),
                giatrosBookWithAmy,
                Collections.singletonList(giatrosBookWithBob));
    }

    @Test
    public void undo_multipleGiatrosBookPointerNotAtStartOfStateList_success() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 1);

        versionedGiatrosBook.undo();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Collections.emptyList(),
                emptyGiatrosBook,
                Arrays.asList(giatrosBookWithAmy, giatrosBookWithBob));
    }

    @Test
    public void undo_singleGiatrosBook_throwsNoUndoableStateException() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(emptyGiatrosBook);

        assertThrows(VersionedGiatrosBook.NoUndoableStateException.class, versionedGiatrosBook::undo);
    }

    @Test
    public void undo_multipleGiatrosBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 2);

        assertThrows(VersionedGiatrosBook.NoUndoableStateException.class, versionedGiatrosBook::undo);
    }

    @Test
    public void redo_multipleGiatrosBookPointerNotAtEndOfStateList_success() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 1);

        versionedGiatrosBook.redo();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Arrays.asList(emptyGiatrosBook, giatrosBookWithAmy),
                giatrosBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleGiatrosBookPointerAtStartOfStateList_success() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 2);

        versionedGiatrosBook.redo();
        assertGiatrosBookListStatus(versionedGiatrosBook,
                Collections.singletonList(emptyGiatrosBook),
                giatrosBookWithAmy,
                Collections.singletonList(giatrosBookWithBob));
    }

    @Test
    public void redo_singleGiatrosBook_throwsNoRedoableStateException() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(emptyGiatrosBook);

        assertThrows(VersionedGiatrosBook.NoRedoableStateException.class, versionedGiatrosBook::redo);
    }

    @Test
    public void redo_multipleGiatrosBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(
                emptyGiatrosBook, giatrosBookWithAmy, giatrosBookWithBob);

        assertThrows(VersionedGiatrosBook.NoRedoableStateException.class, versionedGiatrosBook::redo);
    }

    @Test
    public void equals() {
        VersionedGiatrosBook versionedGiatrosBook = prepareGiatrosBookList(giatrosBookWithAmy, giatrosBookWithBob);

        // same values -> returns true
        VersionedGiatrosBook copy = prepareGiatrosBookList(giatrosBookWithAmy, giatrosBookWithBob);
        assertTrue(versionedGiatrosBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedGiatrosBook.equals(versionedGiatrosBook));

        // null -> returns false
        assertFalse(versionedGiatrosBook.equals(null));

        // different types -> returns false
        assertFalse(versionedGiatrosBook.equals(1));

        // different state list -> returns false
        VersionedGiatrosBook differentGiatrosBookList = prepareGiatrosBookList(giatrosBookWithBob, giatrosBookWithCarl);
        assertFalse(versionedGiatrosBook.equals(differentGiatrosBookList));

        // different current pointer index -> returns false
        VersionedGiatrosBook differentCurrentStatePointer = prepareGiatrosBookList(
                giatrosBookWithAmy, giatrosBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedGiatrosBook, 1);
        assertFalse(versionedGiatrosBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedGiatrosBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedGiatrosBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedGiatrosBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertGiatrosBookListStatus(VersionedGiatrosBook versionedGiatrosBook,
                                             List<ReadOnlyGiatrosBook> expectedStatesBeforePointer,
                                             ReadOnlyGiatrosBook expectedCurrentState,
                                             List<ReadOnlyGiatrosBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new GiatrosBook(versionedGiatrosBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedGiatrosBook.canUndo()) {
            versionedGiatrosBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyGiatrosBook expectedGiatrosBook : expectedStatesBeforePointer) {
            assertEquals(expectedGiatrosBook, new GiatrosBook(versionedGiatrosBook));
            versionedGiatrosBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyGiatrosBook expectedGiatrosBook : expectedStatesAfterPointer) {
            versionedGiatrosBook.redo();
            assertEquals(expectedGiatrosBook, new GiatrosBook(versionedGiatrosBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedGiatrosBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedGiatrosBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedGiatrosBook} with the {@code giatrosBookStates} added into it, and the
     * {@code VersionedGiatrosBook#currentStatePointer} at the end of list.
     */
    private VersionedGiatrosBook prepareGiatrosBookList(ReadOnlyGiatrosBook... giatrosBookStates) {
        assertFalse(giatrosBookStates.length == 0);

        VersionedGiatrosBook versionedGiatrosBook = new VersionedGiatrosBook(giatrosBookStates[0]);
        for (int i = 1; i < giatrosBookStates.length; i++) {
            versionedGiatrosBook.resetData(giatrosBookStates[i]);
            versionedGiatrosBook.commit();
        }

        return versionedGiatrosBook;
    }

    /**
     * Shifts the {@code versionedGiatrosBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedGiatrosBook versionedGiatrosBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedGiatrosBook.undo();
        }
    }
}
