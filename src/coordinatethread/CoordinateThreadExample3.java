package coordinatethread;

import java.math.BigInteger;

public class CoordinateThreadExample3 {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("10000000")));
        thread.setDaemon(true);
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+"="+pow(base,power));
        }
        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result =BigInteger.ONE;
            for(BigInteger i = BigInteger.ZERO; i.compareTo(power)!=0; i= i.add(BigInteger.ONE)){
                result = result.multiply(base);
            }
            return result;
        }
    }
}
