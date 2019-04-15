package seedu.giatros.model.account;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameTest {

    @Test
    public void toStringCheck(){
        Name newName = new Name("Alice");
        assertEquals("Alice", newName.toString());
    }
}
