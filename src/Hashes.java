import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Hashes {

    //Available algorithms: MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512

    public static String getHash(String input, String algos){
        String hashValue="";
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algos);
            messageDigest.update(input.getBytes());
            byte[] digestedBytes = messageDigest.digest();
            for(int i = 0; i< digestedBytes.length; i++) {
                sb.append(Integer.toString((digestedBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //String[] algos = {"MD2", "MD5", "SHA-256", "SHA-384", "SHA-512"};

     //   System.out.println(getHash("1",algos[0]));

        PrintWriter writer = new PrintWriter("lookup.txt");
        for(int j = 0; j < Integer.MAX_VALUE; j++) {
            String md2Hex = org.apache.commons.codec.digest.DigestUtils.md2Hex(String.valueOf(j));
            writer.write("md2, " + j + ", " + md2Hex + "\n");
            String md5Hex = org.apache.commons.codec.digest.DigestUtils.md5Hex(String.valueOf(j));
            writer.write("md5," + j + ", " + md5Hex + "\n");
            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(String.valueOf(j));
            writer.write("sha256, " + j + ", " + md5Hex + "\n");
            String sha384Hex = org.apache.commons.codec.digest.DigestUtils.sha384Hex(String.valueOf(j));
            writer.write("sha384, " + j + ", " + md5Hex + "\n");
            String sha512Hex = org.apache.commons.codec.digest.DigestUtils.sha512Hex(String.valueOf(j));
            writer.write("sha512, " + j + ", " + sha512Hex + "\n");
            //   System.out.println(sha512Hex);
        }

        writer.close();
    }

//    public static void writeFile(String name, int n, String hash){
//        try {
//            PrintWriter writer = new PrintWriter(name);
//            writer.println(n + ", " + hash + "\n");
//            writer.close();
//        }catch (IOException e){
//            System.out.println("Error occurred");
//            e.printStackTrace();
//        }
//    }
}
