package uk.avs;
import Message.abstractions.BinaryMessage;
import abstractions.Cypher;
import abstractions.RequestMessage;
import ch.roland.ModuleGUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uk.avs.util.*;
import uk.avs.util.readfile.Readfile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Example2 extends ModuleGUI {
    public final String jsonbtoDB = "DB.json";
    public ThreadCheckStatus checker;
    public OnCheckCycle checkcycle;
    public String ID="";
    public final String version = "0.0.0.6.R8 Release";
    public final String approve_lock = "ap.lock";
    public final String decline_lock = "de.lock";
    public final String applock = "app.lock";
    public final String req_lock = "request.lock";
    public final String wait_lock = "wait.lock";
    public ServerAktor akt;
    public String urlServer;
    public String urlClient;
    AbstractAction createinitialrequest;
    AbstractAction saveChanges;
    AbstractAction editAction;
    AbstractAction cancelAction;
    public String cancelaction = "cancelaction";
    public String cancelaction_shortcut = "control X";
    public String checkaction = "checkaction";
    public String checkaction_shortcut = "control Z";
    public String createandsendfatbundle = "createfatbundle";
    public String createfatbundle_shortcut = "control R";
    public timeBasedUUID UUIDGen ;
    public String pzuInfo;

  ///  public final String FileNameDump  = "waybill.bin";
    public final static String FileNameDumpJSON  = "waybill.json";
    public final String FileBin  = "req.bin";
    public String savechanges = "saveChanges";
    public String savechanges_shortcut = "control S";
    public JPanel DescriptionPanel;
    public JTextArea DescriptionText;
    public JButton RequestHelp;
    public JButton Cancel;
    public JButton SaveChanges;
    public JButton EditButton;
    public Box contents;
  //  ExchangeView restored;
    public JSONObject restored;
    public JPanel MainPanel;
    public JPanel ButtonPanel;
    public JPanel InfoPanel;
    public JLabel lPosition;
    public JLabel lDescription;
    public JTable PositionTable;
    public JScrollPane pane;
    public JSONizer jsonizer;
    private Readfile readfile;
    JPopupMenu popupMenu;
    public ArrayList metals;


    FlowLayout experimentLayout;
    public Object[] columnsHeaderAVS = new String[]{"Дата", "Время", "Накладная №", "Комментарий", "Металл", "Брутто", "Тара", "Засор", "Примеси", "Нетто", "Режим", "Завершено", "Состояние"};

    public void defaultmetals() throws IOException {
        metals = new ArrayList();
        String line;
        BufferedReader bufferreader = new BufferedReader(new FileReader("metals"));
        while ((line = bufferreader.readLine()) != null) {
            metals.add(line);
        }
    }
    public void cleanup(){
        Utils.safeDelete(FileNameDumpJSON);
        Utils.safeDelete(approve_lock);
        Utils.safeDelete(req_lock);
        Utils.safeDelete(wait_lock);
        Utils.safeDelete(decline_lock);
    }

    public boolean waitReponce(){
        return new File(wait_lock).exists();
    }

    public void initComponents() throws IOException, ParseException {
        if (new File(wait_lock).exists()){
            ID = new String(Files.readAllBytes(Paths.get(wait_lock)));
        }
        UUIDGen = new timeBasedUUID();
        defaultmetals();
        System.out.println("Metals passed!");
        jsonizer = new JSONizer();
        readfile = new Readfile("setts.ini");
        pzuInfo= "Неизвестная площадка";
        if  (readfile.readField("pzu")!=null)
            pzuInfo =readfile.readField("pzu");
        urlServer = readfile.readField("urlServer");
        urlClient = readfile.readField("urlClient");
        if ((urlClient == null) || urlServer==null){
            showMessageDialog(null, "Ошибка инициализации программы");
            System.exit(12);
        }
      ///  showMessageDialog(null, "URL client::"+urlClient);
        System.out.println(urlClient);
        frame = new JFrame("АВС помошник. Версия "+version);
        System.out.println("JFRAME passed!");
           // restored = WayBillUtil.restoreBytesToWayBill(FileNameDump);
        restored = WayBillUtil.restoreJSON(FileNameDumpJSON);
        System.out.println("Restore JSON passed!");

        ////////////ID = restored.get("Date").toString() + restored.get("Time").toString() + restored.get("Comment").toString();

        PositionTable = new JTable(WayBillUtil.dataFromObject(restored), columnsHeaderAVS);

        contents = new Box(BoxLayout.Y_AXIS);
        lPosition = new JLabel("Позиция:");
        lDescription = new JLabel("Опишите проблему");
        DescriptionText = new JTextArea();

        DescriptionText.setWrapStyleWord(true);
        DescriptionText.setLineWrap(true);
        pane = new JScrollPane(PositionTable);
        ButtonPanel = new JPanel(new BorderLayout());
        RequestHelp = new JButton("Запросить изменения");
        SaveChanges = new JButton("Записать изменения");
        EditButton = new JButton("Редактировать");

        Cancel = new JButton("Отмена");
        DescriptionPanel = new JPanel();
        experimentLayout = new FlowLayout();
        InfoPanel = new JPanel(new BorderLayout());
        popupMenu = new JPopupMenu();
        JMenuItem menuItemAdd = new JMenuItem("Add New Row");
        JMenuItem menuItemRemove = new JMenuItem("Remove Current Row");
        JMenuItem menuItemRemoveAll = new JMenuItem("Remove All Rows");

        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemRemoveAll);

        checkcycle = new OnCheckCycle() {
            @Override
            public void check() throws IOException {
                System.out.println("CHECK CHECK");
                askServer();
            }
        };

    }

    public Example2() throws IOException, InterruptedException, ParseException {
        if (Utils.getID(FileNameDumpJSON, "Weighing_id")==null){
            showMessageDialog(null, "Неверный формат файла");
            System.exit(5);
        }
        if (new File(applock).exists()){
            System.exit(3);
        }
        initComponents();

        FileOutputStream fos = new FileOutputStream(applock);
        fos.write("schon".getBytes());
        fos.close();

    }
    public void disableEdit(){
        SaveChanges.setEnabled(false);
        EditButton.setEnabled(false);
    };

    public void enableEdit(){
        SaveChanges.setEnabled(true);
        EditButton.setEnabled(true);
    };

    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        disableEdit();
        contents.add(lPosition);
        PositionTable.setRowHeight(40);

        pane.setMaximumSize(new Dimension(1300, 100));
        pane.setPreferredSize(new Dimension(1300, 100));
        pane.setMinimumSize(new Dimension(1300, 100));
        DescriptionText.append("Обязательно напишите кто вы и с какой площадки");
        ButtonPanel.setLayout(experimentLayout);
        ButtonPanel.add(RequestHelp);
     //   ButtonPanel.add(Cancel);
        ButtonPanel.add(SaveChanges);
        ButtonPanel.add(EditButton);

        SaveChanges.setVisible(false);

        DescriptionText.setRows(20);
        DescriptionText.setColumns(10);

        contents.add(pane);
        contents.add(lDescription);
        contents.add(new JScrollPane(DescriptionText));
        contents.add(ButtonPanel);
        contents.add(InfoPanel);
        frame.setContentPane(contents);
        frame.setSize(1200, 500);
        frame.setVisible(true);
        PositionTable.setComponentPopupMenu(popupMenu);

        if (waitReponce()){
            startASK();
        }
    }

    public void startASK(){
        disableEdit();
        RequestHelp.setEnabled(false);
        System.out.println("CHECK STATUS NOW");
        checker = new ThreadCheckStatus();
        checker.check = checkcycle;
        checker.start();
    }

    public void askServer() throws IOException {
        RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), null);
        req.type = RequestMessage.Type.ask;
        String ID__ = new String(Files.readAllBytes(Paths.get(wait_lock)));
        req.addressToReply = akt.getURL_thisAktor();
        req.ID = ID__;
        akt.send(BinaryMessage.savedToBLOB(req), urlServer);
        System.out.println("ASK server @ GUUID=> "+ ID__);
    };

    public void initListeners() {
        RequestHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(createfatbundle_shortcut), createandsendfatbundle);
        RequestHelp.getActionMap().put(createandsendfatbundle, createinitialrequest);
        RequestHelp.addActionListener(createinitialrequest);

        SaveChanges.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(savechanges_shortcut), savechanges);
        SaveChanges.getActionMap().put(savechanges, saveChanges);
        SaveChanges.addActionListener(saveChanges);

        EditButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(checkaction_shortcut), checkaction);
        EditButton.getActionMap().put(checkaction, editAction);
        EditButton.addActionListener(editAction);

        Cancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(cancelaction_shortcut), cancelaction);
        Cancel.getActionMap().put(cancelaction, cancelAction);
        Cancel.addActionListener(cancelAction);

    }

    void createFile(String filename, String GUUID) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(GUUID.getBytes());
        fos.close();
    }

    public void saveChanges(){
        System.out.println("Description::=>" + DescriptionText.getText());
        //    JOptionPane.showMessageDialog(null, "Сохраняю измнения");
        ArrayList data = new ArrayList();
        ////showMessageDialog(null, "Save Changes!!");
        PositionTable.updateUI();
        for (int i = 0; i <= 12; i++) {
            System.out.println(i + "index@Value::" + PositionTable.getModel().getValueAt(0, i));
            data.add(PositionTable.getModel().getValueAt(0, i));
        }
        RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), jsonizer.JSONedRestored(data));
        req.type = RequestMessage.Type.update;
        try {
            req.addressToReply = akt.getURL_thisAktor();
        } catch (UnknownHostException p) {
            p.printStackTrace();
        }
        try {
            akt.send(BinaryMessage.savedToBLOB(req), urlServer);

            showMessageDialog(null, "Для обновления нажмите Поиск в основной программе");
            Example2.writeJSONtoDB( jsonizer.JSONedRestored(data), jsonbtoDB, Utils.getID(FileNameDumpJSON, "Weighing_id"));
            cleanup();
            cleanAndexit();
            //    akt.terminate();
        } catch (IOException | ParseException ioException) {
            ioException.printStackTrace();
        }
    };




    public static void writeJSONtoDB(String json, String filename, String id) throws IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);
        obj.put("Weighing_id", id);
        FileOutputStream fos = new FileOutputStream(filename);
        ////    if (System.getProperty("os.name").equals("Linux"))
        fos.write(obj.toString().getBytes("UTF-8"));

        ///    else
        ///        fos.write(json.getBytes("windows-1251"));
        fos.close();
    } ;

    public void cleanAndexit(){
      //  showMessageDialog(null, "Try exit");
        if (new File(wait_lock).exists()){
            showMessageDialog(null, "Запрос отправлен, отмена невозможна");
            return;
        }
        Utils.safeDelete(approve_lock);
       // Utils.safeDelete(req_lock);
        Utils.safeDelete(applock);
        Utils.safeDelete(FileNameDumpJSON);
        Utils.safeDelete(wait_lock);
        akt.terminate();
        frame.dispose();
        System.exit(1);
    }

    public void initActions() {
        cancelAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanAndexit();
            }
        };
        editAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PositionTable.updateUI();
                StringBuffer bf = new StringBuffer();
                for (int i = 0; i <= 12; i++) {
                    bf.append("Position #" + i + "data:: " + PositionTable.getModel().getValueAt(0, i) + "\n");
                }
                restored = null;
                try {
                    restored = WayBillUtil.restoreJSON(FileNameDumpJSON);
                } catch (IOException | ParseException exception) {
                    exception.printStackTrace();
                }
                Editor editor = new Editor(String.valueOf(restored.get("Waybill_number")), restored.get("Date").toString(), metals, SaveChanges);
                editor.positiontable = PositionTable;
                editor.callback = new Callback() {
                    @Override
                    public void call() {
                        saveChanges();
                    }
                };
                ArrayList data = new ArrayList<>();
                editor.inputdata = data;
                data.add(restored.get("Comment"));
                data.add(restored.get("Brutto"));
                data.add(restored.get("Netto"));
                data.add(restored.get("Clogging"));
                data.add(restored.get("Trash"));
                data.add(restored.get("Tara"));
                data.add(restored.get("Metall"));
                try {
                    editor.preperaGUI();
                    editor.pasteData();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }

            }
        };

        saveChanges = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                cleanAndexit();
            };
        };
        createinitialrequest = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent f) {
                restored = null;
                try {
                    restored = WayBillUtil.restoreJSON(FileNameDumpJSON);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                if (DescriptionText.getText().length()>=3000){
                    showMessageDialog(null, "слишком длинный текст. Ограничение 3000 символов");
                    return;
                }
                restored.put("pzu", pzuInfo);
                System.out.println("Description::=>" + DescriptionText.getText());
                ID = UUIDGen.generate();
                RequestMessage req = new RequestMessage(ID, DescriptionText.getText(), jsonizer.JSONedRestored(restored));
                System.out.println("\n\n\nJSON to send::" + req.JSONed);
                req.Description = DescriptionText.getText();
                System.out.println("DESCRIPTION>>\n"+DescriptionText.getText());
                req.type = RequestMessage.Type.request;
             //   showMessageDialog(null, "TRY SEND" );
                req.addressToReply = urlClient;///////////akt.getURL_thisAktor();
          //////      System.out.println("ADRESS TO REPLY::"+req.addressToReply);
             //////   showMessageDialog(null, "Address to reply::"+req.addressToReply);
                try {
                    akt.send(BinaryMessage.savedToBLOB(req), urlServer);
                } catch (UnknownHostException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                } catch (IOException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                } catch (CompletionException e) {
                    showMessageDialog(null, "ВОЗНИКЛА ОШИБКА ПРИ ОТПРАВКЕ => ПРОВЕРЬТЕ СЕТЕВЫЕ НАСТРОЙКИ\n" + e);
                    RequestHelp.setEnabled(true);
                }
                try {
                    createFile(wait_lock, ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setState(JFrame.ICONIFIED);
                showMessageDialog(null, "запрос отправлен! ожидайте одобрения");
                RequestHelp.setEnabled(false);

                startASK();
            }
        };
        initListeners();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (!new File(wait_lock).exists()) {
                    cleanAndexit();
                    return;
                }
                Utils.safeDelete(applock);
                akt.terminate();
            }
        });
    }

    public void onjaktorPassed() throws IOException {
        enableEdit();
        FileOutputStream fos = new FileOutputStream(approve_lock);
        fos.write("schon".getBytes());
        fos.close();
        frame.setState(JFrame.NORMAL);
        showMessageDialog(null, "редактирование разрешено");

        //new ThreadAlertApprove().start();
        checker.interrupt();
        checker.stop();
    };

    public void onjaktorDecline() throws IOException {
        FileOutputStream fos = new FileOutputStream(decline_lock);
        fos.write("schon".getBytes());
        fos.close();
        cleanup();
        showMessageDialog(null, "редактирование запрещено");
        //new ThreadAlertApprove().start();
        checker.interrupt();
        checker.stop();
        //new ThreadAlertDecline().start();
    }

    public void prepareAktor() throws InterruptedException {
        akt = new ServerAktor();
        akt.editButton = EditButton;
        akt.setAddress(urlClient);//////////////akt.setAddress("http://127.0.0.1:12215/");
        akt.setCypher((Cypher) new CypherImpl());
        System.out.println("\n\n\n*************************\n****Spawning JAKtor******\n*************************\n\n\n\n");

        akt.activateGUI =  new enableLambda() {
            @Override
            public void activate() {

            }
        };

        akt.ondeclined = new OnDeclined() {
            @Override
            public void declined() throws IOException, InterruptedException {
                onjaktorDecline();
            }
        };
        akt.onapproved = new OnApproved() {
            @Override
            public void passed() throws IOException, InterruptedException {
                onjaktorPassed();
            }
        };
        akt.spawn();

    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException, IOException, ParseException {
        Example2 ex = new Example2();
        ex.prepareAktor();
        ex.preperaGUI();
        ex.initActions();

        System.out.println("URL ::>>>>"+ex.akt.getURL_thisAktor());
    }


    public class CypherImpl implements Cypher {
        @Override
        public byte[] decrypt(byte[] input) {
            return input;
        }
        @Override
        public byte[] encrypt(byte[] input) {
            return input;
        }
    }




};
