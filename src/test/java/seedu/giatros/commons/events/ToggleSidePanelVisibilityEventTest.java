package seedu.giatros.commons.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.giatros.commons.events.ui.ToggleSidePanelVisibilityEvent;

public class ToggleSidePanelVisibilityEventTest {

    private static ToggleSidePanelVisibilityEvent toggle = new ToggleSidePanelVisibilityEvent(true);

    @Test
    public void constructor_returnTrue() {
        assertTrue(toggle.isVisible);
    }

    @Test
    public void simpleName_returnEqualAndNotNull() {
        assertNotNull(toggle.toString());
        assertEquals(toggle.toString(), "ToggleSidePanelVisibilityEvent");
    }
}
