package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.giatros.model.Model;
import seedu.giatros.model.patient.Patient;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Patient> PREDICATE_MATCHING_NO_PATIENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Patient> toDisplay) {
        Optional<Predicate<Patient>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPatientList(predicate.orElse(PREDICATE_MATCHING_NO_PATIENTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Patient... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Patient} equals to {@code other}.
     */
    private static Predicate<Patient> getPredicateMatching(Patient other) {
        return patient -> patient.equals(other);
    }
}
