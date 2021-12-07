import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {

//        KeyGenerate keypair = new KeyGenerate();
//        BigInteger pubKey = new BigInteger("2702668153983242663194999485662717102777564024911927217832542042587224562938262870590928116377889915888650766663647941244363337962492440496175421");
//        BigInteger priKey = new BigInteger("3328710535346234025356452668877307089960263926851082043015504468716611819507424352015737534761444709474999602917579992947023805433784151372669685430542404911338825444294433209977077228211016916368628604552236953726117753251658015066131028211320254575421620655544963289520831919027160546103022465432652160344471878934068694261035593983447570350404550456971659554968686911545949156890799349701592409603438934115222858353441775339864871381746900242822291928530077855487897367627302171092616382812968569678796133007736791068129877728872375462529470979884186188507646477662610993304625796341807020139659865665548611438013") ;
//        BigInteger n = new BigInteger("4280740861645472970661029450947956043516835393961110277198041536561958529333602602777081408115538899175403974522203902423605541337335017937084631548476055200669335645190203506495405750933787446238515922355769820797568395450113145748941960337373791916645197484788852701797339595053725220199132296064955769051493529596062352539943673042977779219201942292894189043033445326564160122585040367143647361546388540578304447976994012616332339851600400920056338899405520737298321011334391875338874533648520042010284108036265296055663053035876318652642045543971065464897999067455596108121471217628594261893331001946743145974883");
        BigInteger[] keys = readFile();
        BigInteger pubKey = keys[2];
        BigInteger priKey = keys[4];
        BigInteger n = keys[3];
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

        OneN_OT ot = new OneN_OT();
        boolean running = true;
        while (running){
            System.out.println("Starting... \n Menu: ");
            System.out.printf('\u2022' + "(1) - Inquirer%n" +
                    '\u2022' + "(2) - Agent%n" +
                    '\u2022' + "(0) - Exit1%n");
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
