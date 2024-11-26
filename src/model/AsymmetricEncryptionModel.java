package model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class AsymmetricEncryptionModel {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    /**
     * Tạo cặp khóa RSA (công khai và riêng) với kích thước khóa chỉ định.
     * Sử dụng thuật toán "RSA" để tạo ra khóa công khai và khóa riêng.
     * @param keySize Kích thước khóa RSA (ví dụ: 2048 hoặc 4096).
     * @throws NoSuchAlgorithmException Nếu thuật toán không hợp lệ.
     */
    public void genKey(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(keySize);
        keyPair = generator.genKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }
    /**
     * Lấy khóa công khai dưới dạng chuỗi mã hóa Base64.
     * Phương thức này sử dụng mã hóa Base64 để trả về khóa công khai dưới dạng chuỗi.
     * @return Chuỗi khóa công khai đã mã hóa Base64.
     */
    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    /**
     * Lấy khóa riêng dưới dạng chuỗi mã hóa Base64.
     * Phương thức này sử dụng mã hóa Base64 để trả về khóa riêng dưới dạng chuỗi.
     * @return Chuỗi khóa riêng đã mã hóa Base64.
     */
    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    /**
     * Mã hóa dữ liệu đầu vào (chuỗi) bằng khóa công khai RSA.
     * Dữ liệu được mã hóa bằng thuật toán mã hóa chỉ định (ví dụ: "RSA/ECB/PKCS1Padding").
     * @param data Dữ liệu đầu vào cần mã hóa.
     * @param option Thuật toán mã hóa (ví dụ: "RSA/ECB/PKCS1Padding").
     * @return Mảng byte dữ liệu đã được mã hóa.
     * @throws NoSuchPaddingException Nếu chế độ padding không hợp lệ.
     * @throws NoSuchAlgorithmException Nếu thuật toán mã hóa không hợp lệ.
     * @throws InvalidKeyException Nếu khóa không hợp lệ.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException Nếu có lỗi padding khi mã hóa.
     */
    public byte[] encrypt(String data, String option) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(option);
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(bytes);
    }
    /**
     * Giải mã dữ liệu đầu vào (mảng byte) bằng khóa riêng RSA.
     * Dữ liệu được giải mã bằng thuật toán mã hóa chỉ định (ví dụ: "RSA/ECB/PKCS1Padding").
     * @param data Dữ liệu mã hóa (mảng byte) cần giải mã.
     * @param option Thuật toán giải mã (ví dụ: "RSA/ECB/PKCS1Padding").
     * @return Dữ liệu giải mã dưới dạng chuỗi.
     * @throws NoSuchPaddingException Nếu chế độ padding không hợp lệ.
     * @throws NoSuchAlgorithmException Nếu thuật toán giải mã không hợp lệ.
     * @throws IllegalBlockSizeException Nếu kích thước khối không hợp lệ.
     * @throws BadPaddingException Nếu có lỗi padding khi giải mã.
     * @throws InvalidKeyException Nếu khóa không hợp lệ.
     */
    public String decrypt(byte[] data, String option) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(option);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }
}
