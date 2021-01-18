package uk.avs.Container;

import Message.abstractions.BinaryMessage;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StateTest {
    String suspedindto="temp.bin";
    @Test
    public void save() throws IOException {
        State initial = new State(true, true, false, false);
        BinaryMessage.write(BinaryMessage.savedToBLOB(initial), suspedindto);
        State etalon = new State(true, false, false, false);
        State a = new State(suspedindto);
        a.wait = false;
        a.save();
        State restored = new State(suspedindto);
        assertEquals(etalon.wait, restored.wait);
    }
}