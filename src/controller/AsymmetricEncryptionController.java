package controller;
import view.AsymmetricEncryptionView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AsymmetricEncryptionController implements ActionListener {
    AsymmetricEncryptionView view;
    public AsymmetricEncryptionController(AsymmetricEncryptionView view) {
        this.view = view;
    }
    /**
     * Xử lý các sự kiện khi người dùng tương tác với giao diện.
     * @param e sự kiện  chứa thông tin về sự kiện đã xảy ra.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        Object source = e.getSource();
        switch (src) {
            case "Tạo Key": {
                if (source instanceof JButton) {
                    try {
                        this.view.genKey();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            }
            case "Bắt đầu mã hóa":
                if (source instanceof JButton) {
                    try {
                        this.view.encypt();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Reset":
                if (source instanceof JButton) {
                    try {
                        this.view.Reset();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Bắt đầu giải mã":
                if (source instanceof JButton) {
                    try {
                        this.view.decrypt();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            default:
                break;
        }
    }
}
