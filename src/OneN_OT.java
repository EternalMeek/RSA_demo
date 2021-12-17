import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class OneN_OT {

    public BigInteger Inquirer(RSA alg, BigInteger pubKey, BigInteger m, BigInteger irn, BigInteger rn){
        //Inquirer sends Enc(irn) + rn
        BigInteger send = alg.encrypt(irn.toString(), pubKey, m).add(rn);
        return send;
    }

    public String[] Agent(String enc, RSA alg, BigInteger priKey, BigInteger m) throws FileNotFoundException {
        //getting items and RN from file
        String[] items = new String[20];
        String[] codes = new String[20];
        BufferedReader br = new BufferedReader(new FileReader("items.txt"));
        String line;
        try {
            int x = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" "); //read line from file separated by space
                if(values.length >= 2){
                    items[x] = values[0];
                    codes[x] = values[1];
                    x++;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        String[] results = new String[20];
        BigInteger receive = new BigInteger(enc);

        //Agent send inquirer Dec(Enc(irn)+RN -RNi) + Ii
        //Agent decrypt the encrypted data inquirer sent after subtract RN of each item
        //then send the result combine with each item back to inquirer
        for(int i = 0; i < items.length; i++){
            BigInteger request = alg.decrypt(receive.subtract(new BigInteger(codes[i])).toString(), priKey, m);
//            System.out.println("Request item "+ (i+1) + " with possible RN: " + request.toString());
            BigInteger result = request.add(new BigInteger(items[i]));
            results[i] = result.toString();
        }
        return results;
    }

}
