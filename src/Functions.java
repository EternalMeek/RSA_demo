import java.math.BigInteger;
import java.util.ArrayList;

public class Functions {

    public String primeCheck(String s){
        BigInteger a = new BigInteger(s);
        String ans="";
        ArrayList<BigInteger> divs = new ArrayList<>();

//        if(a.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0
//                || a.mod(BigInteger.valueOf(3)).compareTo(BigInteger.ZERO) == 0 )
//            return ans="Divisible by 2 or 3";

        BigInteger i = BigInteger.valueOf(5);
        while(i.compareTo(a.sqrt())<=0){
            if(a.mod(i).compareTo(BigInteger.ZERO) == 0)
                divs.add(i);
            if(a.mod(i.add(BigInteger.valueOf(2))).compareTo(BigInteger.ZERO) == 0)
                divs.add(i.add(BigInteger.valueOf(2)));
            i = i.add(BigInteger.valueOf(6));
        }

        if(divs.size() == 0)
            return ans = "true";
        else
            for(BigInteger x : divs)
                ans += a + "\n is divisible by \n" + x + "\n and \n" + a.divide(x) + "\n...............................\n";

        return ans;
    }
}
