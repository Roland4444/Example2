package uk.avs.util;


import Message.abstractions.BinaryMessage;
import abstractions.Cypher;
import abstractions.InfoMessage;
import abstractions.ResponceMessage;
import impl.JAktor;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerAktor extends JAktor {
    private Cypher cypher;
    public String info = "info";
    public JButton editButton;
    public OnApproved onapproved;
    public OnDeclined ondeclined;
    public enableLambda activateGUI;
    public void setCypher(Cypher cypher) {
        this.cypher = cypher;
    }

    @Override
    public int send(byte[] message, String address) throws IOException {
        return this.client.send(this.cypher.encrypt(message), address);
    }

    @Override
    public void receive(byte[] message_) throws IOException {

        byte[] message = cypher.decrypt(message_);
        BinaryMessage resp__ =  BinaryMessage.restored(message);
        if (resp__ instanceof ResponceMessage) {
        if (new File(info).exists())
                   new File(info).delete();
            ResponceMessage resp = (ResponceMessage) resp__;

            System.out.println("RECEIVED::=>" + resp.approved);
            if (!resp.approved) {
                try {
                    ondeclined.declined();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    onapproved.passed();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (resp__ instanceof InfoMessage) {
            InfoMessage info__ = (InfoMessage) resp__;

            System.out.println("RECEIVED INFO::=>" + info__.counter);
            PrintWriter pw = new PrintWriter(info);
            pw.println(info__.counter);
            pw.close();
        }

    }
}