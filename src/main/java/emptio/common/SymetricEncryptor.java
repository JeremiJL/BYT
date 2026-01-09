package emptio.common;

public interface SymetricEncryptor {

    String encrypt(String text) throws SymetricEncryptorException;
    String decrypt(String encryptedText) throws SymetricEncryptorException;

}
