package seedu.giatros.model.account;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PASSWORD;

public class PasswordTest {

    @Test
    public void toStringCheck(){
        Password password = new Password("Alices");
        assertEquals("Alices", password.toString());
    }

    @Test
    public void equals() {
        Password password1 = new Password(VALID_PASSWORD);

        // same object
        assertTrue(password1.equals(password1));

        // different type
        assertFalse(password1.equals(null));
        assertFalse(password1.equals(0));
    }
}
