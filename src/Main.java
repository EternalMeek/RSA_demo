import java.io.*;
import java.math.BigInteger;

public class Main {

    public static void main (String[] args) throws FileNotFoundException {

//        KeyGenerate keypair = new KeyGenerate();
//        BigInteger pubkey = new BigInteger("2702668153983242663194999485662717102777564024911927217832542042587224562938262870590928116377889915888650766663647941244363337962492440496175421");
//        BigInteger priKey = new BigInteger("3328710535346234025356452668877307089960263926851082043015504468716611819507424352015737534761444709474999602917579992947023805433784151372669685430542404911338825444294433209977077228211016916368628604552236953726117753251658015066131028211320254575421620655544963289520831919027160546103022465432652160344471878934068694261035593983447570350404550456971659554968686911545949156890799349701592409603438934115222858353441775339864871381746900242822291928530077855487897367627302171092616382812968569678796133007736791068129877728872375462529470979884186188507646477662610993304625796341807020139659865665548611438013") ;
//        BigInteger n = new BigInteger("4280740861645472970661029450947956043516835393961110277198041536561958529333602602777081408115538899175403974522203902423605541337335017937084631548476055200669335645190203506495405750933787446238515922355769820797568395450113145748941960337373791916645197484788852701797339595053725220199132296064955769051493529596062352539943673042977779219201942292894189043033445326564160122585040367143647361546388540578304447976994012616332339851600400920056338899405520737298321011334391875338874533648520042010284108036265296055663053035876318652642045543971065464897999067455596108121471217628594261893331001946743145974883");
        BigInteger[] keys = readFile();
        BigInteger pubkey = keys[2];
        BigInteger priKey = keys[4];
        BigInteger n = keys[3];
        RSA rsa = new RSA();
        String[] tests = {"2", "6", "7", "13", "22", "29", "52", "1001"};
        int i = 0;
        while(i < tests.length){
            String enc = rsa.encrypt(tests[i], pubkey, n).toString();
            String dec = rsa.decrypt(enc, priKey, n).toString();
            if(!tests[i].equals(dec)) {
                System.out.println("Key generation failed.");
                i++;
            }else{
                System.out.println("Key generation passed.");
                i++;
            }
        }
//        String enc = rsa.encrypt("22", pubkey, n).toString();
//        String dec = rsa.decrypt(enc, priKey, n).toString();
//        System.out.println("enc is : " + enc + "\n");
//        System.out.println("dec is : " + dec + "\n");
//        String[] tests = {"2", "6", "7", "13", "29", "52", "1001"};
//        int i = 0;
//        while(i < tests.length){
//            if(!tests[i].equals(rsa.decrypt(rsa.encrypt(tests[i], keypair.getPubKey(), keypair.getN()).toString(), keypair.getPriKey(), keypair.getN()))){
//                System.out.println("Key generation failed, regenerating key");
//                keypair = new KeyGenerate();
//                i++;
//            }
//            else
//                i++;
//        }
//        System.out.println("Your RSA public key is :\n" + keypair.getPubKey()
//                + "\n------------------------------------\n"
//                + "The n is :\n" + keypair.getN() +"\n"+keypair.getN().bitLength() + "bits"
//                + "\n===========================================================\n"
//                + "Private key is :\n" + keypair.getPriKey()
//                + "\n------------------------------------\n" );

//        Functions functions = new Functions();
//        //String s = functions.primeCheck("5011881136975211310478237074422215001456615111800358007244391642988177092423881373671901184354140120792151356405695649929207972383490882320620297156067981878482874430456032475964317248770282713655041101056132892115231548089612995573254208400360330877398124052165933111091929132478792329883458258156405373703139095092941207139498371248912914821364675932439010410274592077676107203451746190380648244293244049013286370656101462282509794448945925813162350076741306021610674328531931429024989935296280273751274865294425926222984694820260090040384876187208515911601490818206946736494544863784410178272328912852746387975881");
//        BigInteger i = new BigInteger("5011881136975211310478237074422215001456615111800358007244391642988177092423881373671901184354140120792151356405695649929207972383490882320620297156067981878482874430456032475964317248770282713655041101056132892115231548089612995573254208400360330877398124052165933111091929132478792329883458258156405373703139095092941207139498371248912914821364675932439010410274592077676107203451746190380648244293244049013286370656101462282509794448945925813162350076741306021610674328531931429024989935296280273751274865294425926222984694820260090040384876187208515911601490818206946736494544863784410178272328912852746387975881");
//        String s = i.isProbablePrime(50)? "Prime Number" : "Not Prime Number";
//        System.out.println(s);
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
