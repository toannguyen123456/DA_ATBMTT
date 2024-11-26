package view;
import controller.AsymmetricEncryptionController;
import model.AsymmetricEncryptionModel;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class AsymmetricEncryptionView extends JFrame {
    private AsymmetricEncryptionModel model;
    private String cipher;
    private JComboBox<String> languageComboBox, OptionPaddingComboBox;
    private JButton jButton_1, jButton_3, jButton_4, jButton_5;
    private JTextArea TextArea_1, TextArea_2, TextArea_3, TextArea_4, jTextPublicKey, JTextAreaPrivateKey;
    private byte[] bytesEncypted;
    public AsymmetricEncryptionView(String cipher) {
        this.cipher = cipher;
        model = new AsymmetricEncryptionModel();
        this.init();
    }
    public String getCipher() {
        return cipher;
    }
    /**
     * Giao diện tổng thuật toán mã hóa RSA
     */
    private void init() {
        this.setTitle("Thuật Toán: " + this.getCipher());
        this.setSize(850, 510);
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
     * Giao diện bên trái
     * @return
     */
    private JPanel createEncryptPanel() {
        ActionListener actionListener = new AsymmetricEncryptionController(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng mã hóa");
        border.setTitleColor(Color.WHITE);
        panel.setBorder(border);
        panel.setBackground(new Color(25, 25, 112));

        JPanel BRPanel = new JPanel();
        TitledBorder BRborder = BorderFactory.createTitledBorder("Bản rõ");
        BRborder.setTitleColor(Color.WHITE);
        BRPanel.setBorder(BRborder);
        BRPanel.setBackground(new Color(25, 25, 112));

        TextArea_1 = new JTextArea(4, 38);
        TextArea_1.setLineWrap(true);
        TextArea_1.setWrapStyleWord(true);
        TextArea_1.setEnabled(true);
        TextArea_1.setEditable(true);

        JScrollPane scrollPane_3 = new JScrollPane(TextArea_1);
        scrollPane_3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        BRPanel.add(scrollPane_3);
        panel.add(BRPanel);

        JPanel keypanel = new JPanel();

        TitledBorder keyLabel = BorderFactory.createTitledBorder("Public Key");
        keyLabel.setTitleColor(Color.WHITE);
        keypanel.setBorder(keyLabel);
        keypanel.setBackground(new Color(25, 25, 112));

        jTextPublicKey = new JTextArea(4, 40);
        jTextPublicKey.setLineWrap(true);
        jTextPublicKey.setWrapStyleWord(true);

        JScrollPane scrollPane_2 = new JScrollPane(jTextPublicKey);
        scrollPane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        keypanel.add(scrollPane_2);
        panel.add(keypanel);
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(25, 25, 112));

        jButton_1 = new JButton("Bắt đầu mã hóa");
        jButton_1.addActionListener(actionListener);
        jButton_1.setBackground(new Color(245, 245, 245));

        jButton_3 = new JButton("Tạo Key");
        jButton_3.addActionListener(actionListener);
        jButton_3.setBackground(new Color(245, 245, 245));


        JLabel labelTileKey = new JLabel("Key Size: ");
        labelTileKey.setForeground(Color.WHITE);
        String[] languages = {"512", "1024", "2048", "3072", "4096"};
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setSelectedItem("512");
        languageComboBox.setBackground(new Color(255, 255, 204));

        JLabel labelOptionPadding = new JLabel("Chọn Loại mật mã: ");
        labelOptionPadding.setForeground(Color.WHITE);
        String[] optionPadding  = {"RSA", "RSA/ECB/PKCS1Padding", "RSA/ECB/NoPadding"};

        OptionPaddingComboBox = new JComboBox<>(optionPadding);
        OptionPaddingComboBox.setSelectedItem("512");
        OptionPaddingComboBox.setBackground(new Color(255, 255, 204));

        JButton jButtonReset = new JButton("Reset");
        jButtonReset.addActionListener(actionListener);
        jButtonReset.setBackground(new Color(245, 245, 245));

        panel1.add(labelTileKey);
        panel1.add(languageComboBox);
        panel1.add(jButton_1);
        panel1.add(jButton_3);
        panel1.add(labelOptionPadding);
        panel1.add(OptionPaddingComboBox);
        panel1.add(jButtonReset);

        panel.add(panel1);

        JPanel rightPanel = new JPanel();
        TitledBorder rightborder = BorderFactory.createTitledBorder("Bản Mã");
        rightborder.setTitleColor(Color.WHITE);
        rightPanel.setBorder(rightborder);
        rightPanel.setBackground(new Color(25, 25, 112));

        TextArea_2 = new JTextArea(4, 38);
        TextArea_2.setLineWrap(true);
        TextArea_2.setWrapStyleWord(true);
        TextArea_2.setEnabled(true);
        TextArea_2.setEditable(true);

        JScrollPane scrollPane_1 = new JScrollPane(TextArea_2);
        scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(scrollPane_1);

        panel.add(rightPanel);
        return panel;

    }
    /**
     * Giao diện bên phải
     * @return
     */
    private JPanel createDecryptPanel() {
        ActionListener actionListener = new AsymmetricEncryptionController(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        TitledBorder border = BorderFactory.createTitledBorder("Chức năng giải mã");
        border.setTitleColor(Color.WHITE);
        panel.setBorder(border);

        panel.setBackground(new Color(25, 25, 112));
        JPanel leftPanel = new JPanel();
        TitledBorder BMborder = BorderFactory.createTitledBorder("Bản Mã");
        BMborder.setTitleColor(Color.WHITE);
        leftPanel.setBorder(BMborder);
        leftPanel.setBackground(new Color(25, 25, 112));

        TextArea_3 = new JTextArea(4, 38);
        TextArea_3.setLineWrap(true);
        TextArea_3.setWrapStyleWord(true);
        TextArea_3.setEnabled(true);
        TextArea_3.setEditable(true);

        JScrollPane scrollPane1 = new JScrollPane(TextArea_3);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        leftPanel.add(scrollPane1);
        panel.add(leftPanel);
        JPanel keypanel = new JPanel();
        TitledBorder BMborderPrivateKey = BorderFactory.createTitledBorder("Private Key: ");
        BMborderPrivateKey.setTitleColor(Color.WHITE);
        keypanel.setBorder(BMborderPrivateKey);
        keypanel.setBackground(new Color(25, 25, 112));

        JTextAreaPrivateKey = new JTextArea(5, 38);
        JTextAreaPrivateKey.setLineWrap(true); // Tự động xuống dòng nếu nội dung quá dài
        JTextAreaPrivateKey.setWrapStyleWord(true); // Ngắt dòng theo từ, không cắt ngang từ

        JScrollPane scrollPane = new JScrollPane(JTextAreaPrivateKey);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        keypanel.add(scrollPane);
        panel.add(keypanel);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(25, 25, 112));
        jButton_4 = new JButton("Bắt đầu giải mã");
        jButton_4.addActionListener(actionListener);
        jButton_4.setBackground(new Color(245, 245, 245));

        panel1.add(jButton_4);
        panel.add(panel1);

        JPanel BRPanel = new JPanel();
        TitledBorder BRborder = BorderFactory.createTitledBorder("Bản rõ");
        BRborder.setTitleColor(Color.WHITE);
        BRPanel.setBorder(BRborder);
        BRPanel.setBackground(new Color(25, 25, 112));

        TextArea_4 = new JTextArea(4, 38);
        TextArea_4.setLineWrap(true);
        TextArea_4.setWrapStyleWord(true);
        TextArea_4.setEnabled(true);
        TextArea_4.setEditable(true);

        JScrollPane scrollPane_4 = new JScrollPane(TextArea_4);
        scrollPane_4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        BRPanel.add(scrollPane_4);
        panel.add(BRPanel);
        panel.add(BRPanel);
        return panel;
    }
    /**
     * Phương thức này sinh khóa công khai và riêng tư dựa trên kích thước khóa được chọn từ combobox.
     * Sau khi sinh khóa, nó hiển thị khóa công khai và khóa riêng vào giao diện người dùng.
     */
    public void genKey() throws NoSuchAlgorithmException {
        String selectedMode = (String) languageComboBox.getSelectedItem();
        int keySize = Integer.parseInt(selectedMode);
        this.model.genKey(keySize);
        jTextPublicKey.setText(this.model.getPublicKey());
        JTextAreaPrivateKey.setText(this.model.getPrivateKey());
    }
    /**
     * Phương thức này làm sạch các trường văn bản (TextArea và TextField) trên giao diện người dùng.
     * Sau khi gọi phương thức này, tất cả các trường sẽ được reset về trạng thái ban đầu.
     */
    public void Reset() {
        TextArea_1.setText("");
        TextArea_2.setText("");
        TextArea_3.setText("");
        TextArea_4.setText("");
        JTextAreaPrivateKey.setText("");
        jTextPublicKey.setText("");

    }
    /**
     * Phương thức này mã hóa dữ liệu văn bản bằng khóa công khai và thuật toán chọn từ combobox.
     * Dữ liệu sau khi mã hóa sẽ được hiển thị dưới dạng mã hóa Base64 trong các trường TextArea.
     */
    public void encypt() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String selectedMode = (String) OptionPaddingComboBox.getSelectedItem();
        String data = TextArea_1.getText();
        String publickey = jTextPublicKey.getText();
        if(data.isEmpty() || publickey.isEmpty()) {
            JOptionPane.showMessageDialog(this, "bảng mã hoặc Public Key không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        bytesEncypted = this.model.encrypt(data, selectedMode);
        TextArea_2.setText(Base64.getEncoder().encodeToString(bytesEncypted));
        TextArea_3.setText(Base64.getEncoder().encodeToString(bytesEncypted));
    }
    /**
     * Phương thức này giải mã dữ liệu đã mã hóa bằng khóa riêng và thuật toán padding chọn từ combobox.
     * Sau khi giải mã, dữ liệu sẽ được hiển thị lại trong TextArea_4.
     */
    public void decrypt () throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String selectedMode = (String) OptionPaddingComboBox.getSelectedItem();
        String text = TextArea_3.getText();
        String privateKey = jTextPublicKey.getText();
        if(text.isEmpty() || privateKey.isEmpty()) {
            JOptionPane.showMessageDialog(this, "bảng mã hoặc Private Key không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String data = this.model.decrypt(bytesEncypted, selectedMode);
        TextArea_4.setText(data);
    }
}
