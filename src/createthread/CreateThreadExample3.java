package createthread;

public class CreateThreadExample3 {
    public static void main(String[] args) {
        Thread thread = new NewThread();
        thread.start();
    }
    private static class NewThread extends Thread{
        @Override
        public void run() {
            System.out.println("hello from " + this.getName());
        }
    }
}
