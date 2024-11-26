package model;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SerpentEngine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class SymmetricCipherModel {
    public SecretKey key;
    private String transformation;
    private IvParameterSpec ivSpec;
    private byte[] nonce;

    public void setKey(SecretKey key) {
        this.key = key;
    }
    /**
     * Tạo một khóa bí mật với thuật toán đã chọn.
     * @param algorithm Tên thuật toán (AES, DES, ChaCha20, v.v.).
     * @return SecretKey đã tạo.
     * @throws NoSuchAlgorithmException Nếu thuật toán không được hỗ trợ.
     */
    public SecretKey genKey(String algorithm) throws NoSuchAlgorithmException {
        int[] keySizesRC = {64, 128, 256};
        int[] keySizesAES = {128, 192, 256};
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
        System.out.println(algorithm);
        if ("AES".equals(algorithm)) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, keySizesAES.length);
            generator.init(keySizesAES[randomIndex]);
        } else if ("DES".equals(algorithm)) {
            generator.init(56);
        } else if ("DESede".equals(algorithm)) {
            generator.init(168);
        } else if ("Blowfish".equals(algorithm)) {
            int[] validKeySizes = new int[(448 - 32) / 8 + 1];
            for (int i = 0, size = 32; size <= 448; size += 8, i++) {
                validKeySizes[i] = size;
            }
            int randomKeySize = validKeySizes[new Random().nextInt(validKeySizes.length)];
            generator.init(randomKeySize);
        } else if ("ChaCha20".equals(algorithm)) {
            generator.init(256);
        } else if ("RC2".equals(algorithm)) {
            int randomKeySize = keySizesRC[new SecureRandom().nextInt(keySizesRC.length)];
            generator.init(randomKeySize);
        } else if ("RC4".equals(algorithm)) {
            int randomKeySize = keySizesRC[new SecureRandom().nextInt(keySizesRC.length)];
            generator.init(randomKeySize);
        } else {
            throw new NoSuchAlgorithmException("Thuật toán không hợp lệ");
        }
        key = generator.generateKey();
        return generator.generateKey();
    }
    /**
     * Phương thức này sinh một khóa ngẫu nhiên với kích thước được chỉ định.
     * Khóa này được tạo ra bằng cách sử dụng một đối tượng `SecureRandom` để đảm bảo tính ngẫu nhiên và bảo mật.
     * @param size Kích thước của khóa cần sinh (đơn vị byte)
     * @return Một mảng byte đại diện cho khóa ngẫu nhiên
     */
    public byte[] genkeyTowfish(int size) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[size];
        random.nextBytes(key);
        return key;
    }
    /**
     * Phương thức này mã hóa một mảng byte thành chuỗi Base64.
     * @param data Mảng byte chứa dữ liệu cần mã hóa.
     * @return Một chuỗi Base64 đại diện cho dữ liệu đầu vào.
     */
    public static String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    /**
     * Trả về chuỗi biểu diễn khóa dưới dạng Base64.
     * @param key Khóa .
     * @return Chuỗi Base64 của khóa.
     */
    public String printKey(SecretKey key) {
        byte[] keyBytes = key.getEncoded();
        System.out.println("Độ dài gốc của key: " + keyBytes.length + " byte");
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    /**
     * Thiết lập giá trị IV
     * @param iv Mảng byte IV.
     */
    public void setIV(byte[] iv) {
        this.ivSpec = new IvParameterSpec(iv);
    }
    /**
     * Tạo Nonce ngẫu nhiên cho ChaCha20.
     * @param numBytes Kích thước nonce cần tạo.
     * @return Nonce được tạo.
     */
    public byte[] generateNonce(int numBytes) {
        nonce = new byte[numBytes];
        SecureRandom random = new SecureRandom();
        random.nextBytes(nonce);
        return nonce;
    }
    /**
     * Tạo giá trị IV theo thuật toán và kích thước yêu cầu.
     * @param numBytes Kích thước IV cần tạo.
     * @param cipher   Tên thuật toán yêu cầu IV.
     * @return IV đã tạo.
     */
    public IvParameterSpec generateIV(int numBytes, String cipher) {
        if (cipher.equalsIgnoreCase("RC2")) {
            if (numBytes != 8) {
                throw new IllegalArgumentException("RC2 IV phải là 8 bytes.");
            }
        } else if (cipher.equalsIgnoreCase("AES")) {
            if (numBytes != 16) {
                throw new IllegalArgumentException("AES IV phải là 16 bytes.");
            }
        } else if (cipher.equalsIgnoreCase("DES")) {
            if (numBytes != 8) {
                throw new IllegalArgumentException("DES IV phải là 8 bytes.");
            }
        } else if (cipher.equalsIgnoreCase("DESede")) {
            if (numBytes != 8) {
                throw new IllegalArgumentException("3DES IV phải là 8 bytes.");
            }
        } else if (cipher.equalsIgnoreCase("Blowfish")) {
            if (numBytes < 8 || numBytes > 16) {
                throw new IllegalArgumentException("Blowfish IV phải từ 8 đến 16 bytes.");
            }
        } else {
            throw new IllegalArgumentException("Thuật toán không hỗ trợ hoặc không cần IV: " + cipher);
        }
        byte[] iv = new byte[numBytes];
        new SecureRandom().nextBytes(iv);
        this.ivSpec = new IvParameterSpec(iv);
        return this.ivSpec;
    }
    /**
     * Chuyển mảng byte thành chuỗi hex.
     * @param bytes Mảng byte cần chuyển.
     * @return Chuỗi hex.
     */
    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    /**
     * Thêm padding cho dữ liệu đầu vào.
     * @param data  Dữ liệu đầu vào.
     * @param paddingMode Kiểu padding (NoPadding, v.v.).
     * @return Mảng byte dữ liệu đã thêm padding.
     */
    private byte[] addPadding(String data, String paddingMode) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        int blockSize = 16;
        int paddedLength = (dataBytes.length + blockSize - 1) / blockSize * blockSize;
        byte[] paddedData = new byte[paddedLength];
        System.arraycopy(dataBytes, 0, paddedData, 0, dataBytes.length);
        if ("NoPadding".equals(paddingMode)) {
        } else {
            byte paddingValue = (byte) (paddedLength - dataBytes.length);
            for (int i = dataBytes.length; i < paddedLength; i++) {
                paddedData[i] = paddingValue;
            }
        }
        return paddedData;
    }
    /**
     * Loại bỏ padding từ dữ liệu đã giải mã.
     * @param paddedData Dữ liệu đã giải mã và có padding.
     * @return Dữ liệu gốc sau khi loại bỏ padding.
     */
    private byte[] removePadding(byte[] paddedData) {
        int paddingValue = paddedData[paddedData.length - 1] & 0xFF; // Lấy giá trị padding cuối cùng
        if (paddingValue < 1 || paddingValue > paddedData.length) {
            throw new IllegalArgumentException("Padding không hợp lệ");
        }
        byte[] originalData = new byte[paddedData.length - paddingValue];
        System.arraycopy(paddedData, 0, originalData, 0, originalData.length);
        return originalData;
    }
    /**
     * Thiết lập chuỗi transformation cho thuật toán mã hóa.
     * @param cipher  Thuật toán.
     * @param mode    mode.
     * @param padding Kiểu padding.
     */
    public void setTransformation(String cipher, String mode, String padding) {
        this.transformation = cipher + "/" + mode + "/" + padding;
    }
    /**
     * Mã hóa một chuỗi văn bản bằng thuật toán đã được thiết lập.
     * @param data chuỗi văn bản cần mã hóa.
     * @return dữ liệu đã được mã hóa dưới dạng mảng byte.
     */
    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(transformation);
        if (transformation.contains("ChaCha20")) {
            if (nonce == null) {
                throw new InvalidAlgorithmParameterException("Nonce is required for ChaCha20 encryption");
            }
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        } else if (transformation.contains("CBC") || transformation.contains("CFB")) {
            if (ivSpec == null) {
                throw new InvalidAlgorithmParameterException("IV is required for " + transformation);
            }
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        } else if (transformation.contains("RC4")) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        byte[] inputData = data.getBytes(StandardCharsets.UTF_8);
        if (!transformation.contains("ChaCha20")) {
            if (transformation.contains("NoPadding")) {
                int blockSize = cipher.getBlockSize();
                inputData = addPadding(data, String.valueOf(blockSize));  // Padding
            }
        }
        return cipher.doFinal(inputData);
    }
    /**
     * Mã hóa dữ liệu sử dụng thuật toán Twofish.
     * Dữ liệu sẽ được mã hóa thành một mảng byte, và kết quả là mảng byte đã được mã hóa.
     * @param data Mảng byte chứa dữ liệu cần mã hóa.
     * @param key Mảng byte chứa khóa sử dụng để mã hóa.
     * @return Mảng byte chứa dữ liệu đã được mã hóa.
     * @throws CryptoException Nếu có lỗi trong quá trình mã hóa.
     */
    public byte[] encryptTwofish(byte[] data, byte[] key) throws CryptoException {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new TwofishEngine());
        cipher.init(true, new KeyParameter(key));
        byte[] output = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, output, 0);
        cipher.doFinal(output, len);
        return output;
    }
    /**
     * Mã hóa dữ liệu sử dụng thuật toán Serpent.
     * Dữ liệu sẽ được mã hóa thành một mảng byte, và kết quả là mảng byte đã được mã hóa.
     * @param data Mảng byte chứa dữ liệu cần mã hóa.
     * @param key Mảng byte chứa khóa sử dụng để mã hóa.
     * @return Mảng byte chứa dữ liệu đã được mã hóa.
     * @throws CryptoException Nếu có lỗi trong quá trình mã hóa.
     */
    public byte[] encryptSerpent(byte[] data, byte[] key) throws CryptoException {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new SerpentEngine());
        cipher.init(true, new KeyParameter(key));
        byte[] output = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, output, 0);
        cipher.doFinal(output, len);
        return output;
    }
    /**
     * Giải mã dữ liệu đã mã hóa sử dụng thuật toán Twofish.
     * Dữ liệu sẽ được giải mã và trả về dưới dạng một mảng byte.
     * @param data Mảng byte chứa dữ liệu đã mã hóa cần giải mã.
     * @param key Mảng byte chứa khóa sử dụng để giải mã.
     * @return Mảng byte chứa dữ liệu đã được giải mã.
     * @throws CryptoException Nếu có lỗi trong quá trình giải mã.
     */
    public byte[] decryptTwofish(byte[] data, byte[] key) throws CryptoException {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new TwofishEngine());
        cipher.init(false, new KeyParameter(key));
        byte[] output = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, output, 0);
        cipher.doFinal(output, len);
        return output;
    }
    /**
     * Giải mã dữ liệu đã mã hóa sử dụng thuật toán Serpent.
     * Dữ liệu sẽ được giải mã và trả về dưới dạng một mảng byte.
     * @param data Mảng byte chứa dữ liệu đã mã hóa cần giải mã.
     * @param key Mảng byte chứa khóa sử dụng để giải mã.
     * @return Mảng byte chứa dữ liệu đã được giải mã.
     * @throws CryptoException Nếu có lỗi trong quá trình giải mã.
     */
    public static byte[] decryptSerpent(byte[] data, byte[] key) throws CryptoException {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new SerpentEngine());
        cipher.init(false, new KeyParameter(key));
        byte[] output = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, output, 0);
        cipher.doFinal(output, len);
        return output;
    }
    /**
     * Mã hóa chuỗi văn bản và trả về kết quả dưới dạng Base64.
     * @param data chuỗi văn bản cần mã hóa.
     * @return dữ liệu đã mã hóa dưới dạng chuỗi Base64.
     */
    public String encryptBase64(String data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }
    /**
     * Giải mã dữ liệu đã mã hóa.
     * @param data mảng byte chứa dữ liệu đã mã hóa cần giải mã.
     * @return chuỗi văn bản đã được giải mã.
     */
    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(transformation);
        if (transformation.contains("ChaCha20")) {
            if (nonce == null) {
                throw new InvalidAlgorithmParameterException("Nonce is required for ChaCha20 decryption");
            }
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
            cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } else if (transformation.contains("CBC") || transformation.contains("CFB")) {
            if (ivSpec == null) {
                throw new InvalidAlgorithmParameterException("IV is required for " + transformation);
            }
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } else if (transformation.contains("RC4")) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        byte[] decryptedData = cipher.doFinal(data);

        if (!transformation.contains("ChaCha20") && transformation.contains("NoPadding")) {
            decryptedData = removePadding(decryptedData);
        }
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
    /**
     * Giải mã dữ liệu Base64.
     * @param data chuỗi Base64 chứa dữ liệu đã mã hóa.
     * @return chuỗi văn bản đã được giải mã.
     */
    public String decryptBase64(String data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] decodedData = Base64.getDecoder().decode(data);
        return decrypt(decodedData);
    }
    /**
     * Mã hóa nội dung của một file và lưu kết quả vào một file khác.
     * @param srcFilePath  đường dẫn tới file nguồn cần mã hóa.
     * @param destFilePath đường dẫn tới file đích để lưu kết quả mã hóa.
     * @return `true` nếu mã hóa thành công.
     * @throws Exception nếu có lỗi xảy ra trong quá trình mã hóa.
     */
    public boolean encryptFile(String srcFilePath, String destFilePath) throws Exception {
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
    /**
     * Giải mã nội dung của một file và lưu kết quả vào một file khác.
     * @param srcFilePath  đường dẫn tới file nguồn cần giải mã.
     * @param destFilePath đường dẫn tới file đích để lưu kết quả giải mã.
     * @return `true` nếu giải mã thành công.
     * @throws Exception nếu có lỗi xảy ra trong quá trình giải mã.
     */
    public boolean decryptFile(String srcFilePath, String destFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        if (key == null) {
            throw new InvalidKeyException("Key không được thiết lập. Vui lòng tạo hoặc mở key trước khi giải mã.");
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
    /**
     * Mã hóa một tệp sử dụng thuật toán Twofish hoặc Serpent.
     * Cả Twofish và Serpent đều là các thuật toán mã hóa khối và có thể được chọn dựa trên tham số `cipher1`.
     * @param inputFilePath Đường dẫn tới tệp đầu vào cần mã hóa.
     * @param outputFilePath Đường dẫn tới tệp đầu ra để lưu kết quả mã hóa.
     * @param key Mảng byte chứa khóa sử dụng để mã hóa.
     * @param cipher1 Tên của thuật toán mã hóa ("Twofish" hoặc "Serpent").
     * @throws Exception Nếu có lỗi trong quá trình mã hóa hoặc xử lý tệp.
     */
    public void encryptFileTwofish(String inputFilePath, String outputFilePath, byte[] key, String cipher1) throws Exception {
        PaddedBufferedBlockCipher cipher = null;
        if(cipher1.equals("Twofish")) {
            cipher = new PaddedBufferedBlockCipher(new TwofishEngine());
        }else {
            cipher = new PaddedBufferedBlockCipher(new SerpentEngine());
        }
        cipher.init(true, new KeyParameter(key));
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            processFile(cipher, fis, bos);

        }
    }
    /**
     * Giải mã một tệp sử dụng thuật toán Twofish hoặc Serpent.
     * Cả Twofish và Serpent đều là các thuật toán mã hóa khối và có thể được chọn dựa trên tham số `cipher1`.
     * @param inputFilePath Đường dẫn tới tệp đầu vào cần giải mã.
     * @param outputFilePath Đường dẫn tới tệp đầu ra để lưu kết quả giải mã.
     * @param key Mảng byte chứa khóa sử dụng để giải mã.
     * @param cipher1 Tên của thuật toán mã hóa ("Twofish" hoặc "Serpent").
     * @throws Exception Nếu có lỗi trong quá trình giải mã hoặc xử lý tệp.
     */
    public static void decryptFileTwofish(String inputFilePath, String outputFilePath, byte[] key, String cipher1) throws Exception {
        PaddedBufferedBlockCipher cipher = null;
        if(cipher1.equals("Twofish")) {
            cipher = new PaddedBufferedBlockCipher(new TwofishEngine());
        }else {
            cipher = new PaddedBufferedBlockCipher(new SerpentEngine());
        }
        cipher.init(false, new KeyParameter(key));
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            processFile(cipher, fis, bos);
        }
    }
    /**
     * Xử lý tệp (mã hóa hoặc giải mã) theo từng khối dữ liệu.
     * @param cipher Đối tượng cipher đã được khởi tạo với chế độ mã hóa hoặc giải mã.
     * @param input Đầu vào dưới dạng InputStream để đọc dữ liệu.
     * @param output Đầu ra dưới dạng OutputStream để ghi kết quả.
     * @throws CryptoException Nếu có lỗi trong quá trình mã hóa/giải mã.
     * @throws IOException Nếu có lỗi trong quá trình đọc/ghi tệp.
     */
    private static void processFile(PaddedBufferedBlockCipher cipher, InputStream input, OutputStream output) throws CryptoException, IOException {
        byte[] buffer = new byte[1024];
        byte[] outputBuffer = new byte[cipher.getOutputSize(buffer.length)];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            int outputLength = cipher.processBytes(buffer, 0, bytesRead, outputBuffer, 0);
            output.write(outputBuffer, 0, outputLength);
        }
        int outputLength = cipher.doFinal(outputBuffer, 0);
        if (outputLength > 0) {
            output.write(outputBuffer, 0, outputLength);
        }
    }
}
