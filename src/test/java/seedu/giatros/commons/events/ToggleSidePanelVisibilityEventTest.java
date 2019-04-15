package seedu.giatros.commons.events;

import seedu.giatros.commons.events.ui.ToggleSidePanelVisibilityEvent;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ToggleSidePanelVisibilityEventTest {

    public static ToggleSidePanelVisibilityEvent toggle = new ToggleSidePanelVisibilityEvent(true);

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
