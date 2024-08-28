package createthread;

public class CreateThreadExample1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                /*() -> {
                    System.out.println("We are in thread : " + Thread.currentThread().getName());
                    System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
                }*/
                new Runnable() {
                    //생성된 스레드가 실행할 코드 작성
                    @Override
                    public void run() {
                        System.out.println("We are in thread : " + Thread.currentThread().getName());
                        System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
                    }
                });

        thread.setName("New Worker Thread");
        //스레드의 우선순위 설정 1~10의 값을 줘야 한다. (상수 이용 가능)
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("We are in thread : " + Thread.currentThread().getName() + " before starting a new thread");
        //이 때 jvm이 새 스레드를 생성해 운영체제에 전달
        thread.start();
        System.out.println("We are in thread : " + Thread.currentThread().getName() + " after starting a new thread");
    }

}
