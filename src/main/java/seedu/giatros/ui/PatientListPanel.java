package seedu.giatros.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.model.patient.Patient;

/**
 * Panel containing the list of patients.
 */
public class PatientListPanel extends UiPart<Region> {
    private static final String FXML = "PatientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientListPanel.class);

    @FXML
    private ListView<Patient> patientListView;

    public PatientListPanel(ObservableList<Patient> patientList, ObservableValue<Patient> selectedPatient,
            Consumer<Patient> onSelectedPatientChange) {
        super(FXML);
        patientListView.setItems(patientList);
        patientListView.setCellFactory(listView -> new PatientListViewCell());
        patientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in patient list panel changed to : '" + newValue + "'");
            onSelectedPatientChange.accept(newValue);
        });
        selectedPatient.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected patient changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected patient,
            // otherwise we would have an infinite loop.
            if (Objects.equals(patientListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                patientListView.getSelectionModel().clearSelection();
            } else {
                int index = patientListView.getItems().indexOf(newValue);
                patientListView.scrollTo(index);
                patientListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);

            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatientCard(patient, getIndex() + 1).getRoot());
            }
        }
    }

}
