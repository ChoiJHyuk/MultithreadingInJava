package coordinatethread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinateThreadExample4 {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(1000000000L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        List<FactorialThread> threads = new ArrayList<>();

        for (Long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (FactorialThread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }
        for (FactorialThread thread : threads) {
            thread.join(1000);
        }
        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("the calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ONE;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
