package uk.avs;

import Message.abstractions.BinaryMessage;
import abstractions.ResponceMessage;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.BinaryOperator;

import static org.junit.Assert.*;

public class Example2Test {

  ///  @Test
    public void writeJSONtoDB() throws IOException {
        String initialJSON = "{\"a\":\"12\"}";
        String fileTosave = "temp.json";
      ///  Example2.writeJSONtoDB(initialJSON, fileTosave);
        assertEquals(true, new File(fileTosave).exists());
        new File(fileTosave).delete();
    }

    @Test
    public void testString() throws UnsupportedEncodingException {
        byte[] arr = "gggg".getBytes("Windows-1251");
        String n = new String(arr);
        assertEquals("gggg", n);
    }

    @Test
    public void testWriteJSONtoDB() throws IOException, ParseException {
        String initialJSON = "{\"a\":\"12\"}";
        String fileTosave = "temp2.json";
        Example2.writeJSONtoDB(initialJSON, fileTosave, "3");
        assertEquals(true, new File(fileTosave).exists());
       // new File(fileTosave).delete();
    }



}