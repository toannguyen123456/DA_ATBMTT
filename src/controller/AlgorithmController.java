package controller;
import view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AlgorithmController implements ActionListener {
    AlgorithmView view;
    public AlgorithmController(AlgorithmView view) {
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
            case "Twofish":
                if (source instanceof JButton) {
                    try{
                        new SymmetricCipherView("Twofish");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Serpent":
                if (source instanceof JButton) {
                    try{
                        new SymmetricCipherView("Serpent");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "RSA":
                if (source instanceof JButton) {
                    try{
                        new AsymmetricEncryptionView("RSA");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "MD2":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("MD2");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "MD5":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("MD5");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "SHA":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("SHA");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "WHIRLPOOL":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("WHIRLPOOL");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "TIGER":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("TIGER");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "RIPEMD160":
                if (source instanceof JButton) {
                    try{
                        new HashAlgorithmView("RIPEMD160");
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Chữ ký điện tử":
                if (source instanceof JButton) {
                    try{
                        new DigitalSignatureView("Chữ ký điện tử");
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
