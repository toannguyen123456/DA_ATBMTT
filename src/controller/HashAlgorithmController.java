package controller;
import view.HashAlgorithmView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
public class HashAlgorithmController implements ActionListener {
    HashAlgorithmView view;
    public HashAlgorithmController(HashAlgorithmView view) {
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
        String cipher = this.view.getCipher();
        switch (src) {
            case "Bắt đầu Hash":
                if (source instanceof JButton) {
                    if (cipher.equals("MD5") || cipher.equals("SHA") || cipher.equals("MD2") || cipher.equals("WHIRLPOOL") || cipher.equals("TIGER") || cipher.equals("RIPEMD160")) {
                        try {
                            this.view.hash();
                        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            break;
            case "Reset":
                if (source instanceof JButton) {
                    if (cipher.equals("MD5") || cipher.equals("SHA") || cipher.equals("MD2") || cipher.equals("WHIRLPOOL") || cipher.equals("TIGER") || cipher.equals("RIPEMD160")) {
                        try {
                            this.view.reset();
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
