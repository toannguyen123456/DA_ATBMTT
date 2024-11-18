package view;

import controller.AlgorithmController;
import controller.SymmetricCipherController;
import model.SymmetricCipherModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmetricCipherView extends JFrame {
    private SymmetricCipherModel model;
    private IvParameterSpec iv;
    private String cipherType;
    private JComboBox<String> languageComboBox, paddingComboBox;
    private JTextArea textArea1, textArea2, textAre3, textArea4;
    private JTextField jTextFieldKey, jTextFieldIV;
    private Label labelIV, labelMode, paddingMode;
    private boolean isFileMode = false;
    private JPanel jPanelBRL, jPanelBRR;
    private JButton fileButtonEcr, fileButtonDecr, jButtonL, jButtonR, buttonIV;
    public SymmetricCipherView(String cipherType){

        this.cipherType = cipherType;
        this.model = new SymmetricCipherModel();
        this.init();
    }



    public String getCipherType() {
        return cipherType;
    }

    private void init() {
        if("DESede".equals(this.getCipherType())) {
            this.setTitle("Mã Hóa " + "3DES");
        }else {
            this.setTitle("Mã Hóa " + this.getCipherType());
        }

        this.setSize(950, 500);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel jPanelLeft = new JPanel();

        ActionListener actionListener = new SymmetricCipherController(this);

        jPanelLeft.setLayout(new BoxLayout(jPanelLeft, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng mã hóa");
        border.setTitleColor(Color.WHITE);
        jPanelLeft.setBorder(border);
        jPanelLeft.setBackground(new Color(25, 25, 112));

        jPanelBRL = new JPanel(new BorderLayout());
        TitledBorder borderBRL = BorderFactory.createTitledBorder("bản rõ");
        jPanelBRL.setBorder(new CompoundBorder(borderBRL, new EmptyBorder(10, 10, 10, 10)));

        textArea1 = new JTextArea(7, 25);
        jPanelBRL.add(textArea1);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        JPanel jPanelBT = new JPanel(new BorderLayout());
        jButtonL = new JButton("Bắt đầu mã hóa");
        jButtonL.addActionListener(actionListener);
        jPanelBT.add(jButtonL);
        jPanelBT.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel jPanelBML = new JPanel(new BorderLayout());
        TitledBorder borderBML = BorderFactory.createTitledBorder("bản mã");
        jPanelBML.setBorder(new CompoundBorder(borderBML, new EmptyBorder(10, 10, 10, 10)));

        textArea2 = new JTextArea(7, 25);
        jPanelBML.add(textArea2);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        jPanelLeft.add(jPanelBRL);
        jPanelLeft.add(Box.createVerticalStrut(10));
        jPanelLeft.add(jPanelBT);
        jPanelLeft.add(Box.createVerticalStrut(10));
        jPanelLeft.add(jPanelBML);

        JPanel jPanelC = new JPanel(new GridLayout(3, 1));

        JPanel jPanelR = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelMode = new Label("Mode");
        paddingMode = new Label("Padding");

        String[] languages = {"ECB","CBC", "CFB"};
        String[] paddings = {"PKCS5Padding", "ISO10126Padding", "NoPadding"};

        languageComboBox = new JComboBox<>(languages);
        paddingComboBox = new JComboBox<>(paddings);
        languageComboBox.setSelectedItem("ECB");
        paddingComboBox.setSelectedItem("PKCS5Padding");

        JButton jButtonMHF = new JButton("Mã hóa File");
        jButtonMHF.addActionListener(e -> toggleFileMode());


        JButton jButtonReset = new JButton("Reset");
        jButtonReset.addActionListener(actionListener);
        JButton saveKeyIV = new JButton("Save Key IV");
        saveKeyIV.addActionListener(actionListener);
        JButton openKeyIV = new JButton("Open Key IV");
        openKeyIV.addActionListener(actionListener);


        jPanelC.add(jPanelR);


        JPanel jPanelKey = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Label labelKey = new Label("Key");
        jTextFieldKey = new JTextField(25);
        JButton jButtonKey = new JButton("Tạo Key");
        jButtonKey.addActionListener(actionListener);

        labelIV = new Label("IV");
        buttonIV = new JButton("Tạo IV");
        buttonIV.addActionListener(actionListener);
        jTextFieldIV = new JTextField(15);

        jPanelKey.add(labelKey);
        jPanelKey.add(jTextFieldKey);
        jPanelKey.add(jButtonKey);
        jPanelC.add(jPanelKey);

        JPanel jPanelIV = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelIV.add(labelIV);
        jPanelIV.add(jTextFieldIV);
        jPanelIV.add(buttonIV);
        labelIV.setText("Nonce");
        labelIV.setVisible(false);
        jTextFieldIV.setVisible(false);
        buttonIV.setVisible(false);
        jPanelKey.add(jPanelIV);

        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton  jButton = new JButton("Tạo Nonce");
        jButton.addActionListener(actionListener);
        jPanel.add(jButton);

        if(this.getCipherType().equals("ChaCha20")){
            jPanelR.add(jButtonReset);
            jPanelR.add(jButtonMHF);
            labelIV.setVisible(true);
            jTextFieldIV.setVisible(true);
            jPanelIV.add(jPanel);
        }else {
            jPanelR.add(labelMode);
            jPanelR.add(languageComboBox);
            jPanelR.add(paddingMode);
            jPanelR.add(paddingComboBox);
            jPanelR.add(jButtonReset);
            jPanelR.add(jButtonMHF);
            jPanelR.add(saveKeyIV);
            jPanelR.add(openKeyIV);
        }



        languageComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleIVField();
            }
        });

        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout(new BoxLayout(jPanelRight, BoxLayout.Y_AXIS));
        TitledBorder borderGM = BorderFactory.createTitledBorder("Chức năng giải mã");
        borderGM.setTitleColor(Color.WHITE);
        jPanelRight.setBorder(borderGM);
        jPanelRight.setBackground(new Color(25, 25, 112));

        jPanelBRR = new JPanel(new BorderLayout());
        TitledBorder borderBRR = BorderFactory.createTitledBorder("bản rõ");
        jPanelBRR.setBorder(new CompoundBorder(borderBRR, new EmptyBorder(10, 10, 10, 10)));

        textAre3 = new JTextArea(7, 25);
        jPanelBRR.add(textAre3);
        textAre3.setLineWrap(true);
        textAre3.setWrapStyleWord(true);

        JPanel jPanelBTR = new JPanel(new BorderLayout());
        jButtonR = new JButton("Bắt đầu giải mã");
        jButtonR.addActionListener(actionListener);
        jPanelBTR.setBorder(new CompoundBorder(borderBML, new EmptyBorder(0, 10, 0, 10)));
        jPanelBTR.add(jButtonR);
        jPanelBTR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel jPanelBMR = new JPanel(new BorderLayout());
        TitledBorder borderBMR = BorderFactory.createTitledBorder("bản mã");
        jPanelBMR.setBorder(new CompoundBorder(borderBMR, new EmptyBorder(10, 10, 10, 10)));

        textArea4 = new JTextArea(7, 25);
        jPanelBMR.add(textArea4);
        textArea4.setLineWrap(true);
        textArea4.setWrapStyleWord(true);

        jPanelRight.add(jPanelBRR);
        jPanelRight.add(Box.createVerticalStrut(10));
        jPanelRight.add(jPanelBTR);
        jPanelRight.add(Box.createVerticalStrut(10));
        jPanelRight.add(jPanelBMR);

        mainPanel.add(jPanelLeft, BorderLayout.WEST);
        mainPanel.add(jPanelC, BorderLayout.CENTER);
        mainPanel.add(jPanelRight, BorderLayout.EAST);
        this.add(mainPanel);
        this.setVisible(true);
    }

    public void Reset() {
        textArea1.setText("");
        textArea2.setText("");
        textAre3.setText("");
        textArea4.setText("");
        jTextFieldKey.setText("");
        jTextFieldIV.setText("");
    }

    private void toggleFileMode() {
        isFileMode = !isFileMode;
        jPanelBRL.removeAll();
        jPanelBRR.removeAll();
        if (isFileMode) {
            jButtonL.setEnabled(false);
            jButtonR.setEnabled(false);
            fileButtonEcr = new JButton("Chọn File mã hóa");
            fileButtonDecr = new JButton("Chọn file giải mã");

            fileButtonEcr.setPreferredSize(new Dimension(150, 110));
            fileButtonDecr.setPreferredSize(new Dimension(150, 110));
            fileButtonEcr.addActionListener(e -> chooseFileForEncryption());
            fileButtonDecr.addActionListener(e -> chooseFileForDecryption());

            jPanelBRL.add(fileButtonEcr);
            jPanelBRR.add(fileButtonDecr);
        } else {
            jButtonL.setEnabled(true);
            jButtonR.setEnabled(true);
            textArea1 = new JTextArea(7, 25);
            textArea1.setLineWrap(true);
            textArea1.setWrapStyleWord(true);
            jPanelBRL.add(textArea1);

            textAre3 = new JTextArea(7, 25);
            textAre3.setLineWrap(true);
            textAre3.setWrapStyleWord(true);
            jPanelBRR.add(textAre3);
        }

        jPanelBRL.revalidate();
        jPanelBRL.repaint();
    }

    private void chooseFileForEncryption() {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        model.setTransformation(cipher, selectedMode, paddingMode);
        if (!selectedMode.equals("ECB")) {
            String ivText = jTextFieldIV.getText();
            if (ivText.length() != 16) {
                JOptionPane.showMessageDialog(this, "IV phải có đúng 16 ký tự", "Lỗi IV", JOptionPane.ERROR_MESSAGE);
                return;
            }
            byte[] iv = ivText.getBytes(StandardCharsets.UTF_8);
            model.setIV(iv);
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileButtonEcr.setText(selectedFile.getAbsolutePath());
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setDialogTitle("Lưu Encrypt File");
            int saveResult = saveFileChooser.showSaveDialog(this);
            if (saveResult == JFileChooser.APPROVE_OPTION) {
                File destFile = saveFileChooser.getSelectedFile();
                try {
                    model.encryptFileAES(selectedFile.getAbsolutePath(), destFile.getAbsolutePath());
                    textArea2.setText(destFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Mã hóa File thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Mã hóa File thất bại " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void chooseFileForDecryption() {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        model.setTransformation(cipher, selectedMode, paddingMode);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileButtonDecr.setText(selectedFile.getAbsolutePath());
            JFileChooser saveFileChooser = new JFileChooser();
            saveFileChooser.setDialogTitle("Lưu Decrypt File");
            int saveResult = saveFileChooser.showSaveDialog(this);
            if (saveResult == JFileChooser.APPROVE_OPTION) {
                File destFile = saveFileChooser.getSelectedFile();
                try {
                    System.out.println(selectedFile.getAbsolutePath());
                    System.out.println(destFile.getAbsolutePath());
                    model.decryptFileAES(selectedFile.getAbsolutePath(), destFile.getAbsolutePath());
                    textArea4.setText(destFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "giải mã File thành công    ", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "giải mã File thất bại " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void toggleIVField() {
        String selectedMode = (String) languageComboBox.getSelectedItem();
        if ("CBC".equals(selectedMode) || "CFB".equals(selectedMode)) {
            labelIV.setVisible(true);
            jTextFieldIV.setVisible(true);
            buttonIV.setVisible(true);
        } else {
            labelIV.setVisible(false);
            jTextFieldIV.setVisible(false);
            buttonIV.setVisible(false);
        }
        revalidate();
        repaint();
    }

    public void getKey() throws NoSuchAlgorithmException {
        String cipher = this.getCipherType();
        SecretKey key;
        if (cipher != null) {
            key = this.model.genKey(cipher);
        }  else {
            JOptionPane.showMessageDialog(this, "Thuật toán không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String key1 = this.model.printKey(key);
        jTextFieldKey.setText(key1);
    }

    public void getNonce() throws NoSuchAlgorithmException{
        byte[] nonce = this.model.generateNonce();
        String textNonce = this.model.bytesToHex(nonce);
        jTextFieldIV.setText(textNonce);

    }

    public void getIV() throws NoSuchAlgorithmException{
        String cipher = this.getCipherType();
        if(cipher.equals("AES")){
            iv = this.model.generateIV(16, cipher);
        }else if(cipher.equals("RC2")) {
            iv = this.model.generateIV(8, cipher);
        }
        jTextFieldIV.setText(Base64.getEncoder().encodeToString(iv.getIV()));
    }

    public void encrypt() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String cipher = this.getCipherType();
        System.out.println(cipher);

        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();

       if(cipher.equals("ChaCha20")){
           model.setTransformation(cipher, "None", "NoPadding");
           String text = textArea1.getText();
           byte[] cipherText = this.model.encrypt(text);
           String strCipherText = this.model.bytesToHex(cipherText);
           textArea2.setText(strCipherText);
           textAre3.setText(strCipherText);


       }else {
           model.setTransformation(cipher, selectedMode, paddingMode);
//           int ivLength = 0;
//           if (cipher.equals("AES")) {
//               ivLength = 16;
//           } else if (cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish")) {
//               ivLength = 8;
//           }

//           if (!selectedMode.equals("ECB")) {
//               String ivText = jTextFieldIV.getText();
//               if (ivText.length() != ivLength) {
//                   JOptionPane.showMessageDialog(this, "IV phải có đúng " + ivLength + " ký tự " + cipher, "Lỗi IV", JOptionPane.ERROR_MESSAGE);
//                   return;
//               }
//               byte[] iv = ivText.getBytes(StandardCharsets.UTF_8);
//               System.out.println(iv.length);
//               model.setIV(iv);
//           }
       }
        String text = textArea1.getText();
        String encryptedText = model.encryptBase64(text);
        textArea2.setText(encryptedText);
        textAre3.setText(encryptedText);
    }

    public void decrypt() throws Exception {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();

        if(cipher.equals("ChaCha20")){
            model.setTransformation(cipher, "None", "NoPadding");

        }else {
            model.setTransformation(cipher, selectedMode, paddingMode);
//            int ivLength = 0;
//            if (cipher.equals("AES")) {
//                ivLength = 16;
//            } else if (cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish")) {
//                ivLength = 8;
//            }
//
//            if (!selectedMode.equals("ECB")) {
//                String ivText = jTextFieldIV.getText();
//                if (ivText.length() != ivLength) {
//                    JOptionPane.showMessageDialog(this, "IV phải có đúng " + ivLength + " ký tự " + cipher, "Lỗi IV", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//                byte[] iv = ivText.getBytes(StandardCharsets.UTF_8);
//                model.setIV(iv);
//            }
        }

        String text = textAre3.getText();
        String decryptedText = model.decryptBase64(text);
        textArea4.setText(decryptedText);

    }


    public void SaveKeyIV(){
        String key = jTextFieldKey.getText();
        String iv = jTextFieldIV.getText();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String cipher = this.getCipherType();

        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key không được trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!selectedMode.equals("ECB")) {
            if ((cipher.equals("AES") && iv.length() != 16) || (cipher.equals("DES") && iv.length() != 8) || (cipher.equals("3DES") && iv.length() != 8)) {
                JOptionPane.showMessageDialog(this, "IV phải có độ dài " + (cipher.equals("AES") ? "16" : "8") + " ký tự", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Key IV");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();
                FileWriter writer = new FileWriter(fileToSave);
                writer.write("Key: " + key + "\n");

                // Chỉ ghi IV vào file nếu chế độ yêu cầu IV
                if (!selectedMode.equals("ECB")) {
                    writer.write("IV: " + iv + "\n");
                }

                writer.close();
                JOptionPane.showMessageDialog(this, "Lưu key và IV thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi lưu Key và IV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void OpenKeyIV() {
        String selectedMode = (String) languageComboBox.getSelectedItem();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Key IV");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToOpen = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(fileToOpen));
                String line;
                String keyString = null;
                String ivString = null;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Key: ")) {
                        keyString = line.substring(5).trim();
                    } else if (line.startsWith("IV: ")) {
                        ivString = line.substring(4).trim();
                    }
                }
                reader.close();

                // Xác định thuật toán dựa trên độ dài của khóa
                String cipher = null;
                if (keyString != null) {
                    byte[] decodedKey = Base64.getDecoder().decode(keyString);
                    int keyLength = decodedKey.length;
                    if (keyLength == 16) {
                        cipher = "AES";
                    } else if (keyLength == 8) {
                        cipher = "DES";
                    } else if (keyLength == 24) {
                        cipher = "DESede"; // 3DES với 3 khóa
                    } else {
                        JOptionPane.showMessageDialog(this, "Độ dài key không hợp lệ", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tạo key dựa trên thuật toán
                    SecretKey key = new SecretKeySpec(decodedKey, cipher);
                    model.key = key;
                    jTextFieldKey.setText(keyString);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi key", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kiểm tra IV, nếu cần thiết
                if (ivString != null) {
                    byte[] iv = ivString.getBytes(StandardCharsets.UTF_8);
                    int ivLength = cipher.equals("AES") ? 16 : 8;
                    if (iv.length == ivLength) {
                        model.setIV(iv);
                        jTextFieldIV.setText(ivString);
                    } else {
                        JOptionPane.showMessageDialog(this, "IV không đúng độ dài cho " + cipher, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (!"ECB".equals(selectedMode)) {
                    JOptionPane.showMessageDialog(this, "Lỗi IV", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Không mở được file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Key không hợp lệ: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
