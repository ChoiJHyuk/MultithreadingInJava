package threadcommunication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static class SimpleCountDownLatch {
        private int count;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public SimpleCountDownLatch(int count) {
            this.count = count;
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        public void await() throws InterruptedException {
            lock.lock();
            try {
                while (count > 0) {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }

        public void countDown() {
            lock.lock();
            try {
                if (count > 0) {
                    count--;
                    if (count == 0) {
                        condition.signalAll();
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }

}
