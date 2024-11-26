package view;
import controller.SymmetricCipherController;
import model.SymmetricCipherModel;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class SymmetricCipherView extends JFrame {
    private SymmetricCipherModel model;
    private IvParameterSpec iv;
    private String cipherType;
    private JComboBox<String> languageComboBox, paddingComboBox;
    private JTextArea textArea1, textArea2, textAre3, textArea4, jTextFieldKey;
    private JTextField jTextFieldIV;
    private Label labelIV, labelMode, paddingMode;
    private boolean isFileMode = false;
    private JPanel jPanelBRL, jPanelBRR;
    private JButton fileButtonEcr, fileButtonDecr, jButtonL, jButtonR, buttonIV;
    byte[] cipherText;
    byte[] bytes, encrypted, decrypted;
    public SymmetricCipherView(String cipherType){
        this.cipherType = cipherType;
        this.model = new SymmetricCipherModel();
        this.init();
    }
    public String getCipherType() {
        return cipherType;
    }
    /**
     * Giao diện cho tất cả thuật toán  mã hóa đối xứng
     */
    private void init() {
        setTitle("Mã Hóa " + ("DESede".equals(getCipherType()) ? "3DES" : getCipherType()));
        this.setSize(890, 500);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(25, 25, 112));

        JPanel jPanelLeft = new JPanel();
        JPanel jPanelRight = new JPanel();

        ActionListener actionListener = new SymmetricCipherController(this);

        jPanelLeft.setLayout(new BoxLayout(jPanelLeft, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng mã hóa");
        border.setTitleColor(Color.WHITE);
        jPanelLeft.setBorder(border);
        jPanelLeft.setBackground(new Color(25, 25, 112));

        jPanelBRL = new JPanel(new BorderLayout());
        TitledBorder borderBRL = BorderFactory.createTitledBorder("bản rõ");
        jPanelBRL.setBorder(new CompoundBorder(borderBRL, new EmptyBorder(10, 10, 10, 10)));
        jPanelBRL.setBackground(new Color(25, 25, 112));
        borderBRL.setTitleColor(Color.WHITE);
        textArea1 = new JTextArea(7, 25);
        textArea1.setCaretColor(Color.RED);
        jPanelBRL.add(textArea1);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        JPanel jPanelBT = new JPanel(new BorderLayout());
        jButtonL = new JButton("Bắt đầu mã hóa");
        jButtonL.setBackground(new Color(245, 245, 245));
        jButtonL.setFont(new Font("Arial", Font.PLAIN, 14));
        jPanelBT.setBackground(new Color(25, 25, 112));
        jButtonL.addActionListener(actionListener);
        jPanelBT.add(jButtonL);
        jPanelBT.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel jPanelBML = new JPanel(new BorderLayout());
        TitledBorder borderBML = BorderFactory.createTitledBorder("bản mã");
        jPanelBML.setBorder(new CompoundBorder(borderBML, new EmptyBorder(10, 10, 10, 10)));
        jPanelBML.setBackground(new Color(25, 25, 112));
        borderBML.setTitleColor(Color.WHITE);

        textArea2 = new JTextArea(7, 25);
        textArea2.setCaretColor(Color.RED);
        jPanelBML.add(textArea2);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);

        jPanelLeft.add(jPanelBRL);
        jPanelLeft.add(Box.createVerticalStrut(10));
        jPanelLeft.add(jPanelBT);
        jPanelLeft.add(Box.createVerticalStrut(10));
        jPanelLeft.add(jPanelBML);

        JPanel jPanelC = new JPanel(new GridLayout(3, 1));
        jPanelC.setBackground(new Color(25, 25, 112));

        JPanel jPanelR = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelR.setBackground(new Color(25, 25, 112));
        labelMode = new Label("Mode: ");
        labelMode.setForeground(Color.WHITE);
        paddingMode = new Label("Padding: ");
        paddingMode.setForeground(Color.WHITE);
        String[] languages = {"ECB","CBC", "CFB"};
        String[] paddings = {"PKCS5Padding", "ISO10126Padding", "NoPadding"};

        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setBackground(new Color(255, 255, 204));
        paddingComboBox = new JComboBox<>(paddings);
        paddingComboBox.setBackground(new Color(255, 255, 204));
        languageComboBox.setSelectedItem("ECB");
        paddingComboBox.setSelectedItem("PKCS5Padding");

        JButton jButtonMHF = new JButton("Mã hóa File");
        jButtonMHF.setBackground(new Color(245, 245, 245));
        jButtonMHF.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonMHF.addActionListener(e -> toggleFileMode());

        JButton jButtonReset = new JButton("Reset");
        jButtonReset.setBackground(new Color(245, 245, 245));
        jButtonReset.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonReset.addActionListener(actionListener);
        jPanelC.add(jPanelR);

        JPanel jPanelKey = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelKey.setBackground(new Color(25, 25, 112));
        Label labelKey = new Label("Key: ");
        labelKey.setForeground(Color.WHITE);
        jTextFieldKey = new JTextArea(2, 20);
        jTextFieldKey.setCaretColor(Color.RED);

        jTextFieldKey.setLineWrap(true);
        jTextFieldKey.setWrapStyleWord(true);
        JButton jButtonKey = new JButton("Tạo Key");
        jButtonKey.setBackground(new Color(245, 245, 245));
        jButtonKey.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonKey.addActionListener(actionListener);

        labelIV = new Label("IV: ");
        labelIV.setForeground(Color.WHITE);
        buttonIV = new JButton("Tạo IV");
        buttonIV.setBackground(new Color(245, 245, 245));
        buttonIV.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonIV.addActionListener(actionListener);
        jTextFieldIV = new JTextField(15);
        jTextFieldIV.setCaretColor(Color.RED);
        jPanelKey.add(labelKey);
        jPanelKey.add(jTextFieldKey);
        jPanelKey.add(jButtonKey);
        jPanelC.add(jPanelKey);

        JPanel jPanelIV = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelIV.setBackground(new Color(25, 25, 112));
        jPanelIV.add(labelIV);
        jPanelIV.add(jTextFieldIV);
        jPanelIV.add(buttonIV);
        labelIV.setVisible(false);
        jTextFieldIV.setVisible(false);
        buttonIV.setVisible(false);
        jPanelKey.add(jPanelIV);

        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton  jButton = new JButton("Tạo Nonce");
        jButton.addActionListener(actionListener);
        jPanel.add(jButton);

        if(this.getCipherType().equals("ChaCha20")){
            labelIV.setText("Nonce");
            jPanelR.add(jButtonReset);
            jPanelR.add(jButtonMHF);
            labelIV.setVisible(true);
            jTextFieldIV.setVisible(true);
            jPanelIV.add(jPanel);
        } else if (this.getCipherType().equals("RC4") || this.getCipherType().equals("Twofish")|| this.getCipherType().equals("Serpent")) {
            jPanelR.add(jButtonReset);
            jPanelR.add(jButtonMHF);
        } else {
            jPanelR.add(labelMode);
            jPanelR.add(languageComboBox);
            jPanelR.add(paddingMode);
            jPanelR.add(paddingComboBox);
            jPanelR.add(jButtonReset);
            jPanelR.add(jButtonMHF);
        }

        languageComboBox.addActionListener(e -> toggleIVField());
        jPanelRight.setLayout(new BoxLayout(jPanelRight, BoxLayout.Y_AXIS));
        TitledBorder borderGM = BorderFactory.createTitledBorder("Chức năng giải mã");
        borderGM.setTitleColor(Color.WHITE);
        jPanelRight.setBorder(borderGM);
        jPanelRight.setBackground(new Color(25, 25, 112));

        jPanelBRR = new JPanel(new BorderLayout());
        TitledBorder borderBRR = BorderFactory.createTitledBorder("bản rõ");
        jPanelBRR.setBorder(new CompoundBorder(borderBRR, new EmptyBorder(10, 10, 10, 10)));
        jPanelBRR.setBackground(new Color(25, 25, 112));
        borderBRR.setTitleColor(Color.WHITE);

        textAre3 = new JTextArea(7, 25);
        textAre3.setCaretColor(Color.RED);
        jPanelBRR.add(textAre3);
        textAre3.setLineWrap(true);
        textAre3.setWrapStyleWord(true);

        JPanel jPanelBTR = new JPanel(new BorderLayout());
        jButtonR = new JButton("Bắt đầu giải mã");
        jButtonR.setBackground(new Color(245, 245, 245));
        jButtonR.setFont(new Font("Arial", Font.PLAIN, 14));
        jButtonR.addActionListener(actionListener);
        jPanelBTR.setBorder(new CompoundBorder(borderBML, new EmptyBorder(0, 10, 0, 10)));
        jPanelBTR.add(jButtonR);
        jPanelBTR.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanelBTR.setBackground(new Color(25, 25, 112));

        JPanel jPanelBMR = new JPanel(new BorderLayout());
        TitledBorder borderBMR = BorderFactory.createTitledBorder("bản mã");
        borderBMR.setTitleColor(Color.WHITE);
        jPanelBMR.setBorder(new CompoundBorder(borderBMR, new EmptyBorder(10, 10, 10, 10)));
        jPanelBMR.setBackground(new Color(25, 25, 112));

        textArea4 = new JTextArea(7, 25);
        textArea4.setCaretColor(Color.RED);
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
    /**
     * Đặt lại nội dung của tất cả các thành phần giao diện về trạng thái ban đầu.
     * Các thành phần bao gồm:
     * - Các JTextArea: textArea1, textArea2, textAre3, textArea4.
     * - Các JTextField: jTextFieldKey, jTextFieldIV.
     * Mục đích: Xóa sạch dữ liệu nhập trước đó, chuẩn bị giao diện để nhập thông tin mới.
     */
    public void Reset() {
        textArea1.setText("");
        textArea2.setText("");
        textAre3.setText("");
        textArea4.setText("");
        jTextFieldKey.setText("");
        jTextFieldIV.setText("");
    }
    /**
     * Chuyển đổi giữa chế độ file và chế độ text.
     * Cập nhật giao diện và các thành phần trong JPanel tương ứng với chế độ.
     */
    private void toggleFileMode() {
        isFileMode = !isFileMode;
        jPanelBRL.removeAll();
        jPanelBRR.removeAll();
        String hint = isFileMode ? "tên file + đuôi file vd như text.txt" : "";
        setupTextAreaWithHint(textArea2, hint);
        setupTextAreaWithHint(textArea4, hint);
        if (isFileMode) {
            configureFileMode();
        } else {
            configureTextMode();
        }
        jPanelBRL.revalidate();
        jPanelBRL.repaint();
    }
    /**
     * Cấu hình giao diện và thành phần cho chế độ file.
     * Bao gồm hai nút "Chọn File mã hóa" và "Chọn File giải mã".
     */
    private void configureFileMode() {
        jButtonL.setEnabled(false);
        jButtonR.setEnabled(false);

        fileButtonEcr = createButton("Chọn File mã hóa", 150, 110, e -> chooseFileForEncryption());
        fileButtonDecr = createButton("Chọn file giải mã", 150, 110, e -> chooseFileForDecryption());

        jPanelBRL.add(fileButtonEcr);
        jPanelBRR.add(fileButtonDecr);
    }
    /**
     * Cấu hình giao diện và thành phần cho chế độ text.
     * Bao gồm hai JTextArea để nhập dữ liệu mã hóa và giải mã.
     */
    private void configureTextMode() {
        jButtonL.setEnabled(true);
        jButtonR.setEnabled(true);

        JTextArea textAreaLeft = createTextArea(7, 25);
        JTextArea textAreaRight = createTextArea(7, 25);

        jPanelBRL.add(textAreaLeft);
        jPanelBRR.add(textAreaRight);
    }
    /**
     * Tạo một JTextArea với số hàng và số cột được chỉ định.
     * @param rows số hàng của JTextArea.
     * @param cols số cột của JTextArea.
     * @return JTextArea đã được cấu hình.
     */
    private JTextArea createTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }
    /**
     * Tạo một JButton với kích thước và sự kiện nhấn nút được chỉ định.
     * @param text nội dung hiển thị trên nút.
     * @param width chiều rộng của nút.
     * @param height chiều cao của nút.
     * @param actionListener sự kiện khi nhấn nút.
     * @return JButton đã được cấu hình.
     */
    private JButton createButton(String text, int width, int height, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.addActionListener(actionListener);
        return button;
    }
    /**
     * Cấu hình JTextArea với gợi ý hiển thị và xử lý sự kiện focus.
     * @param textArea JTextArea cần cấu hình.
     * @param hint gợi ý hiển thị khi JTextArea trống.
     */
    private void setupTextAreaWithHint(JTextArea textArea, String hint) {
        textArea.setText(hint);
        textArea.setForeground(Color.GRAY);
        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(hint)) {
                    textArea.setText("");
                    textArea.setForeground(Color.GRAY);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(hint);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });
    }
    /**
     * Mã hóa dữ liệu từ một file đầu vào và lưu kết quả mã hóa vào một file đầu ra.
     * @throws Exception lỗi trong quá trình mã hóa.
     */
    private void chooseFileForEncryption() {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        if ("ChaCha20".equals(cipher) || "RC4".equals(cipher)) {
            model.setTransformation(cipher, "", "");
        } else if ("Serpent".equals(cipher) || "Twofish".equals(cipher)) {
            model.setTransformation(cipher, "", "");
        } else {
            model.setTransformation(cipher, selectedMode, paddingMode);
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
                    if(cipher.equals("Twofish") || cipher.equals("Serpent")) {
                        model.encryptFileTwofish(selectedFile.getAbsolutePath(), destFile.getAbsolutePath(), bytes, cipher);
                    }else {
                        model.encryptFile(selectedFile.getAbsolutePath(), destFile.getAbsolutePath());
                    }
                    textArea2.setText(destFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Mã hóa File thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Mã hóa File thất bại " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Giải mã dữ liệu từ một file đã mã hóa và lưu kết quả giải mã vào một file đầu ra.
     * @throws Exception lỗi trong quá trình giải mã.
     */
    private void chooseFileForDecryption() {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        if ("ChaCha20".equals(cipher) || "RC4".equals(cipher) || "Serpent".equals(cipher) || "Twofish".equals(cipher)) {
            model.setTransformation(cipher, "", "");
        } else {
            model.setTransformation(cipher, selectedMode, paddingMode);
        }
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
                    if(cipher.equals("Twofish") || cipher.equals("Serpent")) {
                        model.decryptFileTwofish(selectedFile.getAbsolutePath(), destFile.getAbsolutePath(), bytes, cipher);
                    }else {
                        model.decryptFile(selectedFile.getAbsolutePath(), destFile.getAbsolutePath());
                    }
                    textArea4.setText(destFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "giải mã File thành công    ", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "giải mã File thất bại " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Hiển thị hoặc ẩn trường nhập IV tùy thuộc vào chế độ mã hóa đã chọn.
     */
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
    /**
     * Tạo và hiển thị khóa mã hóa dựa trên thuật toán mã hóa đã chọn.
     * @throws NoSuchAlgorithmException nếu không tìm thấy thuật toán mã hóa phù hợp.
     */
    public void getKey() throws NoSuchAlgorithmException {
        String cipher = this.getCipherType();
        if (cipher != null) {
            SecretKey key = this.model.genKey(cipher);
            String keyStr = this.model.printKey(key);
            jTextFieldKey.setText(keyStr);
        } else {
            JOptionPane.showMessageDialog(this, "Thuật toán không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void getKeyTwofish () throws NoSuchAlgorithmException {
        bytes = this.model.genkeyTowfish(16);
        String text = this.model.encodeBase64(bytes);
        jTextFieldKey.setText(text);
    }
    /**
     * Tạo nonce (ngẫu nhiên) cho các thuật toán mã hóa yêu cầu.
     * @throws NoSuchAlgorithmException nếu không thể tạo nonce.
     */
    public void getNonce() throws NoSuchAlgorithmException {
        byte[] nonce =  this.model.generateNonce(12);
        String textNonce = this.model.bytesToHex(nonce);
        jTextFieldIV.setText(textNonce);
    }
    /**
     * Lấy IV cho thuật toán mã hóa đã chọn và hiển thị trong trường IV.
     * @throws NoSuchAlgorithmException nếu không thể tạo IV.
     */
    public void getIV() throws NoSuchAlgorithmException {
        String cipher = this.getCipherType();
        iv = getCipherIV(cipher);
        if (iv != null) {
            jTextFieldIV.setText(Base64.getEncoder().encodeToString(iv.getIV()));
        } else  {
            JOptionPane.showMessageDialog(this, "lỗi không tạo được iv", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Lấy IV cho các thuật toán mã hóa khác nhau.
     * @param cipher Loại thuật toán mã hóa.
     * @return IV được tạo cho thuật toán mã hóa tương ứng.
     * @throws NoSuchAlgorithmException nếu không thể tạo IV cho thuật toán.
     */
    private IvParameterSpec getCipherIV(String cipher) throws NoSuchAlgorithmException {
        return switch (cipher) {
            case "AES" -> this.model.generateIV(16, cipher);
            case "Blowfish", "DES", "DESede", "RC2" ->
                    this.model.generateIV(8, cipher);
            default -> null;
        };
    }
    /**
     * mã hóa dữ liệu, mode và padding đã chọn.
     * @throws Exception lỗi trong quá trình mã hóa.
     */
    public void encrypt() throws Exception {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        if (isSpecialCipher(cipher)) {
            handleSpecialCiphers(cipher);
        } else {
            model.setTransformation(cipher, selectedMode, paddingMode);
            handleIVAndEncryption(cipher, selectedMode);
        }
    }
    /**
     * Mã hóa dữ liệu bằng thuật toán Twofish.
     * @throws Exception nếu xảy ra lỗi trong quá trình mã hóa
     */
    public void encryptTwofish() throws Exception{
        String data = textArea1.getText();
        String keyText = jTextFieldKey.getText();
        if (data.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        encrypted = this.model.encryptTwofish(data.getBytes(StandardCharsets.UTF_8), bytes);
        textArea2.setText(this.model.encodeBase64(encrypted));
        textAre3.setText(this.model.encodeBase64(encrypted));
    }
    /**
     * Mã hóa dữ liệu bằng thuật toán Serpent.
     * @throws Exception nếu xảy ra lỗi trong quá trình mã hóa
     */
    public void encryptSerpent() throws Exception{
        String data = textArea1.getText();
        String keyText = jTextFieldKey.getText();
        if (data.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        encrypted = this.model.encryptSerpent(data.getBytes(StandardCharsets.UTF_8), bytes);
        textArea2.setText(this.model.encodeBase64(encrypted));
        textAre3.setText(this.model.encodeBase64(encrypted));
    }
    /**
     * Giải mã dữ liệu đã mã hóa bằng thuật toán Twofish.
     * @throws Exception nếu xảy ra lỗi trong quá trình giải mã
     */
    public void decryptTwofish() throws Exception{
        String data = textAre3.getText();
        String keyText = jTextFieldKey.getText();
        if (data.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        decrypted = this.model.decryptTwofish(encrypted, bytes);
        textArea4.setText(new String(decrypted, StandardCharsets.UTF_8));
    }
    /**
     * Giải mã dữ liệu đã mã hóa bằng thuật toán Twofish.
     * @throws Exception nếu xảy ra lỗi trong quá trình giải mã
     */
    public void decryptSerpent() throws Exception{
        String data = textAre3.getText();
        String keyText = jTextFieldKey.getText();
        if (data.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        decrypted = this.model.decryptSerpent(encrypted, bytes);
        textArea4.setText(new String(decrypted, StandardCharsets.UTF_8));
    }
    /**
     * Kiểm tra xem  mã hóa có phải là cipher (ChaCha20, RC4) hay không.
     * @param cipher Thuật toán mã hóa cần kiểm tra.
     * @return true nếu là ChaCha20 hoặc RC4, ngược lại false.
     */
    private boolean isSpecialCipher(String cipher) {
        return cipher.equals("ChaCha20") || cipher.equals("RC4");
    }
    /**
     * Xử lý mã hóa cho các cipher đặc biệt (ChaCha20, RC4).
     * @param cipher Loại cipher cần xử lý.
     * @throws Exception lỗi trong quá trình mã hóa.
     */
    private void handleSpecialCiphers(String cipher) throws Exception {
        String text = textArea1.getText();
        String encryptedText = null;
        String keyText = jTextFieldKey.getText();
        if (text.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cipher.equals("ChaCha20")) {
            model.setTransformation(cipher, "None", "NoPadding");
            cipherText = this.model.encrypt(text);
            encryptedText = this.model.bytesToHex(cipherText);
        } else if (cipher.equals("RC4")) {
            model.setTransformation(cipher, "", "");
            encryptedText = this.model.encryptBase64(text);
        }
        if (encryptedText != null) {
            textArea2.setText(encryptedText);
            textAre3.setText(encryptedText);
        }
    }
    /**
     * Xử lý mã hóa dữ liệu với các thuật toán yêu cầu IV.
     * @param cipher Loại thuật toán mã hóa (AES, DES, vv.)
     * @param selectedMode Chế độ mã hóa được chọn (CBC, ECB, vv.)
     * @throws Exception lỗi trong quá trình mã hóa.
     */
    private void handleIVAndEncryption(String cipher, String selectedMode) throws Exception {
        String plainText = textArea1.getText();
        String keyText = jTextFieldKey.getText();
        if (plainText.isEmpty() || keyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Key hoặc bản rõ không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!selectedMode.equals("ECB")) {
            byte[] ivBytes = parseIV(jTextFieldIV.getText(), cipher);

            if (ivBytes == null) {
                String requiredLength = cipher.equals("AES") ? "16" : "8";
                JOptionPane.showMessageDialog(this, "IV phải có đúng " + requiredLength + " byte cho " + cipher, "Lỗi IV", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.setIV(ivBytes);
        }
        String encryptedText = model.encryptBase64(plainText);
        textArea2.setText(encryptedText);
        textAre3.setText(encryptedText);
    }
    /**
     * kiểm tra độ dài của IV.
     * @param ivText Chuỗi IV nhập vào.
     * @param cipher Thuật toán mã hóa yêu cầu IV.
     * @return Mảng byte chứa IV hợp lệ hoặc null nếu không hợp lệ.
     */
    private byte[] parseIV(String ivText, String cipher) {
        byte[] ivBytes;
        if (ivText.contains("=")) { // Base64 format
            ivBytes = Base64.getDecoder().decode(ivText);
        } else {
            ivBytes = ivText.getBytes(StandardCharsets.UTF_8);
        }
        int expectedLength = (cipher.equals("AES")) ? 16 : 8;
        return ivBytes.length == expectedLength ? ivBytes : null;
    }
    /**
     * Thực hiện giải mã dữ liệu, mode và kiểu padding đã chọn.
     * @throws Exception lỗi trong quá trình giải mã.
     */
    public void decrypt() throws Exception {
        String cipher = this.getCipherType();
        String selectedMode = (String) languageComboBox.getSelectedItem();
        String paddingMode = (String) paddingComboBox.getSelectedItem();
        if (isSpecialCipher(cipher)) {
            handleSpecialDecryption(cipher);
        } else {
            model.setTransformation(cipher, selectedMode, paddingMode);
            handleIVAndDecryption(cipher, selectedMode);
        }
    }
    /**
     * Xử lý giải mã cho các cipher đặc biệt (ChaCha20, RC4).
     * @param cipher Loại cipher cần xử lý.
     * @throws Exception Nếu xảy ra lỗi trong quá trình giải mã.
     */
    private void handleSpecialDecryption(String cipher) throws Exception {
        if (cipher.equals("ChaCha20")) {
            model.setTransformation(cipher, "None", "NoPadding");
            String decryptedText = model.decrypt(cipherText);
            textArea4.setText(decryptedText);
        } else if (cipher.equals("RC4")) {
            model.setTransformation(cipher, "", "");
            String encryptedText = textAre3.getText();
            String decryptedText = model.decryptBase64(encryptedText);
            textArea4.setText(decryptedText);
        }
    }
    /**
     * Xử lý giải mã dữ liệu với các thuật toán yêu cầu IV.
     * @param cipher Loại thuật toán mã hóa (AES, DES, vv.)
     * @param selectedMode Chế độ mã hóa được chọn (CBC, ECB, vv.)
     * @throws Exception lỗi trong quá trình giải mã.
     */
    private void handleIVAndDecryption(String cipher, String selectedMode) throws Exception {
        String encryptedText = textAre3.getText();
        if (encryptedText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bản mã không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!selectedMode.equals("ECB")) {
            byte[] ivBytes = parseIV(jTextFieldIV.getText(), cipher);
            if (ivBytes == null) {
                String requiredLength = cipher.equals("AES") ? "16" : "8";
                JOptionPane.showMessageDialog(this, "IV phải có đúng " + requiredLength + " byte cho " + cipher, "Lỗi IV", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.setIV(ivBytes);
        }
        try {
            String decryptedText = model.decryptBase64(encryptedText);
            textArea4.setText(decryptedText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
