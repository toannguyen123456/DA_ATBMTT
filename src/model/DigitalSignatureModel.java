package model;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;
public class DigitalSignatureModel {
    KeyPair keyPair;
    SecureRandom random;
    Signature signature;
    PublicKey publicKey;
    PrivateKey privateKey;
    /**
     * Khởi tạo đối tượng DigitalSignatureModel với thuật toán chữ ký và provider.
     * Tạo khóa công khai và khóa riêng, và khởi tạo đối tượng Signature cho việc ký và xác minh.
     * @param alg Thuật toán chữ ký sử dụng (ví dụ: "DSA").
     * @param algRandom Thuật toán ngẫu nhiên sử dụng (ví dụ: "SHA1PRNG").
     * @param prov Provider cho thuật toán và ngẫu nhiên.
     * @throws NoSuchAlgorithmException Nếu thuật toán không hợp lệ.
     * @throws NoSuchProviderException Nếu provider không hợp lệ.
     */
    public DigitalSignatureModel(String alg, String algRandom, String prov) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(alg, prov);
        random = SecureRandom.getInstance(algRandom, prov);
        generator.initialize(1024, random);
        keyPair = generator.generateKeyPair();
        signature = Signature.getInstance(alg, prov);
    }
    /**
     * Sinh ra khóa công khai và khóa riêng từ cặp khóa đã tạo.
     * @return true nếu thành công, false nếu cặp khóa không tồn tại.
     */
    public boolean genKey() {
        if(keyPair == null) return false;
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        return true;
    }
    /**
     * Ký dữ liệu đầu vào bằng khóa riêng.
     * Chuyển đổi dữ liệu thành byte và thực hiện ký.
     * @param src Dữ liệu đầu vào (chuỗi) cần ký.
     * @return Chuỗi chữ ký đã mã hóa Base64.
     * @throws InvalidKeyException Nếu khóa không hợp lệ.
     * @throws SignatureException Nếu có lỗi trong quá trình ký.
     */
    public String sign (String src) throws InvalidKeyException, SignatureException {
        byte[] data = src.getBytes();
        signature.initSign(privateKey);
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);

    }
    /**
     * Xác minh chữ ký của dữ liệu bằng khóa công khai.
     * So sánh chữ ký đã giải mã với chữ ký thực tế của dữ liệu.
     * @param src Dữ liệu đầu vào cần xác minh.
     * @param sign Chữ ký cần xác minh (dưới dạng Base64).
     * @return true nếu chữ ký hợp lệ, false nếu không hợp lệ.
     * @throws InvalidKeyException Nếu khóa công khai không hợp lệ.
     * @throws SignatureException Nếu có lỗi trong quá trình xác minh.
     */
    public boolean verify (String src, String sign) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        byte[] data = src.getBytes();
        byte[] signByte = Base64.getDecoder().decode(sign);
        signature.update(data);
        return signature.verify(signByte);
    }
    /**
     * Ký một tệp tin bằng khóa riêng và trả về chữ ký dưới dạng Base64.
     * Dữ liệu trong tệp được đọc theo khối và được ký.
     * @param filePath Đường dẫn tới tệp tin cần ký.
     * @return Chữ ký của tệp tin dưới dạng chuỗi Base64.
     * @throws InvalidKeyException Nếu khóa riêng không hợp lệ.
     * @throws SignatureException Nếu có lỗi trong quá trình ký.
     * @throws IOException Nếu có lỗi khi đọc tệp tin.
     */
    public String signFile(String filePath) throws InvalidKeyException, SignatureException, IOException {
        signature.initSign(privateKey);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }
    /**
     * Xác minh chữ ký của một tệp tin bằng khóa công khai.
     * Đọc tệp tin theo khối và xác minh chữ ký của dữ liệu.
     * @param filePath Đường dẫn tới tệp tin cần xác minh.
     * @param sign Chữ ký của tệp tin (dưới dạng Base64).
     * @return true nếu chữ ký hợp lệ, false nếu không hợp lệ.
     * @throws InvalidKeyException Nếu khóa công khai không hợp lệ.
     * @throws IOException Nếu có lỗi khi đọc tệp tin.
     * @throws SignatureException Nếu có lỗi trong quá trình xác minh.
     */
    public boolean verifyFile(String filePath, String sign) throws InvalidKeyException, IOException, SignatureException {
        signature.initVerify(publicKey);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        byte[] signByte = Base64.getDecoder().decode(sign);
        return signature.verify(signByte);
    }
}
