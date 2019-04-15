package seedu.giatros.model.account;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NameTest {

    @Test
    public void toStringCheck(){
        Name newName = new Name("Alice");
        assertEquals("Alice", newName.toString());
    }
}
