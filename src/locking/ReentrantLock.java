package locking;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class ReentrantLock {

    public static void main(String[] args) {
        PricesContainer pricesContainer = new PricesContainer();
        Thread thread1 = new PriceUpdater(pricesContainer);
        Thread thread2 = new PriceGetter(pricesContainer);

        thread1.start();
        thread2.start();
    }

    public static class PricesContainer {
        private Lock lockObject = new java.util.concurrent.locks.ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public void setLockObject(Lock lockObject) {
            this.lockObject = lockObject;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }

        public Lock getLockObject() {
            return lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while (true) {
                pricesContainer.getLockObject().lock();
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    pricesContainer.setBitcoinPrice(random.nextInt(20000));
                    pricesContainer.setEtherPrice(random.nextInt(20000));
                    pricesContainer.setLitecoinPrice(random.nextInt(20000));
                    pricesContainer.setBitcoinCashPrice(random.nextInt(20000));
                    pricesContainer.setRipplePrice(random.nextInt(20000));
                } finally {
                    pricesContainer.getLockObject().unlock();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static class PriceGetter extends Thread {
        private PricesContainer pricesContainer;

        public PriceGetter(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while (true) {
                if (pricesContainer.getLockObject().tryLock()) {
                    try {
                        System.out.println(pricesContainer.getBitcoinPrice() + " " +
                                pricesContainer.getEtherPrice() + " " +
                                pricesContainer.getLitecoinPrice() + " " +
                                pricesContainer.getBitcoinCashPrice() + " " +
                                pricesContainer.getRipplePrice());
                    } finally {
                        pricesContainer.getLockObject().unlock();
                    }
                }
                else{
                    System.out.println(System.currentTimeMillis());
                }
            }
        }
    }
}
