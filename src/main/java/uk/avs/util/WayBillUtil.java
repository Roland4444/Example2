package uk.avs.util;


import uk.avs.view.WeighingView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WayBillUtil {
    public static byte[] saveWayBillToBytes(WeighingView input) {
        byte[] Result=null ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(input);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {

            }
        }
        return Result;
    }

    public static WeighingView restoreBytesToWayBill(String input) throws IOException {
        Object o=null;
        ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(Paths.get(new File(input).getPath())));
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return (WeighingView) o;
    }

    public static WeighingView restoreBytesToWayBill(byte[] input){
        Object o=null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {

            }
        }
        return (WeighingView) o;
    }

    public static Object[][] dataFromObject(WeighingView input){
        Object[][] result = new Object[1][13];
        result[0][0] = input.getDateCreate();
        result[0][1] = input.getTimeCreate();
        result[0][2] = input.getWaybill();
        result[0][3] = input.getComment();
        result[0][4] = input.getMetal().getName();
        result[0][5] = input.getBrutto();
        result[0][6] = input.getTare();
        result[0][7] = input.getClogging();   //засор
        result[0][8] = input.getTrash();        //примеси
        result[0][9] = input.getNetto();
        result[0][10] = input.getMode();
        result[0][11] = input.getComplete();
        result[0][12] = input.isUpload();
        return result;
    }
}
