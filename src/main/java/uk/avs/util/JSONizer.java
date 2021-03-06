package uk.avs.util;
import java.util.ArrayList;
import org.json.simple.JSONObject;

public class JSONizer {

    public String JSONedRestored(JSONObject input){
        return input.toString();
    }
    public String JSONedRestored(ArrayList <Object>input){
        StringBuilder buf = new StringBuilder();
        //  buf.append(input.)
        buf.append("{");
        buf.append(returnField("Date", input.get(0).toString())+'\n');
        buf.append(returnField("Time", input.get(1).toString())+'\n');
        buf.append(returnField("Waybill_number", input.get(2).toString())+'\n');
        buf.append(returnField("Comment", input.get(3).toString())+'\n');
        buf.append(returnField("Metall", input.get(4).toString())+'\n');
        buf.append(returnField("Brutto", input.get(5).toString())+'\n');
        buf.append(returnField("Tara", input.get(6).toString())+'\n');
        buf.append(returnField("Clogging", input.get(7).toString())+'\n');
        buf.append(returnField("Trash", input.get(8).toString())+'\n');
        buf.append(returnField("Netto", input.get(9).toString())+'\n');
        buf.append(returnField("Mode",input.get(10).toString())+'\n');
        buf.append(returnField("Complete", input.get(11).toString(),true)+'\n');
   //     buf.append(returnField("Condition", input.get(12).toString(),true)+'\n');
        buf.append("}");


        return buf.toString();
    }



    public String returnField(String nameField, String Value){
        StringBuilder sb = new StringBuilder();
        sb.append("\""+nameField+"\":");
        sb.append("\""+Value+"\",");
        return sb.toString();
    }
    public String returnField(String nameField, String Value, boolean last){
        StringBuilder sb = new StringBuilder();
        sb.append("\""+nameField+"\":");
        sb.append("\""+Value+"\"");
        return sb.toString();
    }
}
