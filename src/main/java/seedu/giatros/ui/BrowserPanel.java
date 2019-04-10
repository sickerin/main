package seedu.giatros.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.giatros.MainApp;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Patient;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://www.google.com/search?q=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Patient> selectedPatient) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load patient page when selected patient changes.
        selectedPatient.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadPatientPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Loads a Google search page for the first allergy associated with the {@Code Patient},
     * if the {@code Patient} has some allergies. Otherwise, loads the default homepage.
     */
    private void loadPatientPage(Patient patient) {
        // Modify the method to load the patient's allergy instead of the name of the patient
        if (patient.getAllergies().isEmpty()) {
            loadDefaultPage();
            return;
        }

        Allergy firstAllergy = (Allergy) patient.getAllergies().toArray()[0];

        loadPage(SEARCH_PAGE_URL + firstAllergy.allergyName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
