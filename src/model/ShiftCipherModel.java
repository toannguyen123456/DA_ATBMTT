package model;
import java.util.Random;
public class ShiftCipherModel {
    private String alphabet;
    public ShiftCipherModel(String languageCode) throws Exception {
        setAlphabet(languageCode);
    }
    public String getAlphabet() {
        return alphabet;
    }
//    tạo key dịch chuyển
    public int genKey() {
        Random rd = new Random();
        return rd.nextInt(alphabet.length()) + 1;
    }
// tạo key thay thế
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
// kiểm tra gcd
    public int gcd(int a, int n) {
        if (n == 0) {
            return a;
        }
        return gcd(n, a % n);
    }
// get key affine
    public int[] getKeyAB(int n) {
        Random random = new Random();
        int a;
        do {
            a = random.nextInt(n);
        } while (gcd(a, n) != 1);
        int b = random.nextInt(n);
        return new int[]{a, b};
    }
// tạo key Vigenere
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
    // Tạo ma trận 2x2 ngẫu nhiên
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

    // Tính định thức của ma trận 2x2
    public int determinant(int[][] key) {
        return (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % this.getAlphabet().length();
    }
// ma trận nghịch đão
    public int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
// get key
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
// chuyển chuỗi thành ma trận
    public int [][] convertArr(String keyAsString) {
        int[][] originalKeyMatrix = this.convertKeyStringToMatrix(keyAsString.toString());
        return  originalKeyMatrix;

    }
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
// chọn option mã hóa theo tiếng việt hoặc tiếng anh
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
//    mã hóa dịch chuyển
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
//    mã hóa thay thế
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
//    mã hóa affine
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
//    mã hóa vigenere
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
// mã hóa hill
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
//  giải mã dịch chuyển
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
// giải mã thay thế
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
// tìm nghịch đảo modulo
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
// giải mã affine
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
//    giả mã Vigenere
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
// giải mã hill
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
// hàm tính ma trận nghịch đảo
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
