package controller;
import view.SymmetricCipherView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
public class SymmetricCipherController implements ActionListener {
    SymmetricCipherView view;
    public SymmetricCipherController(SymmetricCipherView view) {
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
        String cipher = view.getCipherType();
        switch (command) {
            case "Tạo Key":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2") || cipher.equals("RC4")) {
                        try {
                            this.view.getKey();
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if (cipher.equals("Twofish") || cipher.equals("Serpent")) {
                        try {
                            this.view.getKeyTwofish();
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Tạo Nonce":
                if (source instanceof JButton) {
                    if (cipher.equals("ChaCha20")) {
                        try {
                            this.view.getNonce();
                        }catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Tạo IV":
                if (source instanceof JButton) {
                    if (cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2")) {
                        try {
                            this.view.getIV();
                        }catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Bắt đầu mã hóa":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2") || cipher.equals("RC4")) {
                        try {
                            this.view.encrypt();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }else if (cipher.equals("Twofish")){
                        try {
                            this.view.encryptTwofish();
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipher.equals("Serpent")) {
                        try {
                            this.view.encryptSerpent();
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
                break;
            case "Bắt đầu giải mã":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2") || cipher.equals("RC4")) {
                        try {
                            this.view.decrypt();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }else if (cipher.equals("Twofish")){
                        try {
                            this.view.decryptTwofish();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (cipher.equals("Serpent")) {
                        try {
                            this.view.decryptSerpent();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Reset":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2") || cipher.equals("RC4") || cipher.equals("Twofish") || cipher.equals("Serpent")) {
                        try {
                            this.view.Reset();
                        }catch (Exception ex){
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
