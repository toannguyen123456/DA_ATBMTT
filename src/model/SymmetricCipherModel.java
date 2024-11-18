package model;

import javax.crypto.*;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
public class SymmetricCipherModel {

    public SecretKey key;
    private String transformation;
    private IvParameterSpec ivSpec;
    private byte[] nonce;

    public SecretKey genKey(String algorithm) throws NoSuchAlgorithmException {
        int[] keySizesRC2 = {64, 128, 256};
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
        System.out.println(algorithm);
        if ("AES".equals(algorithm)) {
            generator.init(128);
            System.out.println("chiều dài key là 128");
        } else if ("DES".equals(algorithm)) {
            generator.init(56);
            System.out.println("chiều dài key là 56");
        } else if ("DESede".equals(algorithm)) {
            generator.init(168);
            System.out.println("chiều dài key là 168");
        }else if("Blowfish".equals(algorithm)){
            generator.init(128);
            System.out.println("chiều dài Blowfish là 128");
        }else if("ChaCha20".equals(algorithm)) {
            generator.init(256);
            System.out.println("chiều dài ChaCha20 là 256");
        } else if ("RC2".equals(algorithm)) {
            int randomKeySize = keySizesRC2[new SecureRandom().nextInt(keySizesRC2.length)];
            System.out.println("Random Key Size: " + randomKeySize + " bits");
            generator.init(randomKeySize);
            System.out.println("chiều dài ChaCha20 là: " + randomKeySize + " bits");
        } else {
            throw new NoSuchAlgorithmException("Thuật toán không hợp lệ");
        }
        key = generator.generateKey();
        return generator.generateKey();
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    public String printKey(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public void setIV(byte[] iv) {
            this.ivSpec = new IvParameterSpec(iv);
    }

    public byte[] generateNonce() {
        nonce = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(nonce);
        return nonce;
    }

    public IvParameterSpec generateIV(int numBytes, String cipher) {
        if (cipher.equalsIgnoreCase("RC2")) {
            if (numBytes != 8) {
                throw new IllegalArgumentException("RC2 phải là  8 bytes.");
            }
        } else if (cipher.equalsIgnoreCase("AES")) {
            if (numBytes != 16) {
                throw new IllegalArgumentException("AES phải là 16 bytes.");
            }
        } else {
            throw new IllegalArgumentException("Unsupported cipher: " + cipher);
        }
        System.out.println("Cipher : " + cipher + "numbytes : " + numBytes );
        byte[] iv = new byte[numBytes];
        new SecureRandom().nextBytes(iv);
        this.ivSpec = new IvParameterSpec(iv);
        return this.ivSpec;
    }
    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

//    private byte[] addPadding(String data, String paddingMode) {
//        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
//        int blockSize = 16;
//        int paddedLength = (dataBytes.length + blockSize - 1) / blockSize * blockSize;
//        byte[] paddedData = new byte[paddedLength];
//        System.arraycopy(dataBytes, 0, paddedData, 0, dataBytes.length);
//        if ("NoPadding".equals(paddingMode)) {
//        } else {
//            byte paddingValue = (byte) (paddedLength - dataBytes.length);
//            for (int i = dataBytes.length; i < paddedLength; i++) {
//                paddedData[i] = paddingValue;
//            }
//        }
//        return paddedData;
//    }

    public void setTransformation(String cipher,String mode, String padding) {
        this.transformation = cipher + "/" + mode + "/" + padding;
    }

    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(transformation);

        if(transformation.contains("ChaCha20")){
            if (nonce == null) {
                throw new InvalidAlgorithmParameterException("Nonce is required for ChaCha20");
            }
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            } else  if (transformation.contains("CBC") || transformation.contains("CFB")) {
                cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
          return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }


    public String encryptBase64(String data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(transformation);

        if(transformation.contains("ChaCha20")){
            if (nonce == null) {
                throw new InvalidAlgorithmParameterException("Nonce is required for ChaCha20");
            }
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
            cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } else if (transformation.contains("CBC") || transformation.contains("CFB")) {
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    public String decryptBase64(String data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] decodedData = Base64.getDecoder().decode(data);
        return decrypt(decodedData);
    }


    public boolean encryptFileAES(String srcFilePath, String destFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("CBC") || transformation.contains("CFB")) {
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        try (FileInputStream fis = new FileInputStream(srcFilePath);
             FileOutputStream fos = new FileOutputStream(destFilePath);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
        return true;
    }

    public boolean decryptFileAES(String srcFilePath, String destFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);

        if (key == null) {
            throw new InvalidKeyException("Key không được thiết lập. Vui lòng tạo hoặc mở key trước khi giải mã.");
        }

        if (ivSpec == null) {
            throw new InvalidAlgorithmParameterException("IV không được thiết lập cho chế độ CBC hoặc CFB.");
        }

        if (transformation.contains("CBC") || transformation.contains("CFB")) {
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        try (FileInputStream fis = new FileInputStream(srcFilePath);
             FileOutputStream fos = new FileOutputStream(destFilePath);
             CipherInputStream cis = new CipherInputStream(fis, cipher)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return true;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        SymmetricCipherModel model = new SymmetricCipherModel();

//        model.setNonce(key);
//        System.out.println(model.getNonceBase64());

//        System.out.println(model.printKey(model.genKeyDES()));

//        model.genKey();
//        model.setTransformation("ECB", "PKCS5Padding");
//
//        String data = "nguyễn việt toàn";
//
//        String encryptedBase64 = model.encryptBase64(data);
//        System.out.println("Encrypted (Base64): " + encryptedBase64);
//        String decryptedData = model.decryptBase64(encryptedBase64);
//        System.out.println("Decrypted: " + decryptedData);

    }

}
