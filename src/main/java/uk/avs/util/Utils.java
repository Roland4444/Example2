package uk.avs.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Provide general purpose methods for handling OpenCV-JavaFX data conversion.
 * Moreover, expose some "low level" methods for matching few JavaFX behavior.
 *
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @author <a href="http://max-z.de">Maximilian Zuleger</a>
 * @version 1.0 (2016-09-17)
 * @since 1.0
 */
public final class Utils {

    public static String trimApply(String input){
        BigDecimal bd = new BigDecimal(input);
        BigDecimal result  =  bd.setScale(2, RoundingMode.HALF_UP);
        return String.valueOf(result);
    };

    public  static void safeDelete(String filename){
        if (new File(filename).exists())
            new File(filename).delete();
    };

}
