package uk.avs.util;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UtilsTest {
    String inputted = "00011.3426262";
    String formatted = "11.34";

    String anotherinputted = "000.45262";
    String anotherformatted = "0.45";

    String near1 = "0.787";
    String near1formatted = "0.79";

    @Test
    public void trimApply() {
        assertEquals(formatted, Utils.trimApply(inputted));
        assertEquals(anotherformatted, Utils.trimApply(anotherinputted));
        assertEquals(near1formatted, Utils.trimApply(near1));
    }

    @Test
    public void hollyshit(){
        BigDecimal brutto = new BigDecimal("1.2");
        BigDecimal tare = new BigDecimal("0.2");
        BigDecimal trash = new BigDecimal("0.2");
        BigDecimal clogging = new BigDecimal("10");

        assertEquals(brutto, new BigDecimal("1.2"));
        brutto.subtract(tare);
        assertEquals(brutto, new BigDecimal("1.2"));

        BigDecimal n = brutto.subtract(tare);
        assertEquals(n, new BigDecimal("1.0"));

        BigDecimal sub = brutto.subtract(tare).subtract(trash);
        assertEquals(sub, new BigDecimal("0.8"));

        BigDecimal percentage = clogging
                .divide(new BigDecimal("100.00"))
                .multiply(sub);

        assertEquals(percentage, new BigDecimal("0.08"));

        BigDecimal netto = sub.subtract(percentage)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(netto, new BigDecimal("0.72"));


    }

    @Test
    public void getID() throws IOException, ParseException {
        String filename = "waybillwithid.json";
        String fieldname = "Weighing_id";
        assertEquals("4", Utils.getID(filename, fieldname));

    }

    @Test
    public void getIDfake() throws IOException, ParseException {
        String filename = "waybillwithoutid.json";
        String fieldname = "Weighing_id";
        System.out.println(Utils.getID(filename, fieldname));
      //  assertEquals("4", Utils.getID(filename, fieldname));

    }

    @Test
    public void ret() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        assertEquals(3, Utils.callret(new Utils()));
    }
}