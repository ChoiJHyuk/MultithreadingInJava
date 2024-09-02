package lockfree;

import java.util.concurrent.atomic.AtomicReference;

public class CompareAndSetExample {
    public static void main(String[] args) {
        String oldName = "old name";
        String newName = "new name";
        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);
        atomicReference.set("hello");
        if(atomicReference.compareAndSet(oldName, newName)){
            System.out.println("change");
        }else{
            System.out.println("not change");
        }
    }
}
