package emptio.adapters.rest.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ImageBytesExtractor {

    public static byte[] extract(HttpExchange exchange) throws IOException {
        byte[] body = exchange.getRequestBody().readAllBytes();

        String ct = firstHeader(exchange, "Content-Type");
        String boundary = extractBoundary(ct);
        if (boundary == null) {
            throw new IllegalArgumentException("Missing multipart boundary in Content-Type: " + ct);
        }

        byte[] boundaryMarker = ("--" + boundary).getBytes(StandardCharsets.US_ASCII);

        int firstBoundary = indexOf(body, boundaryMarker, 0);
        if (firstBoundary < 0) throw new IllegalArgumentException("Boundary not found in body");

        int afterBoundaryLine = indexOf(body, "\r\n".getBytes(StandardCharsets.US_ASCII), firstBoundary) + 2;
        if (afterBoundaryLine < 2) throw new IllegalArgumentException("Malformed boundary line");

        int headerEnd = indexOf(body, "\r\n\r\n".getBytes(StandardCharsets.US_ASCII), afterBoundaryLine);
        if (headerEnd < 0) throw new IllegalArgumentException("Part headers not terminated");

        int fileStart = headerEnd + 4;

        byte[] delim = ("\r\n--" + boundary).getBytes(StandardCharsets.US_ASCII);
        int next = indexOf(body, delim, fileStart);
        if (next < 0) throw new IllegalArgumentException("Closing boundary not found");

        byte[] fileBytes = slice(body, fileStart, next);

        return fileBytes;
    }

    private static String firstHeader(HttpExchange ex, String name) {
        List<String> vals = ex.getRequestHeaders().get(name);
        return (vals == null || vals.isEmpty()) ? null : vals.get(0);
    }

    private static String extractBoundary(String contentType) {
        if (contentType == null) return null;
        for (String part : contentType.split(";")) {
            part = part.trim();
            if (part.startsWith("boundary=")) {
                String b = part.substring("boundary=".length()).trim();
                if (b.startsWith("\"") && b.endsWith("\"") && b.length() >= 2) {
                    b = b.substring(1, b.length() - 1);
                }
                return b;
            }
        }
        return null;
    }

    private static int indexOf(byte[] haystack, byte[] needle, int from) {
        outer:
        for (int i = Math.max(0, from); i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) continue outer;
            }
            return i;
        }
        return -1;
    }

    private static byte[] slice(byte[] a, int start, int endExclusive) {
        if (start < 0 || endExclusive < start || endExclusive > a.length) {
            throw new IllegalArgumentException("Bad slice: " + start + ".." + endExclusive + " of " + a.length);
        }
        byte[] out = new byte[endExclusive - start];
        System.arraycopy(a, start, out, 0, out.length);
        return out;
    }
}

