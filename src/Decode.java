/**
 *
 * Online resource
 * Credit to : https://www.youtube.com/watch?v=nLiWndd1CCA&t=736s
 *
 */

import java.math.*;
import java.util.*;

public class Decode {

    private static final int BIT_LENGTH = 100;
    private static final int THREAD_COUNT = 16;

    public static void main(String args[]) {
        int bits1 = new Random().nextInt(BIT_LENGTH - 3) + 2;
        int bits2 = BIT_LENGTH - bits1;

        BigInteger p1 = new BigInteger(bits1, 50, new Random());
        BigInteger p2 = new BigInteger(bits2, 50, new Random());
        //BigInteger largeComposite = p1.multiply(p2);
        BigInteger largeComposite = new BigInteger("6276566754097137755466764816000720600280824644917074128203468518932072252994457386277825705171623221195825316938529495056106904279231186993560643288690709");

        //System.out.println(p1 + ", " + bits1 + " bits");
        //System.out.println(p2 + ", " + bits2 + " bits");
        System.out.println("largeComposite= " + largeComposite + ", " + largeComposite.bitLength() + "bits");
        BigInteger square = bigSQRT(largeComposite);
        System.out.println("Square root = " + square);
        System.out.println();

        //divide by 2, the only even prime number
        if (largeComposite.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
            System.out.println("First prime factor is 2");
            System.out.println("Second prime factor is " + largeComposite.divide(BigInteger.TWO));
            return;
        }

        //groupCount will hold the amount of numbers that each Thread will perform calculations on
        BigInteger groupCount = square.divide(new BigInteger("" + THREAD_COUNT)).add(BigInteger.ONE);
        //System.out.println(groupCount);

        FoundFactor ff = new FoundFactor();
        BigInteger from = null;
        BigInteger to = null;

        for (int i = 0; i < THREAD_COUNT; i++) {
            from = new BigInteger("" + i).multiply(groupCount);
            if (i == 0) {
                from = new BigInteger("3");
            }
            //check to see if from is an even number, if so, subtract 1
            if (from.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
                from = from.subtract(BigInteger.ONE);
            }
            to = new BigInteger("" + (i + 1)).multiply(groupCount);
            System.out.println(from + ", " + to);

            new FindPrimeFactors(largeComposite, from, to, "thread"+i, ff);
        }


        Thread mainThread = Thread.currentThread(); //main thread
        System.out.println("Active threads for this thread group: "+ Thread.activeCount());
        while(Thread.activeCount() > 1){
            if (ff.foundIt.equals("FOUND")){
                //System.out.println("Found the factor!!!!!");
                for(int i = 0; i < THREAD_COUNT; i++){
                    Thread t = threadRef("thread"+i);
                    if(t != null){
                        t.interrupt();
                        //System.out.println("----Interupted-----");
                    }
                }
            }
            try{
                mainThread.sleep(200);
            }catch(InterruptedException e){
                System.out.println("Main thread interrupted");
            }
        }
        //System.out.println("Active threads for this thread group: " + Thread.activeCount());
    }

    static Thread threadRef(String name) {
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Thread temp : map.keySet()) {
            if (temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }

    public static BigInteger bigSQRT(BigInteger x) {
        BigInteger two = new BigInteger("2"), y;
        int i = 0;
        for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; y = ((x.divide(y)).add(y)).divide(two), i++) ;
        if (x.compareTo(y.multiply(y)) == 0) {
            return y;
        } else {
            return y.add(BigInteger.ONE);
        }
    }
}

class FoundFactor {
    public static String foundIt = "";
}

class FindPrimeFactors extends Thread {
    BigInteger largeComposite, start, end;
    String threadName = "";
    static FoundFactor ff;

    FindPrimeFactors(BigInteger largeComposite, BigInteger start, BigInteger end, String threadName, FoundFactor ff) {
        super(threadName);
        this.largeComposite = largeComposite;
        this.start = start;
        this.end = end;
        this.threadName = threadName;
        this.ff = ff; //reference to original object ;
        start();
    }

    @Override
    public void run() { //this is where the new thread starts
        searchRange();
    }

    boolean searchRange() {
        long startTime = System.currentTimeMillis();
        boolean returnVal = false;
        while (true) {
            if (start.isProbablePrime(5)) {
                if (largeComposite.mod(start).bitLength() == 0) {
                    System.out.println("First prime factor is " + start);
                    System.out.println("Second prime factor is " + largeComposite.divide(start));
                    System.out.println(threadName = "found the factors");
                    System.out.println("Time to find prime factors: " + (System.currentTimeMillis() - startTime) + "millseconds");
                    ff.foundIt = "FOUND";
                    returnVal = true;
                    break;
                }
                if (start.compareTo(end) == 1) {
                    //System.out.println(threadName + " reached the end");
                    break;
                }
                if (Thread.currentThread().isInterrupted()) {
                    //System.out.println(threadName + " interrupted");
                    break;
                }
            }
            start = start.add(BigInteger.TWO);
        }
        return returnVal;
    }
}