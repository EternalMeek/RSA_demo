import java.math.BigInteger;
import java.util.Random;

public class KeyGenerate {

    private BigInteger p;
    private BigInteger q;
    private BigInteger pubKey;
    private BigInteger priKey;
    private BigInteger n;

    public KeyGenerate(){
        initial();
    }

    public BigInteger getP(){
        return p;
    }
    public BigInteger getQ(){
        return q;
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
        BigInteger p = new BigInteger(1024, 20, rnd); //create number of 1024 bits with (1-1/2^20) certainty prime
        while(!p.isProbablePrime(20)){
            p = new BigInteger(1024, 20, rnd);
        }
        this.p = p;
        //System.out.println("p is : " + p +  "\n---------------------------------+\n");

        //find a prime number q
        BigInteger q = new BigInteger(1022, 20, rnd); //create number of 1022 bits with (1-1/2^20) certainty prime
        while(!q.isProbablePrime(20)){
            q = new BigInteger(1022, 20, rnd);
        }
        this.q = q;
        //System.out.println("q is : " + q + "\n---------------------------------+\n");

        // n = pq
        n = p.multiply(q);
        // z = (p-1)(q-1)
        BigInteger z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        generate(z);
    }

    private void generate(BigInteger a){
        //find a number e that is relative prime to z
        //e is public key
        BigInteger e = new BigInteger(780, new Random());
        while(e.gcd(a).compareTo(BigInteger.ONE) != 0){
            e = new BigInteger(780, new Random());
        }
        pubKey = e;

        //find number d such that e*d = 1 mod z
        //d is private key
        priKey = pubKey.modInverse(a);
    }
}
