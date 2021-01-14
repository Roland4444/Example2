package uk.avs.util;

import static javax.swing.JOptionPane.showMessageDialog;

public class ThreadAlertApprove extends Thread{
    public void run(){
        showMessageDialog(null, "редактирование разрешено");
    }
}
