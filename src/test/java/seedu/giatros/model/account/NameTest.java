package seedu.giatros.model.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class NameTest {

    @Test
    public void toStringCheck() {
        Name newName = new Name("Alice");
        assertEquals("Alice", newName.toString());
    }
}
