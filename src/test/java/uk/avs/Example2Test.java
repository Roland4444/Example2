package uk.avs;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class Example2Test {

    @Test
    public void writeJSONtoDB() throws IOException {
        String initialJSON = "{\"a\":\"12\"}";
        String fileTosave = "temp.json";
        Example2.writeJSONtoDB(initialJSON, fileTosave);
        assertEquals(true, new File(fileTosave).exists());
        new File(fileTosave).delete();
    }
}