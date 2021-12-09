import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {

//        KeyGenerate keypair = new KeyGenerate();
        BigInteger pubKey = new BigInteger("3");
        BigInteger priKey = new BigInteger("425507") ;
        BigInteger n = new BigInteger("639889");
//        BigInteger[] keys = readFile();
//        BigInteger pubKey = keys[2];
//        BigInteger priKey = keys[4];
//        BigInteger n = keys[3];
        RSA rsa = new RSA();
        String[] tests = {"2", "6", "7", "13", "22", "29", "52", "1001"};
        int i = 0;
        while(i < tests.length){
            String enc = rsa.encrypt(tests[i], pubKey, n).toString();
            String dec = rsa.decrypt(enc, priKey, n).toString();
            if(!tests[i].equals(dec)) {
                System.out.println("Key generation failed.");
                i++;
            }else{
                System.out.println("Key generation passed.");
                i++;
            }
        }

//        BigInteger p = new BigInteger("11003");
//        BigInteger q = new BigInteger("50497");
//        BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
//        BigInteger e = new BigInteger("37");
//        BigInteger d = e.modInverse(z);
//        System.out.println("private key is : " + d.toString());

//        Functions functions = new Functions();
//        System.out.println("PRIME: " + functions.primeCheck("555618491"));

        OneN_OT ot = new OneN_OT();
        boolean running = true;
        while (running){
            System.out.println("Starting... \n Menu: ");
            System.out.printf('\u2022' + "(1) - Inquirer%n" +
                    '\u2022' + "(2) - Agent%n" +
                    '\u2022' + "(0) - Exit%n");
            Scanner input = new Scanner(System.in);
            switch (input.nextInt()){
                case 1:
                    System.out.print("Enter your random number: ");
                    BigInteger irn = BigInteger.valueOf(input.nextInt());
                    System.out.print("Enter the code of item you want: ");
                    BigInteger code = BigInteger.valueOf(input.nextInt());
                    System.out.println(ot.Inquirer(rsa, pubKey, n, irn, code));
                    break;
                case 2:
                    System.out.print("Enter request: ");
                    String req = input.next();
                    String[] outputs = ot.Agent(req, rsa, priKey, n);
                    for(String s : outputs)
                        System.out.println(s);
                    break;
                case 0:
                    running = false;
                    break;
            }
        }

//        System.out.println("Your RSA public key is :\n" + keypair.getPubKey()
//                + "\n------------------------------------\n"
//                + "The n is :\n" + keypair.getN() +"\n"+keypair.getN().bitLength() + "bits"
//                + "\n===========================================================\n"
//                + "Private key is :\n" + keypair.getPriKey()
//                + "\n------------------------------------\n" );
    }

    public static BigInteger[] readFile() throws FileNotFoundException {
        BigInteger[] keys = new BigInteger[5];
        String fname = "keys.txt";
        File file = new File(fname);
        if(!file.exists()){
            PrintWriter pw = new PrintWriter(fname);
            KeyGenerate keyset = new KeyGenerate();
            pw.write("p: " + keyset.getP() + " " + keyset.getP().bitLength() + " bits." + "\n");
            pw.write("q: " + keyset.getQ() + " " + keyset.getQ().bitLength() + " bits." + "\n");
            pw.write("e: " + keyset.getPubKey() + " " +keyset.getPubKey().bitLength() + " bits." + "\n");
            pw.write("n: " + keyset.getN() + " " + keyset.getN().bitLength() + "bits." + "\n");
            pw.write("d: " + keyset.getPriKey() + " " + keyset.getPriKey().bitLength()+ " bits." + "\n");
            keys[0] = keyset.getP();
            keys[1] = keyset.getQ();
            keys[2] = keyset.getPubKey();
            keys[3] = keyset.getN();
            keys[4] = keyset.getPriKey();
            pw.close();
        } else{
            try{
                BufferedReader br = new BufferedReader(new FileReader(fname));
                String line;
                int i = 0;
                while ((line = br.readLine()) != null){
                    String[] values = line.split(" ");
                    if(values.length >= 2)
                        keys[i++] = new BigInteger(values[1]);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return keys;
    }
}
