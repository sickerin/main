package seedu.giatros.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code GiatrosBook} that keeps track of its own history.
 */
public class VersionedGiatrosBook extends GiatrosBook {

    private final List<ReadOnlyGiatrosBook> giatrosBookStateList;
    private int currentStatePointer;

    public VersionedGiatrosBook(ReadOnlyGiatrosBook initialState) {
        super(initialState);

        giatrosBookStateList = new ArrayList<>();
        giatrosBookStateList.add(new GiatrosBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code GiatrosBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        giatrosBookStateList.add(new GiatrosBook(this));
        currentStatePointer++;
        indicateModified();
    }


    /**
     * Resets the pointer of the versioned Giatros book after saving the latest state.
     */
    public void reset() {
        ReadOnlyGiatrosBook finalState = giatrosBookStateList.get(currentStatePointer);
        giatrosBookStateList.clear();
        giatrosBookStateList.add(finalState);
        currentStatePointer = 0;
    }

    private void removeStatesAfterCurrentPointer() {
        giatrosBookStateList.subList(currentStatePointer + 1, giatrosBookStateList.size()).clear();
    }

    /**
     * Restores the Giatros book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(giatrosBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the Giatros book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(giatrosBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has Giatros book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has Giatros book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < giatrosBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedGiatrosBook)) {
            return false;
        }

        VersionedGiatrosBook otherVersionedGiatrosBook = (VersionedGiatrosBook) other;

        // state check
        return super.equals(otherVersionedGiatrosBook)
                && giatrosBookStateList.equals(otherVersionedGiatrosBook.giatrosBookStateList)
                && currentStatePointer == otherVersionedGiatrosBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of giatrosBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of giatrosBookState list, unable to redo.");
        }
    }
}
