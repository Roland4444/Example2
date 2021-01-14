package uk.avs.util;


import Message.abstractions.BinaryMessage;
import abstractions.Cypher;
import abstractions.ResponceMessage;
import impl.JAktor;

import javax.swing.*;
import java.io.IOException;

public class ServerAktor extends JAktor {
    private Cypher cypher;

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
        ResponceMessage resp = (ResponceMessage) BinaryMessage.restored(message);
        System.out.println("RECEIVED::=>"+resp.approved);
        if (!resp.approved){
            try {
                ondeclined.declined();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                onapproved.passed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}