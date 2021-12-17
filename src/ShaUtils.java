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
/*
        String algorithm = "SHA-256"; // if you perfer SHA-2
        String algorithm = "SHA3-256";
        String alg2 = "SHA3-384";

        String pText = "1";
        System.out.println(String.format(OUTPUT_FORMAT, "Input (string)", pText));
        System.out.println(String.format(OUTPUT_FORMAT, "Input (length)", pText.length()));

        byte[] shaInBytes = ShaUtils.digest(String.valueOf(0).getBytes(UTF_8), algorithm);
        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(shaInBytes)));
        byte[] shaInBytes2 = ShaUtils.digest(pText.getBytes(UTF_8), alg2);
        System.out.println(String.format(OUTPUT_FORMAT, alg2 + " (hex) ", bytesToHex(shaInBytes2)));
         fixed length, 32 bytes, 256 bits.
        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (length)", shaInBytes.length));

 */
        String fname = "lookup.txt";
        File file = new File(fname);
        if (!file.exists()) {//only create the file if it doesn't exist
            PrintWriter writer = new PrintWriter(fname);
            for (int j = 0; j <= (int) Math.ceil(Math.sqrt(Integer.MAX_VALUE)); j++) {
                //hash value of j using MD2
                byte[] md2Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "MD2");
                writer.write("md2, " + j + ", " + bytesToHex(md2Bytes) + "\n");
                //hash value of j using md5
                byte[] md5Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "MD5");
                writer.write("md5, " + j + ", " + bytesToHex(md5Bytes) + "\n");
                //hash value of j using SHA-256
                byte[] sha256Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "SHA-256");
                writer.write("sha-256, " + j + ", " + bytesToHex(sha256Bytes) + "\n");
                //hash value of j using SHA-384
                byte[] sha384Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "SHA-384");
                writer.write("sha-384, " + j + ", " + bytesToHex(sha384Bytes) + "\n");
                //hash value of j using SHA-512
                byte[] sha512Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "SHA-512");
                writer.write("sha-512, " + j + ", " + bytesToHex(sha512Bytes) + "\n");
                //hash value of j using SHA3-256
                byte[] sha3256Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "SHA3-256");
                writer.write("sha3-256, " + j + ", " + bytesToHex(sha3256Bytes) + "\n");
                //hash value of j using SHA3-384
                byte[] sha3384Bytes = ShaUtils.digest(String.valueOf(j).getBytes(UTF_8), "SHA3-384");
                writer.write("sha3-384, " + j + ", " + bytesToHex(sha3384Bytes) + "\n");
            }
            writer.close();
        }

    }
}