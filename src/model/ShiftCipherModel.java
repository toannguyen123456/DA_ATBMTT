package model;
import java.util.Random;
public class ShiftCipherModel {
    private String alphabet;
    public ShiftCipherModel(String languageCode) throws Exception {
        setAlphabet(languageCode);
    }
    /**
     * Trả về bảng chữ cái.
     * @return Chuỗi chứa bảng chữ cái sử dụng trong mã hóa.
     */
    public String getAlphabet() {
        return alphabet;
    }
    /**
     * Tạo key dịch chuyển
     * @return Khóa dịch chuyển ngẫu nhiên.
     */
    public int genKey() {
        Random rd = new Random();
        return rd.nextInt(alphabet.length()) + 1;
    }
    /**
     * Tạo key thay thế
     * @return Khóa dịch chuyển ngẫu nhiên.
     */
    public String genKeyTT() {
        Random rd = new Random();
        StringBuffer sb = new StringBuffer(alphabet);
        for (int i = alphabet.length() - 1; i > 0; i--) {
            int j = rd.nextInt(i + 1);
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
        }
        return sb.toString();
    }

    /**
     * Tính toán UCLN (Greatest Common Divisor) của hai số nguyên a và n.
     * Phương thức này được sử dụng để kiểm tra nếu a và n có phải là các số nguyên tố cùng nhau hay không.
     * @param a Số nguyên thứ nhất.
     * @param n Số nguyên thứ hai.
     * @return UCLN của a và n.
     */
    public int gcd(int a, int n) {
        if (n == 0) {
            return a;
        }
        return gcd(n, a % n);
    }
    /**
     * Tạo key affine
     * @return Khóa dịch chuyển ngẫu nhiên.
     */
    public int[] getKeyAB(int n) {
        Random random = new Random();
        int a;
        do {
            a = random.nextInt(n);
        } while (gcd(a, n) != 1);
        int b = random.nextInt(n);
        return new int[]{a, b};
    }
    /**
     * Tạo key Vigenere
     * @return Khóa dịch chuyển ngẫu nhiên.
     */
    public String getKeyVigenere(String str) {
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        int index = rd.nextInt(str.length() -  3) + 4;
        for (int i = 0; i < index; i++) {
                char randomChar  = str.charAt(rd.nextInt(str.length()));
                sb.append(randomChar );
        }
        return sb.toString();
    }
    /**
     * Tạo ma trận khóa 2x2 ngẫu nhiên để sử dụng trong mã hóa Hill.
     * Ma trận này cần đảm bảo rằng định thức của nó không bằng 0 và khả nghịch.
     * @return Ma trận 2x2 ngẫu nhiên.
     */
    public int[][] generateRandomKey() {
        Random rd = new Random();
        int[][] key = new int[2][2];
        do {
            key[0][0] = rd.nextInt(this.getAlphabet().length());
            key[0][1] = rd.nextInt(this.getAlphabet().length());
            key[1][0] = rd.nextInt(this.getAlphabet().length());
            key[1][1] = rd.nextInt(this.getAlphabet().length());
        } while (determinant(key) == 0 || modInverse(determinant(key), this.getAlphabet().length()) == -1); // Đảm bảo ma trận khả nghịch
        return key;
    }
    /**
     * Tính định thức của ma trận 2x2. Định thức cần phải khác 0 để ma trận có thể nghịch đảo được.
     * @param key Ma trận cần tính định thức.
     * @return Định thức của ma trận.
     */
    public int determinant(int[][] key) {
        return (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % this.getAlphabet().length();
    }
    /**
     * Tính toán nghịch đảo của một số trong modulo m.
     * Phương thức này được sử dụng để tìm nghịch đảo của định thức ma trận trong mã hóa Hill.
     * @param a Số cần tính nghịch đảo.
     * @param m Modulo m.
     * @return Nghịch đảo của a trong modulo m.
     */
    public int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
    /**
     * Tạo key Hill
     * @return Khóa dịch chuyển ngẫu nhiên.
     */
    public String getKeyHill() {
        int[][] key = this.generateRandomKey();
        StringBuilder keyAsString = new StringBuilder();
        for (int[] row : key) {
            for (int value : row) {
                keyAsString.append(this.getAlphabet().charAt(value));
            }
        }
        return keyAsString.toString();
    }
    /**
     * Chuyển đổi chuỗi khóa thành ma trận. Đây là bước cần thiết khi sử dụng mã hóa Hill.
     * @param keyAsString Chuỗi khóa cần chuyển đổi thành ma trận.
     * @return Ma trận khóa 2x2.
     */
    public int [][] convertArr(String keyAsString) {
        int[][] originalKeyMatrix = this.convertKeyStringToMatrix(keyAsString.toString());
        return  originalKeyMatrix;

    }
    /**
     * Chuyển đổi chuỗi khóa thành ma trận 2x2. Phương thức này giúp mã hóa Hill dễ dàng sử dụng khóa.
     * @param keyAsString Chuỗi khóa cần chuyển đổi.
     * @return Ma trận khóa 2x2.
     */
    public int[][] convertKeyStringToMatrix(String keyAsString) {
        int[][] keyMatrix = new int[2][2];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                char keyChar = keyAsString.charAt(index++);
                keyMatrix[i][j] = this.getAlphabet().indexOf(keyChar);
            }
        }
        return keyMatrix;
    }
    /**
     * Chọn option tiếng việt hay tiếng anh
     * @param languageCode
     * @throws Exception
     */
    private void setAlphabet(String languageCode) throws Exception {
        switch(languageCode) {
            case "EN": {
                alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            }
            case "VI": {
                alphabet = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTÚÙỦŨỤƯỨỪỬỮỰVXY";
                break;
            }
            default: {
                throw new Exception("Unsupported language code");
            }
        }
    }
    /**
     * Mã hóa theo phương pháp Caesar Cipher (Dịch chuyển).
     * Mỗi ký tự trong chuỗi sẽ được dịch chuyển theo một khóa cho trước.
     * @param input Chuỗi văn bản cần mã hóa.
     * @param alphabet Bảng chữ cái sử dụng trong mã hóa.
     * @param key Khóa dịch chuyển (số nguyên).
     * @return Chuỗi văn bản đã được mã hóa.
     */
    public String encrypt(String input, String alphabet, int key) {
        StringBuffer sb = new StringBuffer();
        String inputUpper = input.toUpperCase();
        for (int i = 0; i < inputUpper.length(); i++) {
            char c = inputUpper.charAt(i);
            int charIndex = alphabet.indexOf(c);
            if (charIndex != -1) {
                int newIndex = (charIndex + key) % alphabet.length();
                char newChar = alphabet.charAt(newIndex);
                sb.append(newChar);
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Mã hóa theo phương pháp Thay thế (Substitution Cipher).
     * Mỗi ký tự trong văn bản sẽ được thay thế bởi ký tự tương ứng trong khóa thay thế.
     * @param input Chuỗi văn bản cần mã hóa.
     * @param alphabet Bảng chữ cái sử dụng trong mã hóa.
     * @param key Khóa thay thế là một chuỗi chứa các ký tự thay thế.
     * @return Chuỗi văn bản đã được mã hóa.
     */
    public String encryptTT(String input, String alphabet, String key) {
        StringBuffer sb = new StringBuffer();
        String inputUpper = input.toUpperCase();
        for (int i = 0; i < inputUpper.length(); i++) {
            char c = inputUpper.charAt(i);
            int charIndex = 0;
            try {
                charIndex = alphabet.indexOf(c);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (charIndex != -1) {
                char keyChar = key.charAt(charIndex % key.length());
                sb.append(keyChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Mã hóa theo phương pháp Affine Cipher.
     * Công thức mã hóa Affine là C = (a * P + b) % n, trong đó a và b là khóa, P là chỉ số của ký tự gốc, n là độ dài bảng chữ cái.
     * @param plaintext Chuỗi văn bản cần mã hóa.
     * @param a Khóa a trong công thức Affine.
     * @param b Khóa b trong công thức Affine.
     * @param alphabet Bảng chữ cái sử dụng trong mã hóa.
     * @return Chuỗi văn bản đã được mã hóa.
     */
    public  String encryptAffine(String plaintext, int a, int b, String alphabet) {
        int n = alphabet.length();
        if (gcd(a, n) != 1) {
            throw new IllegalArgumentException("a và n không phải là nguyên tố cùng nhau");
        }
        StringBuilder sb = new StringBuilder();
        String text = plaintext.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int index = alphabet.indexOf(ch);
            if (index != -1) {
                int charIndex = (a * index + b) % n;
                char cipherChar = alphabet.charAt(charIndex);
                sb.append(cipherChar);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    /**
     * Mã hóa theo phương pháp Vigenère Cipher.
     * Mỗi ký tự trong văn bản sẽ được mã hóa theo công thức Vigenère với khóa.
     * @param plaintext Chuỗi văn bản cần mã hóa.
     * @param alphabet Bảng chữ cái sử dụng trong mã hóa.
     * @param key Khóa Vigenère (chuỗi ký tự).
     * @return Chuỗi văn bản đã được mã hóa.
     */
    public String encrptVigenere(String plaintext, String alphabet, String key) {
        StringBuffer sb = new StringBuffer();
        int len = alphabet.length();
        key = key.toUpperCase();
        plaintext = plaintext.toUpperCase();
        int j = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            int charIndex = alphabet.indexOf(c);
            if (charIndex != -1) {
                char keyChar = key.charAt(j % key.length());
                int keyIndex = alphabet.indexOf(keyChar);

                int cipherIndex = (charIndex + keyIndex) % len;
                char cipherChar = alphabet.charAt(cipherIndex);
                sb.append(cipherChar);
                j++;
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Mã hóa theo phương pháp Hill Cipher.
     * Mã hóa bằng cách nhân ma trận khóa với vector văn bản và sử dụng bảng chữ cái.
     * @param plaintext Chuỗi văn bản cần mã hóa.
     * @param key Ma trận khóa 2x2.
     * @return Chuỗi văn bản đã được mã hóa.
     */
    public String encryptHill(String plaintext, int[][] key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^" + alphabet + "]", "");
        if (plaintext.length() % 2 != 0) {
            plaintext += 'X';
        }
        int[] plaintextVector = new int[2];
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            plaintextVector[0] = alphabet.indexOf(plaintext.charAt(i));
            plaintextVector[1] = alphabet.indexOf(plaintext.charAt(i + 1));
            int cipher1 = (key[0][0] * plaintextVector[0] + key[0][1] * plaintextVector[1]) % alphabet.length();
            int cipher2 = (key[1][0] * plaintextVector[0] + key[1][1] * plaintextVector[1]) % alphabet.length();
            if (cipher1 < 0) {
                cipher1 += alphabet.length();
            }
            if (cipher2 < 0) {
                cipher2 += alphabet.length();
            }
            ciphertext.append(alphabet.charAt(cipher1));
            ciphertext.append(alphabet.charAt(cipher2));
        }
        return ciphertext.toString();
    }
    /**
     * Giải mã theo phương pháp Caesar Cipher (Dịch chuyển).
     * Phương thức này giải mã chuỗi văn bản theo thuật toán Caesar bằng cách dịch chuyển ký tự ngược lại với khóa cho trước.
     * @param input Chuỗi văn bản cần giải mã.
     * @param alphabet Bảng chữ cái dùng để mã hóa.
     * @param key Khóa (số nguyên) để dịch chuyển ký tự.
     * @return Chuỗi văn bản đã giải mã.
     */
    public String decrypt(String input, String alphabet, int key) {
        StringBuffer sb = new StringBuffer();
        String inputUpper = input.toUpperCase();
        for (int i = 0; i < inputUpper.length(); i++) {
            char c = inputUpper.charAt(i);
            int charIndex = alphabet.indexOf(c);
            if (charIndex != -1) {
                int newIndex = (charIndex - key) % alphabet.length();
                if (newIndex < 0) {
                    newIndex += alphabet.length();
                }
                char newChar = alphabet.charAt(newIndex);
                sb.append(newChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Giải mã theo phương pháp Thay thế (Substitution Cipher).
     * Phương thức này giải mã chuỗi văn bản bằng cách thay thế các ký tự theo bảng khóa.
     * @param input Chuỗi văn bản cần giải mã.
     * @param alphabet Bảng chữ cái dùng để mã hóa.
     * @param key Khóa thay thế (một chuỗi chứa các ký tự thay thế).
     * @return Chuỗi văn bản đã giải mã.
     */
    public String decryptTT(String input, String alphabet, String key) {
        StringBuffer sb = new StringBuffer();
        String inputUpper = input.toUpperCase();
        for (int i = 0; i < inputUpper.length(); i++) {
            char c = inputUpper.charAt(i);
            System.out.println(c);
            int charIndex = 0;
            try {
                charIndex = key.indexOf(c);
                System.out.println(charIndex);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (charIndex != -1) {
                char alphabetChar = alphabet.charAt(charIndex);
                sb.append(alphabetChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Tìm nghịch đảo của một số theo modulo n.
     * Phương thức này sử dụng thuật toán Euclid mở rộng để tìm nghịch đảo của số a trong hệ thống modulo n.
     * @param a Số cần tìm nghịch đảo.
     * @param n Mô-đun.
     * @return Nghịch đảo của a mod n, nếu tồn tại, -1 nếu không tìm được nghịch đảo.
     */
    private int inverse(int a, int n) {
        int index1 = 0, index3 = 1;
        int index2 = n, index4 = a;

        while (index4 != 0) {
            int quotient = index2 / index4;

            int tempT = index3;
            index3 = index1 - quotient * index3;
            index1 = tempT;

            int tempR = index4;
            index4 = index2 - quotient * index4;
            index2 = tempR;
        }
        if (index2 > 1) {
            return -1;
        }
        if (index1 < 0) {
            index1 += n;
        }
        return index1;
    }
    /**
     * Giải mã theo phương pháp Affine Cipher.
     * Phương thức này giải mã chuỗi văn bản bằng công thức của Affine Cipher với khóa a và b.
     * @param str Chuỗi văn bản cần giải mã.
     * @param a Khóa a trong công thức Affine.
     * @param b Khóa b trong công thức Affine.
     * @param alphabet Bảng chữ cái dùng để mã hóa.
     * @return Chuỗi văn bản đã giải mã.
     */
    public String decryptAffine(String str, int a, int b, String alphabet) {
        int n = alphabet.length();

        int inverse = inverse(a, n);
        if (inverse == -1) {
            throw new IllegalArgumentException("Không tồn tại nghịch đảo");
        }
        StringBuilder sb = new StringBuilder();
        String text = str.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int index = alphabet.indexOf(ch);
            if (index != -1) {
                int charIndex = (inverse * (index - b + n)) % n;
                char plain = alphabet.charAt(charIndex);
                sb.append(plain);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    /**
     * Giải mã theo phương pháp Vigenère Cipher.
     * Phương thức này giải mã chuỗi văn bản bằng công thức của Vigenère Cipher với khóa cho trước.
     * @param text Chuỗi văn bản cần giải mã.
     * @param alphabet Bảng chữ cái dùng để mã hóa.
     * @param key Khóa giải mã.
     * @return Chuỗi văn bản đã giải mã.
     */
    public String decryptVigenere(String text, String alphabet, String key) {
        StringBuilder sb = new StringBuilder();
        int len = alphabet.length();
        key = key.toUpperCase();
        text = text.toUpperCase();
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int charIndex = alphabet.indexOf(c);
            if (charIndex != -1) {
                char keyChar = key.charAt(j % key.length());
                int keyIndex = alphabet.indexOf(keyChar);

                int plainIndex = (charIndex - keyIndex + len) % len;
                char plainChar = alphabet.charAt(plainIndex);
                sb.append(plainChar);
                j++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * Giải mã theo phương pháp Hill Cipher.
     * Phương thức này giải mã chuỗi văn bản đã mã hóa bằng Hill Cipher.
     * @param ciphertext Chuỗi văn bản mã hóa cần giải mã.
     * @param inverseKey Ma trận khóa nghịch đảo (được tính từ ma trận khóa ban đầu).
     * @return Chuỗi văn bản đã giải mã.
     */
    public String decryptHill(String ciphertext, int[][] inverseKey) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^" + alphabet + "]", "");
        int[] ciphertextVector = new int[2];
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            ciphertextVector[0] = alphabet.indexOf(ciphertext.charAt(i));
            ciphertextVector[1] = alphabet.indexOf(ciphertext.charAt(i + 1));
            int plain1 = (inverseKey[0][0] * ciphertextVector[0] + inverseKey[0][1] * ciphertextVector[1]) % alphabet.length();
            int plain2 = (inverseKey[1][0] * ciphertextVector[0] + inverseKey[1][1] * ciphertextVector[1]) % alphabet.length();
            if (plain1 < 0) {
                plain1 += alphabet.length();
            }
            if (plain2 < 0) {
                plain2 += alphabet.length();
            }
            plaintext.append(alphabet.charAt(plain1));
            plaintext.append(alphabet.charAt(plain2));
        }
        return plaintext.toString();
    }
    /**
     * Tính toán ma trận nghịch đảo của ma trận khóa trong Hill Cipher.
     * Phương thức này tính toán ma trận nghịch đảo của một ma trận khóa 2x2 theo modulo n.
     * @param key Ma trận khóa 2x2 cần tính nghịch đảo.
     * @return Ma trận nghịch đảo của khóa.
     */
    public int[][] inverse(int[][] key) {
        int determinant = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % alphabet.length();
        if (determinant < 0) determinant += alphabet.length();
        int inverseDeterminant = modInverse(determinant, alphabet.length());
        int[][] inverseKey = new int[2][2];
        inverseKey[0][0] = key[1][1] * inverseDeterminant % alphabet.length();
        inverseKey[0][1] = (-key[0][1]) * inverseDeterminant % alphabet.length();
        inverseKey[1][0] = (-key[1][0]) * inverseDeterminant % alphabet.length();
        inverseKey[1][1] = key[0][0] * inverseDeterminant % alphabet.length();
        // Đảm bảo không có giá trị nào âm
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (inverseKey[i][j] < 0) {
                    inverseKey[i][j] += alphabet.length();
                }
            }
        }
        return inverseKey;
    }
}
