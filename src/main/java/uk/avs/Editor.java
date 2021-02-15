package uk.avs;
import ch.roland.ModuleGUI;
import uk.avs.util.Callback;
import uk.avs.util.Checker;
import uk.avs.util.Utils;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Editor extends ModuleGUI {
    public Checker checker;
    public Callback callback;
    public JPanel CommentPanel, CommentLabelPanel, CommentTextPanel,
            BruttoPanel, BruttoLabelPanel, BruttoTextPanel,
            NettoPanel, NettoLabelPanel, NettoTextPanel,
            CloggingPanel, CloggingLabelPanel, CloggingTextPanel,
            TrashPanel, TrashLabelPanel, TrashTextPanel,
            TaraPanel, TaraLabelPanel, TaraTextPanel,
            MetalPanel, MetalLabelPanel, MetalItemPanel;
    public JTextField Comment;
    public JLabel Ldata;
    public JLabel LComment;
    public JTextField Brutto;
    public JLabel LBrutto;
    public JTextField Netto;
    public JLabel LNetto;
    public JTextField Clogging;
    public JLabel LClogging;
    public JTextField Trash;
    public JLabel LTrash;
    public JTextField Tara;
    public JLabel LTara;
    public JComboBox Metal;
    public JLabel LMetal;
    public JButton UpdateComment;
    public JPanel containerPanel;
    public JTable positiontable;
    AbstractAction updateAction;
    public String updateaction = "checkaction";
    JButton Save;
    public JLabel description;
    public JPanel buttonPanel;
    public String updateaction_shortcut = "control Z";
    public ArrayList inputdata;
    private String[] data1 = { "Чай" ,"Кофе"  ,"Минеральная","Морс", "Алюминий хлам"};
    public ArrayList metals;
    public JButton saver;
    public String errorDescription;
    public boolean loaded = false;
    public Editor(String number, String date, ArrayList data1, JButton saver){
        checker = new Checker();
        this.saver = saver;
        saver.setEnabled(true);
        CommentPanel = new JPanel(new GridLayout());
        CommentLabelPanel = new JPanel(new BorderLayout());
        CommentTextPanel = new JPanel(new BorderLayout());

        BruttoPanel= new JPanel(new GridLayout());
        BruttoLabelPanel = new JPanel(new BorderLayout());
        BruttoTextPanel = new JPanel(new BorderLayout());

        NettoPanel= new JPanel(new GridLayout());
        NettoLabelPanel = new JPanel(new BorderLayout());
        NettoTextPanel = new JPanel(new BorderLayout());

        CloggingPanel= new JPanel(new GridLayout());
        CloggingLabelPanel = new JPanel(new BorderLayout());
        CloggingTextPanel = new JPanel(new BorderLayout());

        TaraPanel= new JPanel(new GridLayout());
        TaraLabelPanel = new JPanel(new BorderLayout());
        TaraTextPanel = new JPanel(new BorderLayout());

        TrashPanel= new JPanel(new GridLayout());
        TrashLabelPanel = new JPanel(new BorderLayout());
        TrashTextPanel = new JPanel(new BorderLayout());

        MetalPanel = new JPanel(new GridLayout());
        MetalLabelPanel = new JPanel(new BorderLayout());
        MetalItemPanel = new JPanel(new BorderLayout());

        buttonPanel =  new JPanel();

        frame = new JFrame("Накладная номер #"+ number+ " @"+ date);
        Comment = new JTextField("",15);
        LComment = new JLabel("Комментарий   ");
        UpdateComment = new JButton("Обновить");
        containerPanel = new JPanel();
        description = new JLabel("Накладная номер #"+ number+ " @"+ date);
        GridLayout gr = new GridLayout(12,1);
        gr.setHgap(2);
        gr.setVgap(2);
        containerPanel.setLayout(gr);
        LBrutto = new JLabel("Брутто   ");
        String[] array = new String[data1.size()];
        for (int i=0; i<data1.size(); i++){
            array[i]= (String) data1.get(i);
        }
        Metal = new JComboBox(array);
        Brutto = new JTextField("",15);
        Netto = new JTextField("",15);
        LNetto = new JLabel("Нетто   ");
        Clogging = new JTextField("",15);
        LClogging  = new JLabel("Засор   ");
        Trash = new JTextField("",15);
        LTrash = new JLabel("Примеси   ");
        Tara = new JTextField("",15);
        LTara = new JLabel("Тара   ");
        LMetal = new JLabel("Металл   ");

        metals = data1;
        Save = new JButton("Сохранить");
     //   springLayoutPanel = new JPanel(new SpringLayout());

    }

    public Editor(String[] data){
        frame = new JFrame();
        Comment = new JTextField();
        LComment = new JLabel("Комментарий");
        UpdateComment = new JButton("Обновить");
        containerPanel = new JPanel();
        Metal = new JComboBox(data);
    }


    public static float calculateNetto(float trash, float brutto, float tara, float clogging){
        float sub = brutto-tara-trash;
        float percentage = (float) (clogging/100.00*sub);
        float netto = sub-percentage;
        return new Float(Utils.trimApply(String.valueOf(netto)));
    };

    @Override
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        CommentTextPanel.add(Comment, BorderLayout.WEST);
        CommentLabelPanel.add(LComment, BorderLayout.EAST);
        CommentPanel.add(CommentLabelPanel);
        CommentPanel.add(CommentTextPanel);

        BruttoLabelPanel.add(LBrutto, BorderLayout.EAST);
        BruttoTextPanel.add(Brutto, BorderLayout.WEST);
        BruttoPanel.add(BruttoLabelPanel);
        BruttoPanel.add(BruttoTextPanel);

        NettoLabelPanel.add(LNetto, BorderLayout.EAST);
        NettoTextPanel.add(Netto, BorderLayout.WEST);
        NettoPanel.add(NettoLabelPanel);
        NettoPanel.add(NettoTextPanel);

        CloggingLabelPanel.add(LClogging, BorderLayout.EAST);
        CloggingTextPanel.add(Clogging, BorderLayout.WEST);
        CloggingPanel.add(CloggingLabelPanel);
        CloggingPanel.add(CloggingTextPanel);

        TrashLabelPanel.add(LTrash, BorderLayout.EAST);
        TrashTextPanel.add(Trash, BorderLayout.WEST);
        TrashPanel.add(TrashLabelPanel);
        TrashPanel.add(TrashTextPanel);

        TaraLabelPanel.add(LTara, BorderLayout.EAST);
        TaraTextPanel.add(Tara, BorderLayout.WEST);
        TaraPanel.add(TaraLabelPanel);
        TaraPanel.add(TaraTextPanel);

        MetalLabelPanel.add(LMetal, BorderLayout.EAST);
        MetalItemPanel.add(Metal, BorderLayout.WEST);
        MetalPanel.add(MetalLabelPanel);
        MetalPanel.add(MetalItemPanel);

        buttonPanel.add(Save);

        containerPanel.add(CommentPanel);
        containerPanel.add(BruttoPanel);
        containerPanel.add(NettoPanel);
        containerPanel.add(CloggingPanel);
        containerPanel.add(TrashPanel);
        containerPanel.add(TaraPanel);
        containerPanel.add(MetalPanel);

        containerPanel.add(buttonPanel);

        frame.setContentPane(containerPanel);
        frame.setSize(500, 450);
        frame.setVisible(true);
        initActions();
    }

    @Override
    public void initListeners() {
        UpdateComment.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(updateaction_shortcut), updateaction);
        UpdateComment.getActionMap().put(updateaction, updateAction);
        UpdateComment.addActionListener(updateAction);

        Save.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(updateaction_shortcut), updateaction);
        Save.getActionMap().put(updateaction, updateAction);
        Save.addActionListener(updateAction);

    }

    public void pasteData(){
        Comment.setText(inputdata.get(0).toString());
        Brutto.setText(inputdata.get(1).toString());
        Netto.setText(inputdata.get(2).toString());
        Clogging.setText(inputdata.get(3).toString());
        Trash.setText(inputdata.get(4).toString());
        Tara.setText(inputdata.get(5).toString());
        Metal.setSelectedItem(inputdata.get(6).toString());
        loaded=true;
    };

    public boolean checkInput(){
        if (!checker.isnumber(Brutto.getText())) {
            errorDescription = "в поле Брутто";
            return false;
        }
        if (!checker.isnumber(Tara.getText())) {
            errorDescription = "в поле Тара";
            return false;
        }
        if (!checker.isnumber(Clogging.getText())) {
            errorDescription = "в поле Засор";
            return false;
        }
        if (!checker.isnumber(Trash.getText())) {
            errorDescription = "в поле Примесь";
            return false;
        }
        if (!checker.isnumber(Netto.getText())) {
            errorDescription = "в поле Нетто";
            return false;
        }
        float clogging = Float.parseFloat(Clogging.getText());
        if (clogging>100){
            errorDescription = "Засор больше 100!";
            return false;
        };
        float trash = Float.parseFloat(Trash.getText());
        float brutto = Float.parseFloat(Brutto.getText());
        float tara = Float.parseFloat(Tara.getText());
        //public static float calculateNetto(float trash, float brutto, float tara, float clogging){
        if (calculateNetto(trash, brutto, tara, clogging)<0){
            errorDescription = "Нетто меньше или равно 0!";
            return false;
        }
        if (brutto<(tara+trash)){
            errorDescription = "брутто меньше тара + примесь!";
            return false;
        }
        if (Comment.getText().length()>60){
            errorDescription = "Слишком длинный комментарий. Ограничение 60 символов";
            return false;
        }
        return true;
    };

    public void update(){
        if (!checkInput()) {
            JOptionPane.showMessageDialog(null, "Проверьте ввод чисел. Ошибка ::  "+errorDescription);
            return;
        }
        positiontable.setValueAt(Comment.getText(), 0, 3);
        positiontable.setValueAt(Metal.getSelectedItem(), 0, 4);
        positiontable.setValueAt(Utils.trimApply(Brutto.getText()), 0, 5);
        positiontable.setValueAt(Utils.trimApply(Tara.getText()), 0, 6);
        positiontable.setValueAt(Utils.trimApply(Clogging.getText()), 0, 7);
        positiontable.setValueAt(Utils.trimApply(Trash.getText()), 0, 8);
        float trash = Float.parseFloat(Trash.getText());
        float clogging = Float.parseFloat(Clogging.getText());
        float brutto = Float.parseFloat(Brutto.getText());
        float tara = Float.parseFloat(Tara.getText());
        float netto = Editor.calculateNetto(trash, brutto, tara, clogging);
        positiontable.setValueAt(Utils.trimApply(String.valueOf(netto)), 0, 9);
        positiontable.updateUI();
    ///    JOptionPane.showMessageDialog(null, "Для обновления нажмите Поиск в основной программе");
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        callback.call();
    }

    public void recalculateNetto(){
        if (!loaded)
            return;
        float trash = 0;
        float clogging = 0;
        float brutto = 0;
        float tara = 0;
        try{
            trash = Float.parseFloat(Trash.getText());
            clogging = Float.parseFloat(Clogging.getText());
            brutto = Float.parseFloat(Brutto.getText());
            tara = Float.parseFloat(Tara.getText());
        }
        catch (NumberFormatException e){

        }
        float netto = Editor.calculateNetto(trash, brutto, tara, clogging);
        Netto.setText(Utils.trimApply(String.valueOf(netto)));
        positiontable.setValueAt(Utils.trimApply(String.valueOf(netto)), 0, 9);
        positiontable.updateUI();
    };

    @Override
    public void initActions() {
        Tara.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalculateNetto();
            }
        });
        Trash.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalculateNetto();
            }
        });
        Clogging.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalculateNetto();
            }
        });
        Brutto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recalculateNetto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recalculateNetto();
            }
        });
        updateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //    JOptionPane.showMessageDialog(null, "NETTO::"+Netto.getText());
            update();

            }
        };

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Hiding window!!!!");
                frame.setVisible(false);
            }
        });

        initListeners();
    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new Editor("  3   ", "  26.11.2020   ", null, null).preperaGUI();
    }
}
