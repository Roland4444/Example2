package uk.avs.util;

import uk.avs.view.WeighingView;

import java.util.ArrayList;

public class JSONizer {
    public String JSONedRestored(WeighingView input){
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append(returnField("Date", input.getDateCreate().toString())+'\n');
        buf.append(returnField("Time", input.getTimeCreate().toString())+'\n');
        buf.append(returnField("Waybill_number", String.valueOf(input.getWaybill()))+'\n');
        buf.append(returnField("Comment", input.getComment())+'\n');
        buf.append(returnField("Metall", input.getMetal().toString())+'\n');
        buf.append(returnField("Brutto", input.getBrutto().toString())+'\n');
        buf.append(returnField("Tara", input.getTare().toString())+'\n');
        buf.append(returnField("Clogging", input.getClogging().toString())+'\n');
        buf.append(returnField("Trash", input.getTrash().toString())+'\n');
        buf.append(returnField("Netto", input.getNetto().toString())+'\n');
        buf.append(returnField("Mode", input.getMode())+'\n');
        buf.append(returnField("Complete", input.getComplete())+'\n');
        buf.append(returnField("Condition", input.isUpload(), true)+'\n');
        buf.append("}");


        return buf.toString();
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
        buf.append(returnField("Complete", input.get(11).toString())+'\n');
        buf.append(returnField("Condition", input.get(12).toString(),true)+'\n');
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
