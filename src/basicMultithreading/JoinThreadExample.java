package basicMultithreading;

public class JoinThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(()->{
            for(int i = 0; i < 50; i++){
                System.out.println("Thread 1 : " + i);
            }
        });

        Thread two = new Thread(()->{
            for(int i = 0; i < 100; i++){
                System.out.println("Thread 2 : " + i);
            }
        });

        System.out.println("Before executing the threads");
        one.start();
        two.start();
        one.join();
        two.join();

        System.out.println("Done executing the threads");
    }
}