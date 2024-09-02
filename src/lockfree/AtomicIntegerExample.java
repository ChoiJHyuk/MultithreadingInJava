package lockfree;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    public static void main(String[] args) {
        int initialValue = 0 ;
        AtomicInteger atomicInteger = new AtomicInteger(initialValue);
        System.out.println(atomicInteger.getAndIncrement()); // 이전 값을 리턴하고 값 증가
        System.out.println(atomicInteger.incrementAndGet()); // 값을 증가하고 증가한 값을 리턴
        System.out.println(atomicInteger.getAndAdd(3)); // 이전 값 리턴하고 3 더하기
        System.out.println(atomicInteger.addAndGet(3)); // 3 더하고 이 값 리턴
    }
}
