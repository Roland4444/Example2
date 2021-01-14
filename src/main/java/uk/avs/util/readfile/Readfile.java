package uk.avs.util.readfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Readfile {
    private String filename;
    public Readfile(String filename){
        this.filename = filename;
        if (!new File(filename).exists()) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");

        }
    }
    public ArrayList read(){
        ArrayList result = new ArrayList();
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            fprop = new FileInputStream(filename);
            property.load(fprop);
            result.add(property.getProperty("ip"));
            result.add(property.getProperty("db"));
            result.add(property.getProperty("login"));
            result.add(property.getProperty("pass"));
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return result;
    }
    public String readField(String field){
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            fprop = new FileInputStream(filename);
            property.load(fprop);
            return property.getProperty(field);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }
    }

    public String AktorPORT(){
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            fprop = new FileInputStream(filename);
            property.load(fprop);
            return property.getProperty("AktorPORT");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }
    };

    public String FTP(){
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            fprop = new FileInputStream(filename);
            property.load(fprop);
            return property.getProperty("FTP");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return null;
        }
    }



}
