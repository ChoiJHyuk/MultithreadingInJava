package threadcommunication;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreExample {
    public static void main(String[] args) {
        Barrier barrier = new Barrier(3);
        Thread thread1 = new Thread(new CoordinatedWorkRunner(barrier));
        Thread thread2 = new Thread(new CoordinatedWorkRunner(barrier));
        Thread thread3 = new Thread(new CoordinatedWorkRunner(barrier));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static class Barrier {
        private final int numberOfWorkers;
        private final Semaphore semaphore = new Semaphore(0);
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers) {
            this.numberOfWorkers = numberOfWorkers;
        }

        public void waitForOthers() throws InterruptedException {
            lock.lock();
            boolean isLastWorker = false;
            try {
                counter++;

                if (counter == numberOfWorkers) {
                    isLastWorker = true;
                }
            } finally {
                lock.unlock();
            }

            if (isLastWorker) {
                semaphore.release(numberOfWorkers - 1);
            } else {
                semaphore.acquire();
            }
        }
    }

    public static class CoordinatedWorkRunner implements Runnable {

        private final Barrier barrier;

        public CoordinatedWorkRunner(Barrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                task();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void task() throws InterruptedException {
            // Performing Part 1
            System.out.println(Thread.currentThread().getName()
                    + " part 1 of the work is finished");

            barrier.waitForOthers();

            // Performing Part2
            System.out.println(Thread.currentThread().getName()
                    + " part 2 of the work is finished");
        }
    }
}
