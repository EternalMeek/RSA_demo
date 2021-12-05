import java.math.BigInteger;

public class RSA {

    //return the encrypted value of message
    public BigInteger encrypt(String s, BigInteger exp, BigInteger m){
        BigInteger enc = new BigInteger(s).modPow(exp, m);
        return enc;
    }

    //reveal the message after decrypt it
    public BigInteger decrypt(String s, BigInteger exp, BigInteger m){
        BigInteger dec = new BigInteger(s).modPow(exp, m);
        return dec;
    }
}
