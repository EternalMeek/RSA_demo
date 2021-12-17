import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {

        BigInteger pubKey = null;
        BigInteger priKey = null;
        BigInteger n = null;
        System.out.println("What would you like to do?");
        System.out.printf('\u2022' + "(S) - Own key pair%n" +
                '\u2022' + "(O) - Other's%n" +
                '\u2022' + "(G) - Generate other's private key%n" +
                '\u2022' + "(E) - Exit%n");
        Scanner select = new Scanner(System.in);
        switch(select.next().charAt(0)) {
            case 'S':
                BigInteger[] keys = readFile();
                pubKey = keys[2];
                priKey = keys[4];
                n = keys[3];
                break;
            case 'O':
                pubKey = new BigInteger("3");
                priKey = new BigInteger("425507");
                n = new BigInteger("639889");
                break;
            case 'G':
                System.out.print("Enter p: ");
                String s1 = select.next();
                System.out.print("Enter n:");
                String s2 = select.next();
                System.out.print("Enter e: ");
                String s3 = select.next();;
                prikeyFinder(s1, s2, s3);
                return;
            default:
                return ;
        }

        RSA rsa = new RSA();
        String[] tests = {"2", "6", "7", "13", "22", "29", "52", "1001"};
        int i = 0;
        while(i < tests.length){
            String enc = rsa.encrypt(tests[i], pubKey, n).toString();
            String dec = rsa.decrypt(enc, priKey, n).toString();
            if(!tests[i].equals(dec)) {
                System.out.println("Key generation failed.");
                return;
            }else{
                i++;
            }
        }
        System.out.println("Key generation passed.");

//        Functions functions = new Functions();
//        System.out.println("PRIME: " + functions.primeCheck("555618491"));

        OneN_OT ot = new OneN_OT();
        boolean running = true;
        while (running){
            System.out.println("Starting... \n Menu: ");
            System.out.printf('\u2022' + "(I) - Inquirer%n" +
                    '\u2022' + "(A) - Agent%n" +
                    '\u2022' + "(E) - Exit%n");
            Scanner input = new Scanner(System.in);
            switch (input.next().charAt(0)){
                case 'I':
                    System.out.print("Enter your random number: ");
                    BigInteger irn = BigInteger.valueOf(input.nextInt());
                    System.out.print("Enter the code of item you want: ");
                    BigInteger code = BigInteger.valueOf(input.nextInt());
                    System.out.println(ot.Inquirer(rsa, pubKey, n, irn, code));
                    break;
                case 'A':
                    System.out.print("Enter request: ");
                    String req = input.next();
                    String[] outputs = ot.Agent(req, rsa, priKey, n);
                    for(String s : outputs)
                        System.out.println(s);
                    break;
                case 'E':
                    running = false;
                    break;
                default:
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

    //find the private key if p or q is found
    public static void prikeyFinder(String sp, String sn, String se){
        BigInteger p = new BigInteger(sp);
        if(!p.isProbablePrime(18)) {
            System.out.println("P is not a prime number.");
            return;
        }
        else if(!new BigInteger(sn).remainder(p).equals(BigInteger.ZERO)){
            System.out.println("P is not a factor of n.");
            return;
        }
        BigInteger q = new BigInteger(sn).divide(p);
        BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = new BigInteger(se);
        if(e.gcd(z).compareTo(BigInteger.ONE) != 0){
            System.out.println("E not relative prime to z.");
            return;
        }
        BigInteger d = e.modInverse(z);
        System.out.println("private key is : " + d);
    }
}
