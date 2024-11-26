package model;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;

public class HashAlgorithmModel {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    /**
     * Hash chuỗi dữ liệu đầu vào sử dụng thuật toán băm được chỉ định.
     * @param input Dữ liệu đầu vào cần băm (dưới dạng chuỗi).
     * @param algorithm Thuật toán băm cần sử dụng ("WHIRLPOOL", "TIGER", hoặc các thuật toán băm khác).
     * @return Chuỗi băm (hex) của dữ liệu đầu vào.
     * @throws NoSuchAlgorithmException Nếu thuật toán băm không hợp lệ.
     * @throws NoSuchProviderException Nếu provider không hợp lệ.
     */
    public String hash(String input, String algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
        MessageDigest md = null;
        if (algorithm.equals("WHIRLPOOL")) {
            md = MessageDigest.getInstance("WHIRLPOOL", "BC");
        } else if (algorithm.equals("TIGER")) {
            md = MessageDigest.getInstance("TIGER", "BC"); // Thêm hỗ trợ TIGER
        }  else {
            md = MessageDigest.getInstance(algorithm);
        }
        byte[] data = input.getBytes();
        byte[] digest = md.digest(data);
        BigInteger bi = new BigInteger(1, digest);
        String hashText = bi.toString(16);

         if (algorithm.equals("WHIRLPOOL")) {
            while (hashText.length() < 128) {
                hashText = "0" + hashText;
            }
        } else if (algorithm.equals("TIGER")) {
            while (hashText.length() < 64) {
                hashText = "0" + hashText;
            }
        } else {
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
        }
        return hashText;
    }
    /**
     * Hash tệp tin đầu vào sử dụng thuật toán băm được chỉ định.
     * Kết quả băm sẽ được chuyển thành chuỗi hex (thập lục phân) và được trả về.
     * @param src Đường dẫn tới tệp tin cần băm.
     * @param algorithm Thuật toán băm cần sử dụng ("WHIRLPOOL", "TIGER", hoặc các thuật toán băm khác).
     * @return Chuỗi băm (hex) của tệp tin đầu vào, hoặc null nếu tệp không tồn tại.
     * @throws NoSuchAlgorithmException Nếu thuật toán băm không hợp lệ.
     * @throws IOException Nếu có lỗi khi đọc tệp.
     * @throws NoSuchProviderException Nếu provider không hợp lệ.
     */
    public String hashFile(String src, String algorithm) throws NoSuchAlgorithmException, IOException, NoSuchProviderException {
        MessageDigest md = null;
        if (algorithm.equals("WHIRLPOOL")) {
            md = MessageDigest.getInstance("WHIRLPOOL", "BC");
        } else if (algorithm.equals("TIGER")) {
            md = MessageDigest.getInstance("TIGER", "BC");
        } else {
            md = MessageDigest.getInstance(algorithm);
        }
        File file = new File(src);
        if (!file.exists()) {
            return null;
        }
        try (DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), md)) {
            byte[] buffer = new byte[1024];
            while (dis.read(buffer) != -1) {
            }
        }
        byte[] digest = md.digest();
        BigInteger bi = new BigInteger(1, digest);
        String hashText = bi.toString(16);

        if (algorithm.equals("WHIRLPOOL")) {
            while (hashText.length() < 128) {
                hashText = "0" + hashText;
            }
        } else if (algorithm.equals("TIGER")) {
            while (hashText.length() < 64) {
                hashText = "0" + hashText;
            }
        } else {
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
        }
        return hashText;
    }
}
