/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Hashes {

    public static void main(String[] args) throws FileNotFoundException {
        //String[] algos = {"MD2", "MD5", "SHA-256", "SHA-384", "SHA-512"};

        //generate hashed values of between an interval and save to a file
        //hash values generated through: md2, md5, sha256, sha384, sha512
        String fname = "lookup.txt";
        File file = new File(fname);
        if (!file.exists()) {
            PrintWriter writer = new PrintWriter(fname);
            for (int j = 0; j <= (int) Math.ceil(Math.sqrt(Integer.MAX_VALUE)); j++) {
                String md2Hex = org.apache.commons.codec.digest.DigestUtils.md2Hex(String.valueOf(j));
                writer.write("md2, " + j + ", " + md2Hex + "\n");
                String md5Hex = org.apache.commons.codec.digest.DigestUtils.md5Hex(String.valueOf(j));
                writer.write("md5," + j + ", " + md5Hex + "\n");
                String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(String.valueOf(j));
                writer.write("sha256, " + j + ", " + sha256hex + "\n");
                String sha384Hex = org.apache.commons.codec.digest.DigestUtils.sha384Hex(String.valueOf(j));
                writer.write("sha384, " + j + ", " + sha384Hex + "\n");
                String sha512Hex = org.apache.commons.codec.digest.DigestUtils.sha512Hex(String.valueOf(j));
                writer.write("sha512, " + j + ", " + sha512Hex + "\n");
                //   System.out.println(sha512Hex);
            }
            writer.close();
        }
    }
}
*/