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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Object source = e.getSource();
        String cipher = view.getCipherType();
        switch (command) {

            case "Tạo Key":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20") || cipher.equals("RC2")) {
                        try {
                            this.view.getKey();
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
                    if (cipher.equals("RC2") || cipher.equals("AES")) {
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
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20")) {
                        try {
                            this.view.encrypt();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }

                }
                break;
            case "Bắt đầu giải mã":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20")) {
                        try {
                            this.view.decrypt();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Reset":
                if (source instanceof JButton) {
                    if(cipher.equals("AES") || cipher.equals("DES") || cipher.equals("DESede") || cipher.equals("Blowfish") || cipher.equals("ChaCha20")) {
                        try {
                            this.view.Reset();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }

                }
                break;

            case "Save Key IV":
                if (source instanceof JButton) {
                    if(cipher.equals("AES")) {
                        try {
                            this.view.SaveKeyIV();
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }
                }
                break;

            case "Open Key IV":
                if (source instanceof JButton) {
                    if(cipher.equals("AES")) {
                        try {
                            this.view.OpenKeyIV();
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
