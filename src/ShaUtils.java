/**
 *
 *Online resource
 * source: https://mkyong.com/java/java-sha-hashing-example/
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtils {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {

        //String algorithm = "SHA-256"; // if you perfer SHA-2
        String algorithm = "SHA3-256";
        String alg2 = "SHA3-384";

//        String pText = "1";
//        System.out.println(String.format(OUTPUT_FORMAT, "Input (string)", pText));
//        System.out.println(String.format(OUTPUT_FORMAT, "Input (length)", pText.length()));

//        byte[] shaInBytes = ShaUtils.digest(pText.getBytes(UTF_8), algorithm);
//        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(shaInBytes)));
//        byte[] shaInBytes2 = ShaUtils.digest(pText.getBytes(UTF_8), alg2);
//        System.out.println(String.format(OUTPUT_FORMAT, alg2 + " (hex) ", bytesToHex(shaInBytes2)));
        // fixed length, 32 bytes, 256 bits.
        //System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (length)", shaInBytes.length));
        String fname = "lookup2.txt";
        File file = new File(fname);
        if (!file.exists()) {
            PrintWriter writer = new PrintWriter(fname);
            for (int j = 0; j <= (int) Math.ceil(Math.sqrt(Integer.MAX_VALUE)) * 1000; j++) {
                byte[] shaInBytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), algorithm);
                writer.write("sha3-256, " + j + ", " + bytesToHex(shaInBytes) + "\n");
                byte[] shaInBytes2 = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), alg2);
                writer.write("sha3-384, " + j + ", " + bytesToHex(shaInBytes2) + "\n");
            }
            writer.close();
        }

    }
}