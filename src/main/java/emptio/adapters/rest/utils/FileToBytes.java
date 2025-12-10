package emptio.adapters.rest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileToBytes {

    static public byte[] getBytes(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Page file can't be read.");
        }
    }
}
