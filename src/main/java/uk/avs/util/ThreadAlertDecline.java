package uk.avs.util;

import static javax.swing.JOptionPane.showMessageDialog;

public class ThreadAlertDecline  extends Thread {
    public void run(){
        showMessageDialog(null, "редактирование запрещено");
    }

}
