import java.math.BigInteger;
import java.util.Random;

public class KeyGenerate {

    private BigInteger pubKey;
    private BigInteger priKey;
    private BigInteger n;

    public KeyGenerate(){
        initial();
    }

    public BigInteger getPubKey(){
        return pubKey;
    }
    public BigInteger getPriKey(){
        return priKey;
    }
    public BigInteger getN(){
        return n;
    }


    private void initial(){
        Random rnd = new Random();
        //find a prime number p
        BigInteger p = new BigInteger(1024, 30, rnd);
//        while(!validate(p)){
//            p = new BigInteger(1024, 30, rnd);
//        }

        //find a prime number q
        BigInteger q = new BigInteger(1022, 30, rnd);
//        while(!validate(q)) {
//            q = new BigInteger(1022, 30, rnd);
//        }

        n = p.multiply(q);
        BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        generate(z);
    }

    private void generate(BigInteger a){
        //find a number e that is relative prime to z
        //e is public key
        BigInteger e = new BigInteger(320, new Random());
        while(e.gcd(a).compareTo(BigInteger.ONE) != 0){
            e = new BigInteger(320, new Random());
        }
        pubKey = e;

        //find number d such that e*d = 1 mod z
        //d is private key
        priKey = pubKey.modInverse(a);
    }

    //check if random generated number really is prime
    private boolean validate(BigInteger a){
        Functions function = new Functions();
        if(!function.primeCheck(a.toString()).equals("true"))
            return true;
        return false;
    }

}
