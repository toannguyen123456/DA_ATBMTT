package controller;
import view.ShiftCipherView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ShiftCipherController implements ActionListener {
    ShiftCipherView view;
    public ShiftCipherController(ShiftCipherView view) {
        this.view = view;
    }
    /**
     * Xử lý các sự kiện khi người dùng tương tác với giao diện.
     * @param e sự kiện  chứa thông tin về sự kiện đã xảy ra.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Object source = e.getSource();
        String cipherType = view.getCipherType();
        switch (command) {
            case "Tạo Key":
                if (source instanceof JButton) {
                    if (cipherType.equals("Dịch Chuyển")) {
                        try {
                            this.view.setkey();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Thay Thế")) {
                        try {
                            this.view.setKeyTT();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Affine")) {
                        try {
                            this.view.setKeyAffine();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                    } else if (cipherType.equals("Vigenere")) {
                        try {
                            this.view.setkeyVigenere();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Hill")) {
                        try{
                            this.view.setKeyHill();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "lỗi mã hóa không tạo key.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Bắt đầu mã hóa":
                if (source instanceof JButton) {
                    if (cipherType.equals("Dịch Chuyển")) {
                        try {
                            this.view.encrypt();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Thay Thế")) {
                        try {
                            this.view.encryptTT();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Affine")) {
                        try {
                            this.view.encryptAffine();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Vigenere")) {
                        try {
                            this.view.encryptVigenere();
                        }catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Hill")) {
                        try{
                            this.view.encryptHill();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "lỗi mã hóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Bắt đầu giải mã":
                if (source instanceof JButton) {
                    if (cipherType.equals("Dịch Chuyển")) {
                        try {
                            this.view.decrypt();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Thay Thế")) {
                        try {
                            this.view.decryptTT();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Affine")) {
                        try {
                            this.view.decryptAffine();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Vigenere")) {
                        try{
                            this.view.decryptVigenere();

                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }

                    } else if (cipherType.equals("Hill")) {
                        try {
                            this.view.decryptHill();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "lỗi giả mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Lưu Key":
                if (source instanceof JButton) {
                    if (cipherType.equals("Dịch Chuyển")) {
                        try {
                            this.view.saveKey("Dịch Chuyển");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Thay Thế")) {
                        try {
                            this.view.saveKey("Thay Thế");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if (cipherType.equals("Affine")) {
                        try {
                            this.view.saveKey("Affine");
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Vigenere")) {
                        try{
                            this.view.saveKey("Vigenere");
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Hill")) {
                        try {
                            this.view.saveKey("Hill");
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "lỗi lưu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Open key":
                if (source instanceof JButton) {
                    if (cipherType.equals("Dịch Chuyển")) {
                        try {
                            this.view.openKey("Dịch Chuyển");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Thay Thế")) {
                        try {
                            this.view.openKey("Thay Thế");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Affine")) {
                        try {
                            this.view.openKey("Affine");
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Vigenere")) {
                        try {
                            this.view.openKey("Vigenere");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipherType.equals("Hill")) {
                        try {
                            this.view.openKey("Hill");
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "lỗi openkey", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            default:
                break;
        }
    }
}
