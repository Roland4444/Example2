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
    @Test
    public void testeleme(){
      float[] brutos = {(float) 8.10, (float) 26.5};
      float[] taras = {0, 0};
      float[] trashes = {(float) 0.2, (float) 0.0};
      float[] cloggings = {1, 50};
      assertEquals(7.82, Editor.calculateNetto(trashes[0], brutos[0], taras[0], cloggings[0]), 0.01);
      assertEquals(13.25, Editor.calculateNetto(trashes[1], brutos[1], taras[1], cloggings[1]), 0.01);


    };
}