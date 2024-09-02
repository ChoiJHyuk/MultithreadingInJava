package lockfree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStackExample {

    public static class LockFreeStack<T> {

        private final AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private final AtomicInteger counter = new AtomicInteger(0)

        public void push(T value){
            StackNode<T> newHeadNode = new StackNode<>(value);
            //다른 스레드에서 이미 헤드를 변경하는 과정이 생길 수 있으므로 반복 작업 해야함
            while(true){
                StackNode<T> currentHeadNode = head.get();
                newHeadNode.next=currentHeadNode;
                if(head.compareAndSet(currentHeadNode,newHeadNode))
                    break;
                else
                    LockSupport.parkNanos(1);
            }
            counter.incrementAndGet();
        }

        public T pop(){
            StackNode<T> currentHeadNode = head.get();
            StackNode<T> newHeadNode;

            while(currentHeadNode!=null){
                newHeadNode = currentHeadNode.next;
                if(head.compareAndSet(currentHeadNode, newHeadNode)){
                    break;
                }else {
                    LockSupport.parkNanos(1);
                    currentHeadNode = head.get();
                }
            }
            counter.decrementAndGet();
            return currentHeadNode != null ? currentHeadNode.value : null;
        }

        public int getCounter() {
            return counter.get();
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value){
            this.value=value;
        }
    }
}
