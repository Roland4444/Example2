package uk.avs;

import org.junit.Test;

import static org.junit.Assert.*;

public class EditorTest {

    @Test
    public void calculateNetto() {
        float etalon = (float) 5.53;
        float brutto = (float) 8.9;
        float tara=1;
        float clogging = 30;
        float trash =0;

        assertEquals(etalon, Editor.calculateNetto(trash, brutto, tara, clogging), 0.01);
        float result = Editor.calculateNetto(trash, brutto, tara, clogging);
        System.out.println(String.valueOf(result));
    }
}