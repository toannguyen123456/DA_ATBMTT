package view;
import controller.HashAlgorithmController;
import model.HashAlgorithmModel;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
public class HashAlgorithmView extends JFrame {
    private HashAlgorithmModel model;
    private String cipher, hashText, fileHash;
    private JTextArea textArea_1, textArea_2;
    private boolean isFileMode = false;
    private JPanel jPanelInput;
    private String selectedFilePath;
    private JButton fileChooserButton;
    private JComboBox optionSHAComboBox, optionBLAKEComboBox;

    public HashAlgorithmView(String cipher) {
        this.model = new HashAlgorithmModel();
        this.cipher = cipher;
        this.init();
    }
    public String getCipher() {
        return cipher;
    }

    /**
     * Giao diện tổng cho thuật toán mã hóa Hash
     */
    public void init(){

        setTitle("Mã hóa: " + this.getCipher());
        this.setSize(500, 410);
        this.setLocationRelativeTo(null);

        ActionListener actionListener = new HashAlgorithmController(this);
        JPanel jPanel = new JPanel(new BorderLayout());

        jPanelInput = new JPanel(new BorderLayout()); // Sử dụng BorderLayout cho JScrollPane
        TitledBorder borderInput = BorderFactory.createTitledBorder("Input");
        jPanelInput.setBorder(new CompoundBorder(borderInput, new EmptyBorder(10, 10, 10, 10)));
        jPanelInput.setBackground(new Color(25, 25, 112));
        borderInput.setTitleColor(Color.WHITE);

        textArea_1 = new JTextArea(7, 50);
        textArea_1.setCaretColor(Color.RED);
        textArea_1.setLineWrap(true);
        textArea_1.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea_1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelInput.add(scrollPane, BorderLayout.CENTER);

        JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanelButton.setBackground(new Color(25, 25, 112));
        jPanelButton.setBorder(new EmptyBorder(10, 0, 10, 0));

        if(this.getCipher().equals("SHA")) {
            String[] optionSHA = {"SHA-1", "SHA-224", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
            JLabel labelStype = new JLabel("Type: ");
            labelStype.setForeground(Color.WHITE);
            optionSHAComboBox = new JComboBox<>(optionSHA);
            optionSHAComboBox.setBackground(new Color(255, 255, 204));
            jPanelButton.add(labelStype);
            jPanelButton.add(optionSHAComboBox);
        }

        JButton JbuttonHash = new JButton("Bắt đầu Hash");
        JbuttonHash.setBackground(new Color(245, 245, 245));
        JbuttonHash.addActionListener(actionListener);

        JButton JbuttonFile = new JButton(this.getCipher() + " File");
        JbuttonFile.addActionListener(e -> toggleFileMode());
        JbuttonFile.setBackground(new Color(245, 245, 245));

        JButton JbuttonReset = new JButton("Reset");
        JbuttonReset.addActionListener(actionListener);
        JbuttonReset.setBackground(new Color(245, 245, 245));

        jPanelButton.add(JbuttonHash);
        jPanelButton.add(JbuttonFile);
        jPanelButton.add(JbuttonReset);

        JPanel jPaneOutput = new JPanel(new BorderLayout());
        TitledBorder borderOutput = BorderFactory.createTitledBorder("Output");
        jPaneOutput.setBorder(new CompoundBorder(borderOutput, new EmptyBorder(10, 10, 10, 10)));
        jPaneOutput.setBackground(new Color(25, 25, 112));
        borderOutput.setTitleColor(Color.WHITE);

        textArea_2 = new JTextArea(7, 50);
        textArea_2.setCaretColor(Color.RED);
        textArea_2.setLineWrap(true);
        textArea_2.setWrapStyleWord(true);

        JScrollPane scrollPane_1 = new JScrollPane(textArea_2);
        scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        jPaneOutput.add(scrollPane_1, BorderLayout.CENTER);

        jPanel.add(jPanelInput, BorderLayout.NORTH);
        jPanel.add(jPanelButton, BorderLayout.CENTER);
        jPanel.add(jPaneOutput, BorderLayout.SOUTH);

        this.add(jPanel);
        this.setVisible(true);
    }
    /**
     * Phương thức này dùng để reset lại các thành phần trong giao diện người dùng.
     * Xóa các nội dung trong các TextArea và chuyển sang chế độ văn bản nếu đang ở chế độ file.
     * @throws NoSuchAlgorithmException nếu gặp lỗi khi thao tác với thuật toán.
     */
    public void reset () throws NoSuchAlgorithmException {
        textArea_2.setText("");
        textArea_1.setText("");
        if (isFileMode) {
            isFileMode = false;
            jPanelInput.removeAll();
            configureTextMode();
            jPanelInput.revalidate();
            jPanelInput.repaint();
        }
        selectedFilePath = null;
    }
    /**
     * Phương thức này dùng để hash dữ liệu hoặc file tùy thuộc vào chế độ hiện tại.
     * @throws NoSuchAlgorithmException nếu thuật toán hash không hợp lệ.
     * @throws NoSuchProviderException nếu nhà cung cấp không hỗ trợ thuật toán.
     */
    public void hash() throws NoSuchAlgorithmException, NoSuchProviderException {
        String cipher = this.getCipher();
        System.out.println(cipher);
        if (isFileMode) {
            if (selectedFilePath == null || selectedFilePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file để hash", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (cipher.equals("MD5") || cipher.equals("MD2")) {
                    fileHash = model.hashFile(selectedFilePath, cipher);
                } else if (cipher.equals("SHA")) {
                    String selectedMode = (String) optionSHAComboBox.getSelectedItem();
                    fileHash = model.hashFile(selectedFilePath, selectedMode);
                } else if (cipher.equals("WHIRLPOOL")) {  // Thêm hỗ trợ WHIRLPOOL
                    fileHash = model.hashFile(selectedFilePath, "WHIRLPOOL");
                } else if (cipher.equals("TIGER")) {  // Thêm hỗ trợ TIGER
                    fileHash = model.hashFile(selectedFilePath, "TIGER");
                }else if (cipher.equals("RIPEMD160") ) {
                    fileHash = model.hashFile(selectedFilePath, cipher);
                }
                textArea_2.setText(fileHash);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi hash file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String data = textArea_1.getText();
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cipher.equals("MD5") || cipher.equals("MD2")) {
                hashText = this.model.hash(data, cipher);
            } else if (cipher.equals("SHA")) {
                String selectedMode = (String) optionSHAComboBox.getSelectedItem();
                hashText = this.model.hash(data, selectedMode);
            } else if (cipher.equals("WHIRLPOOL")) {
                hashText = this.model.hash(data, cipher);
            } else if (cipher.equals("TIGER")) {
                hashText = this.model.hash(data, cipher);
            }else if (cipher.equals("RIPEMD160") ) {

                hashText = this.model.hash(data, cipher);
            }
            textArea_2.setText(hashText);
        }
    }
    /**
     * Phương thức này chuyển đổi giữa chế độ file và chế độ văn bản.
     * Khi chế độ file được bật, người dùng có thể chọn file để hash.
     * Khi chế độ văn bản được bật, người dùng có thể nhập dữ liệu văn bản để hash.
     */

    private void toggleFileMode() {
        isFileMode = !isFileMode;
        jPanelInput.removeAll();
        if (isFileMode) {
            configureFileMode();
        } else {
            configureTextMode();
        }
        jPanelInput.revalidate();
        jPanelInput.repaint();
    }
    /**
     * Phương thức này cấu hình giao diện khi chế độ file được chọn.
     * Hiển thị nút chọn file để hash.
     */
    private void configureFileMode() {
        fileChooserButton = new JButton("Chọn file để hash");
        fileChooserButton.addActionListener(e -> selectFile());
        fileChooserButton.setBackground(new Color(255, 255, 204));
        Dimension textAreaSize = textArea_1.getPreferredSize();
        fileChooserButton.setPreferredSize(new Dimension((int) textAreaSize.getWidth(), (int) textAreaSize.getHeight()));
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(new Color(25, 25, 112));
        buttonWrapper.add(fileChooserButton);
        jPanelInput.add(buttonWrapper, BorderLayout.CENTER);
    }
    /**
     * Phương thức này cấu hình giao diện khi chế độ văn bản được chọn.
     * Hiển thị TextArea để người dùng nhập dữ liệu văn bản.
     */
    private void configureTextMode() {
        JScrollPane scrollPane = new JScrollPane(textArea_1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jPanelInput.add(scrollPane, BorderLayout.CENTER);
    }
    /**
     * Phương thức này cho phép người dùng chọn file từ hệ thống để hash.
     */
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file để Hash");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedFilePath  = selectedFile.getAbsolutePath();
            fileChooserButton.setText(selectedFilePath);
        }
    }
}
