package uk.avs.util;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WayBillUtil {
    public static JSONObject restoreJSON(String input) throws IOException, ParseException {
        String json =new String(Files.readAllBytes(Paths.get(input)));
        return (JSONObject) new JSONParser().parse(json);
    }
    public static Object[][] dataFromObject(JSONObject input){
        Object[][] result = new Object[1][13];
        result[0][0] = input.get("Date").toString();
        result[0][1] = input.get("Time").toString();
        result[0][2] = input.get("Waybill_number").toString();
        result[0][3] = input.get("Comment").toString();
        result[0][4] = input.get("Metall").toString();
        result[0][5] = input.get("Brutto").toString();
        result[0][6] = input.get("Tara").toString();
        result[0][7] = input.get("Clogging").toString();   //засор
        result[0][8] = input.get("Trash").toString();        //примеси
        result[0][9] = input.get("Netto").toString();
        result[0][10] = "";//input.get("mode").toString();
        result[0][11] = input.get("Complete").toString();
        result[0][12] = "";
        return result;
    }

}
