package emptio.common;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DasSymetricEncryptor implements SymetricEncryptor{

    private static final String ALGORITHM = "DES";

    private final SecretKey secretKey;

    public DasSymetricEncryptor() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Encryptor initialization failed.", e);
        }
    }

    @Override
    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedTextAsBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedTextAsBytes);

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new SymetricEncryptorException("Encryption process failed: " + e.getMessage());
        }
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encryptedTextAsBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedTextAsBytes = cipher.doFinal(encryptedTextAsBytes);

            return new String(decryptedTextAsBytes, StandardCharsets.UTF_8);

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new SymetricEncryptorException("Decryption process failed: " + e.getMessage());
        }
    }
}
