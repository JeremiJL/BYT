package emptio.adapters.rest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FilePathToBytes {

    static public byte[] getBytes(String path) {
        File file = new File(path);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Page file can't be read.");
        }
    }
}
