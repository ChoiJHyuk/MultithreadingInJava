package threadcommunication;

public class WaitNotifyExample {

    public static void main(String[] args) throws InterruptedException {
        StateClass stateClass = new StateClass();

        Thread change = new ChangeStateThread(stateClass);
        Thread check1 = new CheckStateThread(stateClass);
        Thread check2 = new CheckStateThread(stateClass);

        check1.start();
        check2.start();
        Thread.sleep(2000);
        change.start();
    }

    public static class ChangeStateThread extends Thread{
        private final StateClass stateClass;

        public ChangeStateThread(StateClass stateClass) {
            this.stateClass = stateClass;
        }

        @Override
        public void run() {
            stateClass.changeState();
        }
    }

    public static class CheckStateThread extends Thread{
        private final StateClass stateClass;

        public CheckStateThread(StateClass stateClass) {
            this.stateClass = stateClass;
        }

        @Override
        public void run() {
            try {
                stateClass.isTrueState();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class StateClass{
        private boolean state =false;

        public synchronized void changeState(){
            state =true;
            notifyAll();
        }

        public synchronized void isTrueState() throws InterruptedException {
            System.out.println("enter");
            while(!state){
                wait();
            }
            System.out.println("State is " + state);
        }
    }
}
