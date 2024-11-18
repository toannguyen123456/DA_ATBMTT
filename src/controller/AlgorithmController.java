package controller;
import view.AlgorithmView;
import view.ShiftCipherView;
import view.SymmetricCipherView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AlgorithmController implements ActionListener {
    AlgorithmView view;
    public AlgorithmController(AlgorithmView view) {
        this.view = view;
    }

    //    xử lý sự kiện khi click vào thuật toán mã hóa
    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        Object source = e.getSource();
        switch (src) {
            case "Dịch Chuyển":
                if (source instanceof JButton) {
                    try {
                        new ShiftCipherView("Dịch Chuyển");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Thay Thế":
                if (source instanceof JButton) {
                    try {
                        new ShiftCipherView("Thay Thế");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Affine":
                if (source instanceof JButton) {
                    try {
                        new ShiftCipherView("Affine");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Vigenere":
                if (source instanceof JButton) {
                    try {
                        new ShiftCipherView("Vigenere");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Hill":
                if (source instanceof JButton) {
                    try {
                        new ShiftCipherView("Hill");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;

            case "DES":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("DES");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "AES":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView( "AES");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "3DES":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("DESede");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Blowfish":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("Blowfish");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;

            case "ChaCha20":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("ChaCha20");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "RC2":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("RC2");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "RC4":
                if (source instanceof JButton) {
                    try {
                        new SymmetricCipherView("RC4");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "RC5":
                if (source instanceof JButton) {
                    try{
                        new SymmetricCipherView("RC5");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;

            default:
                break;
        }
    }
}
