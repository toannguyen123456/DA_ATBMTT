package view;
import controller.ShiftCipherController;
import model.ShiftCipherModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
public class ShiftCipherView extends JFrame {
    private ShiftCipherModel model;
    private JComboBox<String> languageComboBox;
    private JButton jButton_1, jButton_2, jButton_3, jButton_4, jButton_5;
    private JTextArea TextArea_1, TextArea_2, TextArea_3, TextArea_4;
    private JTextField jTextField_1, jTextField_2, jTextField_3, jTextField_4, jTextField_5, jTextField_6;
    private int key;
    private String keyTT;
    private String cipherType;
    private int keyA;
    private int keyB;
    private String keyVigenere;
    private String keyHill;
    private int [][] arrHill;
    public ShiftCipherView(String cipherType) {
        this.cipherType = cipherType;
        try {
            this.model = new ShiftCipherModel("EN");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.init();
    }
    public String getCipherType() {
        return cipherType;
    }

    /**
     * Giao diện cho tất cả thuật toán mã hóa cơ bản
     */
    public void init() {
        this.setTitle("Mã Hóa " + this.getCipherType());
        this.setSize(800, 350);
        this.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBackground(new Color(25, 25, 112));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel encryptPanel = createEncryptPanel();
        mainPanel.add(encryptPanel);
        JPanel decryptPanel = createDecryptPanel();
        mainPanel.add(decryptPanel);
        this.add(mainPanel);
        this.setVisible(true);
    }
    /**
     * Hiển thị giao diện bên trái
     * @return
     */
    private JPanel createEncryptPanel() {

        ActionListener actionListener = new ShiftCipherController(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng mã hóa");
        border.setTitleColor(Color.WHITE);
        panel.setBorder(border);
        panel.setBackground(new Color(25, 25, 112));

        JPanel BRPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder BRborder = BorderFactory.createTitledBorder("Bản rõ");
        BRborder.setTitleColor(Color.WHITE);
        BRPanel.setBorder(BRborder);
        BRPanel.setBackground(new Color(25, 25, 112));

        TextArea_1 = new JTextArea(4, 38);
        TextArea_1.setEnabled(true);
        TextArea_1.setEditable(true);
        TextArea_1.setLineWrap(true);
        TextArea_1.setWrapStyleWord(true);
        BRPanel.add(TextArea_1);
        panel.add(BRPanel);

        JPanel keypanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        if(this.getCipherType().equals("Dịch Chuyển") || this.getCipherType().equals("Thay Thế")  || this.getCipherType().equals("Vigenere") || this.getCipherType().equals("Hill")) {
            Label keyLabel = new Label("Khóa ");
            keyLabel.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(keyLabel);

            jTextField_1 = new JTextField(17);
            keypanel.add(jTextField_1);
        }else  if (this.getCipherType().equals("Affine")){

            Label labelA = new Label("a");
            labelA.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(labelA);

            jTextField_3 = new JTextField(10);
            keypanel.add(jTextField_3);

            Label labelB = new Label("b");
            labelB.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(labelB);

            jTextField_4 = new JTextField(10);
            keypanel.add(jTextField_4);
        }
        String[] languages = {"Tiếng Anh", "Tiếng Việt"};
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setSelectedItem("Tiếng Anh");
        languageComboBox.setBackground(new Color(255, 255, 204));

        keypanel.add(languageComboBox);
        panel.add(keypanel);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.setBackground(new Color(25, 25, 112));

        jButton_1 = new JButton("Bắt đầu mã hóa");
        jButton_1.setBackground(new Color(245, 245, 245));
        jButton_1.addActionListener(actionListener);

        jButton_2 = new JButton("Lưu Key");
        jButton_2.setBackground(new Color(245, 245, 245));
        jButton_2.addActionListener(actionListener);

        jButton_3 = new JButton("Tạo Key");
        jButton_3.setBackground(new Color(245, 245, 245));
        jButton_3.addActionListener(actionListener);

        panel1.add(jButton_1);
        panel1.add(jButton_2);
        panel1.add(jButton_3);
        panel.add(panel1);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder rightborder = BorderFactory.createTitledBorder("Bản Mã");
        rightborder.setTitleColor(Color.WHITE);
        rightPanel.setBorder(rightborder);
        rightPanel.setBackground(new Color(25, 25, 112));

        TextArea_2 = new JTextArea(4, 38);
        TextArea_2.setLineWrap(true);
        TextArea_2.setWrapStyleWord(true);
        TextArea_2.setEnabled(true);
        TextArea_2.setEditable(true);
        rightPanel.add(new JScrollPane(TextArea_2));
        panel.add(rightPanel);
        return panel;
    }
    /**
     * Hiển thị sao diện bên phải
     * @return
     */
    private JPanel createDecryptPanel() {
        ActionListener actionListener = new ShiftCipherController(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng giải mã");
        border.setTitleColor(Color.WHITE);
        panel.setBorder(border);

        panel.setBackground(new Color(25, 25, 112));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder BMborder = BorderFactory.createTitledBorder("Bản Mã");
        BMborder.setTitleColor(Color.WHITE);
        leftPanel.setBorder(BMborder);
        leftPanel.setBackground(new Color(25, 25, 112));

        TextArea_3 = new JTextArea(4, 38);
        TextArea_3.setLineWrap(true);
        TextArea_3.setWrapStyleWord(true); //
        TextArea_3.setEnabled(true);
        TextArea_3.setEditable(true);
        leftPanel.add(new JScrollPane(TextArea_3));
        panel.add(leftPanel);

        JPanel keypanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (this.getCipherType().equals("Dịch Chuyển") || this.getCipherType().equals("Thay Thế") || this.getCipherType().equals("Vigenere") || this.getCipherType().equals("Hill")) {

            Label keyLabel = new Label("Khóa ");
            keyLabel.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(keyLabel);

            jTextField_2 = new JTextField(30);
            keypanel.add(jTextField_2);
        }else if (this.getCipherType().equals("Affine")) {

            Label labelA = new Label("a");
            labelA.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(labelA);

            jTextField_5 = new JTextField(10);
            keypanel.add(jTextField_5);

            Label labelB = new Label("b");
            labelB.setForeground(Color.WHITE);
            keypanel.setBackground(new Color(25, 25, 112));
            keypanel.add(labelB);

            jTextField_6 = new JTextField(10);
            keypanel.add(jTextField_6);

        }
        panel.add(keypanel);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.setBackground(new Color(25, 25, 112));
        jButton_4 = new JButton("Bắt đầu giải mã");
        jButton_4.setBackground(new Color(245, 245, 245));
        jButton_4.addActionListener(actionListener);

        jButton_5 = new JButton("Open key");
        jButton_5.setBackground(new Color(245, 245, 245));
        jButton_5.addActionListener(actionListener);

        panel1.add(jButton_4);
        panel1.add(jButton_5);
        panel.add(panel1);

        JPanel BRPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder BRborder = BorderFactory.createTitledBorder("Bản rõ");
        BRborder.setTitleColor(Color.WHITE);
        BRPanel.setBorder(BRborder);
        BRPanel.setBackground(new Color(25, 25, 112));

        TextArea_4 = new JTextArea(4, 38);
        TextArea_4.setLineWrap(true);
        TextArea_4.setWrapStyleWord(true); //
        TextArea_4.setEnabled(true);
        TextArea_4.setEditable(true);
        BRPanel.add(TextArea_4);
        panel.add(BRPanel);
        return panel;
    }
    /**
     * Tạo key mã hóa dịch chuyển
     * @throws Exception nếu xảy ra lỗi khi tạo key
     */
    public void setkey() throws Exception {
        getLanguageTranslator();
        key = model.genKey();
        jTextField_1.setText(String.valueOf(key));
    }
    /**
     * Tạo key mã hóa thay thế
     * @throws Exception nếu xảy ra lỗi khi tạo key
     */
    public void setKeyTT() throws Exception {
        getLanguageTranslator();
        keyTT = model.genKeyTT();
        jTextField_1.setText(keyTT);
    }
    /**
     * Tạo key mã hóa affine
     * @throws Exception nếu xảy ra lỗi khi tạo key
     */
    public void setKeyAffine() throws Exception {
        getLanguageTranslator();
        int alphabetSize = model.getAlphabet().length();
        int [] arr = model.getKeyAB(alphabetSize);
        jTextField_3.setText(String.valueOf(arr[0]));
        jTextField_4.setText(String.valueOf(arr[1]));

    }
    /**
     * Tạo key mã hóa Vigenere
     * @throws Exception nếu xảy ra lỗi khi tạo key
     */
    public void setkeyVigenere () throws Exception {
        getLanguageTranslator();
        String  alphabet = model.getAlphabet();
        keyVigenere = model.getKeyVigenere(alphabet);
        jTextField_1.setText(keyVigenere);
    }
    /**
     * Tạo key mã hóa Hill
     * @throws Exception nếu xảy ra lỗi khi tạo key
     */
    public void setKeyHill () throws Exception {
        getLanguageTranslator();
        keyHill = model.getKeyHill();
        arrHill = model.convertArr(keyHill);
        jTextField_1.setText(keyHill);
    }
    /**
     * Mã hóa dịch chuyển
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void encrypt() throws Exception {
        getLanguageTranslator();
        if (jTextField_1.getText().trim().isEmpty() || TextArea_1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung mã hoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        key = Integer.parseInt(jTextField_1.getText().trim());
        String text = TextArea_1.getText();
        String  alphabet = model.getAlphabet();
        String encrypt = model.encrypt(text, alphabet, key);
        TextArea_2.setText(encrypt);
        TextArea_3.setText(encrypt);
    }
    /**
     * Mã hóa thay thế
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void encryptTT() throws Exception {
        getLanguageTranslator();
        if (jTextField_1.getText().trim().isEmpty() || TextArea_1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung mã hoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        keyTT = jTextField_1.getText().trim();
        String text = TextArea_1.getText();
        String  alphabet = model.getAlphabet();
        String encryptTT = model.encryptTT(text, alphabet, keyTT);
        TextArea_2.setText(encryptTT);
        TextArea_3.setText(encryptTT);
    }
    /**
     * Mã hóa affine
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void encryptAffine() throws Exception {
        getLanguageTranslator();
        if (jTextField_3.getText().trim().isEmpty() || TextArea_1.getText().trim().isEmpty() || jTextField_4.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung mã hoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        keyA = Integer.parseInt(jTextField_3.getText().trim());
        keyB = Integer.parseInt(jTextField_4.getText().trim());
        String text = TextArea_1.getText();
        String  alphabet = model.getAlphabet();
        String encryptAffine = model.encryptAffine(text, keyA, keyB, alphabet);
        TextArea_2.setText(encryptAffine);
        TextArea_3.setText(encryptAffine);

    }
    /**
     * Mã hóa Vigenere
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void encryptVigenere() throws Exception {
        getLanguageTranslator();
        if (jTextField_1.getText().trim().isEmpty() || TextArea_1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung mã hoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String  alphabet = model.getAlphabet();
        String text = TextArea_1.getText();
        String encryptVigenere = model.encrptVigenere(text, alphabet, keyVigenere);
        TextArea_2.setText(encryptVigenere);
        TextArea_3.setText(encryptVigenere);
    }
    /**
     * Mã hóa Hill
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void encryptHill() throws Exception {
        if (jTextField_1.getText().trim().isEmpty() || TextArea_1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung mã hoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String text = TextArea_1.getText();
        String encryptHill = model.encryptHill(text, arrHill);
        TextArea_2.setText(encryptHill);
        TextArea_3.setText(encryptHill);
    }
    /**
     * Giải mã dịch chuyển
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void decrypt() throws Exception{

        if (jTextField_2.getText().trim().isEmpty() || TextArea_3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung giã mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        key = Integer.parseInt(jTextField_2.getText().trim());
        String text = TextArea_3.getText();
        String  alphabet = model.getAlphabet();
        String decrypt = model.decrypt(text, alphabet, key);
        TextArea_4.setText(decrypt);
    }
    /**
     * Giải mã thay thế
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void decryptTT() throws Exception {getLanguageTranslator();
        if (jTextField_2.getText().trim().isEmpty() || TextArea_3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung giã mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        keyTT = jTextField_2.getText().trim();
        String text = TextArea_3.getText();
        String  alphabet = model.getAlphabet();
        String decryptTT = model.decryptTT(text, alphabet, keyTT);
        TextArea_4.setText(decryptTT);
    }
    /**
     * Giải mã affine
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void decryptAffine() throws Exception {

        if (jTextField_5.getText().trim().isEmpty() || TextArea_3.getText().trim().isEmpty() || jTextField_5.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung giải mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        keyA = Integer.parseInt(jTextField_5.getText().trim());
        keyB = Integer.parseInt(jTextField_6.getText().trim());
        String text = TextArea_3.getText();
        String  alphabet = model.getAlphabet();
        String decryptAffine = model.decryptAffine(text, keyA, keyB, alphabet);
        TextArea_4.setText(decryptAffine);
    }
    /**
     * Giải mã vigenere
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void decryptVigenere () {
        if (jTextField_2.getText().trim().isEmpty() || TextArea_3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung giã mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String  alphabet = model.getAlphabet();
        String text = TextArea_3.getText();
        String decryptVigenere = model.decryptVigenere(text, alphabet, keyVigenere);
        TextArea_4.setText(decryptVigenere);
    }
    /**
     * Giải mã hill
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void decryptHill() {
        if (jTextField_2.getText().trim().isEmpty() || TextArea_3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập key hoặc nội dung giã mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String text = TextArea_3.getText();
        int[][] inverseKey = model.inverse(arrHill);
        String decryptHill = model.decryptHill(text, inverseKey);
        TextArea_4.setText(decryptHill);
    }
    /**
     * Lưu key vào file.text
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void saveKey(String str) {
        if (str.equals("Dịch Chuyển") || str.equals("Thay Thế") || str.equals("Vigenere") || str.equals("Hill")) {
            if (jTextField_1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Key chưa được tạo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (str.equals("Affine")) {
            if (jTextField_3.getText().trim().isEmpty() || jTextField_4.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Key a hoặc b chưa được tạo", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        JFileChooser fileChooser  = new JFileChooser();
        fileChooser.setDialogTitle("Lưu Key");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }
            try (FileWriter writer = new FileWriter(fileToSave)) {
                if (str.equals("Dịch Chuyển") || str.equals("Thay Thế") || str.equals("Vigenere") || str.equals("Hill")) {
                    writer.write("Key: " + jTextField_1.getText().trim());
                } else if (str.equals("Affine")) {
                    writer.write("Key a: " + jTextField_3.getText().trim() + "\n");
                    writer.write("Key b: " + jTextField_4.getText().trim());
                }
                JOptionPane.showMessageDialog(this, "Lưu key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Mở key từ file.txt
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void openKey(String str) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Mở key");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                String line;
                if (str.equals("Dịch Chuyển") || str.equals("Thay Thế")  || str.equals("Vigenere") || str.equals("Hill")) {
                    if ((line = reader.readLine()) != null) {
                        String key = line.split(":")[1].trim();
                        jTextField_2.setText(key);
                    }
                } else if (str.equals("Affine")) {
                    String keyA = "";
                    String keyB = "";
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Key a:")) {
                            keyA = line.split(":")[1].trim();
                        } else if (line.startsWith("Key b:")) {
                            keyB = line.split(":")[1].trim();
                        }
                    }
                    jTextField_5.setText(keyA);
                    jTextField_6.setText(keyB);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi mở file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Chọn mã hóa theo tiếng anh hoặc tiếng việt
     * @throws Exception nếu xảy ra lỗi khi mã hóa
     */
    public void getLanguageTranslator() throws Exception {
        String selectedLanguage = (String) languageComboBox.getSelectedItem();
        if (selectedLanguage.equals("Tiếng Việt")) {
            model = new ShiftCipherModel("VI");
        } else {
            model = new ShiftCipherModel("EN");
        }
    }
}
