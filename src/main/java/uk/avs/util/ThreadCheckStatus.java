package uk.avs.util;

import java.io.IOException;

public class ThreadCheckStatus extends Thread{
    public OnCheckCycle check;
    boolean responced = false;
    public void run(){
        while (!responced){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                check.check();
            } catch (IOException e) {
                System.out.println("SERVER NOT RESPONCED!");
                e.printStackTrace();
            }
            ;
        }
    };
}
