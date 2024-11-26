package view;
import controller.DigitalSignatureController;
import model.DigitalSignatureModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
public class DigitalSignatureView extends JFrame {
    public JComboBox<String> cbAlg, cbRandom, cbProvider;
    public JTextField txtInputSign, txtInputVerify, txtSignature;
    public JTextArea txtSignedResult;
    public JButton btnApplyConfig, btnSign, btnVerify, btnSignFile;
    public JLabel lblConfigStatus, lblResult;
    private DigitalSignatureModel model;
    private String cipher;
    private boolean isFileSigningMode = false;
    public DigitalSignatureView(String cipher) throws HeadlessException {
        this.cipher = cipher;
        this.init();
        }
    /**
     * Giao diện tổng chữ ký điện tử
     * @throws HeadlessException
     */
    private void init() throws HeadlessException {
        setTitle("Chương trình ký và xác minh chữ ký số");
        setSize(450, 250);
        setLocationRelativeTo(null);

        ActionListener actionListener = new DigitalSignatureController(this);
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel configPanel = new JPanel();
        configPanel.setBackground(new Color(25, 25, 112));
        configPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblAlgorithm = new JLabel("Thuật toán chữ ký:");
        lblAlgorithm.setForeground(Color.WHITE);
        configPanel.add(lblAlgorithm);
        cbAlg = new JComboBox<>(new String[]{"DSA"});
        cbAlg.setBackground(new Color(255, 255, 204));
        configPanel.add(cbAlg);

        JLabel lblRandomAlgorithm = new JLabel("Thuật toán sinh số ngẫu nhiên:");
        lblRandomAlgorithm.setForeground(Color.WHITE);
        configPanel.add(lblRandomAlgorithm);

        cbRandom = new JComboBox<>(new String[]{"SHA1PRNG"});
        cbRandom.setBackground(new Color(255, 255, 204));
        configPanel.add(cbRandom);

        JLabel lblProvider = new JLabel("Provider:");
        lblProvider.setForeground(Color.WHITE);
        configPanel.add(lblProvider);

        cbProvider = new JComboBox<>(new String[]{"SUN"});
        cbProvider.setBackground(new Color(255, 255, 204));
        configPanel.add(cbProvider);

        btnApplyConfig = new JButton("Áp dụng cấu hình");
        btnApplyConfig.setBackground(new Color(245, 245, 245));
        configPanel.add(btnApplyConfig);

        lblConfigStatus = new JLabel("Trạng thái:");
        lblConfigStatus.setForeground(Color.WHITE);
        configPanel.add(lblConfigStatus);

        configPanel.add(lblConfigStatus);
        btnApplyConfig.addActionListener(actionListener);

        tabbedPane.add("Cấu hình", configPanel);

        JPanel signPanel = new JPanel();
        signPanel.setBackground(new Color(25, 25, 112));
        signPanel.setLayout(null);

        JLabel lblInputSign = new JLabel("Dữ liệu cần ký:");
        lblInputSign.setForeground(Color.WHITE);
        signPanel.add(lblInputSign);

        lblInputSign.setBounds(10, 10, 100, 25);
        signPanel.add(lblInputSign);

        txtInputSign = new JTextField();
        txtInputSign.setBounds(120, 10, 300, 25);
        signPanel.add(txtInputSign);

        btnSign = new JButton("Ký số");
        btnSign.setBackground(new Color(245, 245, 245));
        btnSignFile = new JButton("Ký File");
        btnSignFile.setBackground(new Color(245, 245, 245));

        btnSignFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFileSigningMode) {
                    isFileSigningMode = false;
                    btnSign.setEnabled(true);
                    btnSignFile.setText("Ký File");
                    txtInputSign.setEnabled(true);
                } else {
                    isFileSigningMode = true;
                    btnSign.setEnabled(false);
                    btnSignFile.setText("Quay lại");
                    txtInputSign.setEnabled(false);
                    openFileChooserAndSign();
                }
            }
        });
        btnSign.setBounds(120, 50, 100, 25);
        btnSignFile.setBounds(230, 50, 100, 25);
        btnSign.addActionListener(actionListener);
        signPanel.add(btnSign);
        signPanel.add(btnSignFile);

        txtSignedResult = new JTextArea();
        txtSignedResult.setBounds(120, 90, 300, 70);
        txtSignedResult.setLineWrap(true);
        signPanel.add(txtSignedResult);

        tabbedPane.add("Ký số", signPanel);

        JPanel verifyPanel = new JPanel();
        verifyPanel.setBackground(new Color(25, 25, 112));
        verifyPanel.setLayout(null);

        JLabel lblInputVerify = new JLabel("Dữ liệu:");
        lblInputVerify.setForeground(Color.WHITE);
        lblInputVerify.setBounds(10, 10, 100, 25);
        verifyPanel.add(lblInputVerify);

        txtInputVerify = new JTextField();
        txtInputVerify.setBounds(120, 10, 300, 25);
        verifyPanel.add(txtInputVerify);

        JLabel lblSignature = new JLabel("Chữ ký:");
        lblSignature.setForeground(Color.WHITE);
        lblSignature.setBounds(10, 50, 100, 25);
        verifyPanel.add(lblSignature);

        txtSignature = new JTextField();
        txtSignature.setBounds(120, 50, 300, 25);
        verifyPanel.add(txtSignature);

        btnVerify = new JButton("Xác minh");
        btnVerify.setBackground(new Color(245, 245, 245));
        btnVerify.setBounds(120, 90, 100, 25);
        verifyPanel.add(btnVerify);
        btnVerify.addActionListener(actionListener);
        lblResult = new JLabel("Kết quả: ");
        lblResult.setBounds(120, 130, 300, 25);
        lblResult.setForeground(Color.WHITE);
        verifyPanel.add(lblResult);

        tabbedPane.add("Xác minh", verifyPanel);
        add(tabbedPane);
        this.setVisible(true);
    }
    /**
     * Phương thức này mở hộp thoại chọn tệp và thực hiện ký tệp đã chọn.
     * Sau khi tệp được chọn, nó sẽ thực hiện ký và hiển thị chữ ký trong giao diện.
     */
    private void openFileChooserAndSign() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn tệp cần ký");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                txtInputSign.setText(file.getAbsolutePath());
                txtInputVerify.setText(file.getAbsolutePath());
                String signature = model.signFile(file.getAbsolutePath());

                txtSignedResult.setText(signature);
            } catch (IOException | SignatureException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi ký tệp: " + ex.getMessage());
            } catch (InvalidKeyException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khóa: " + e.getMessage());
            }
        }
    }
    /**
     * Phương thức này cấu hình thuật toán và các thông số cần thiết cho việc ký số.
     * Sau khi cấu hình, khóa sẽ được sinh ra và trạng thái cấu hình sẽ được cập nhật.
     */
    public void config() {
        String algorithm = cbAlg.getSelectedItem().toString();
        String randomAlg = cbRandom.getSelectedItem().toString();
        String provider = cbProvider.getSelectedItem().toString();
        try {
            model = new DigitalSignatureModel(algorithm, randomAlg, provider);
            model.genKey();
            lblConfigStatus.setText("Trạng thái: " + algorithm + " đã được cấu hình.");
        } catch (Exception ex) {
            lblConfigStatus.setText("Trạng thái: Lỗi cấu hình! " + ex.getMessage());
        }
    }
    /**
     * Phương thức này thực hiện ký dữ liệu đã nhập trong trường txtInputSign.
     * Sau khi ký xong, chữ ký sẽ được hiển thị trong trường txtSignedResult.
     */
    public void kySo() {
        if (model == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng cấu hình trước!");
            return;
        }
        try {
            String data = txtInputSign.getText();
            String signature = model.sign(data);
            txtSignedResult.setText(signature);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ký: " + ex.getMessage());
        }
    }
    /**
     * Phương thức này kiểm tra tính hợp lệ của chữ ký.
     * Nếu đang ở chế độ ký tệp, nó sẽ xác minh tệp và chữ ký, nếu không sẽ kiểm tra văn bản và chữ ký.
     */
    public void CheckResult() {
        if (model == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng cấu hình trước!");
            return;
        }
        try {
            if (isFileSigningMode) {
                String filePath = txtInputVerify.getText();
                String signature = txtSignature.getText();
                boolean isValid = model.verifyFile(filePath, signature);
                lblResult.setText("Kết quả: " + (isValid ? "Hợp lệ" : "Không hợp lệ"));
            } else {
                String data = txtInputVerify.getText();
                String signature = txtSignature.getText();
                boolean isValid = model.verify(data, signature);
                lblResult.setText("Kết quả: " + (isValid ? "Hợp lệ" : "Không hợp lệ"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xác minh: " + ex.getMessage());
        }
    }
}
