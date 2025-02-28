package otherConcepts;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private final Lock lockA = new ReentrantLock(true);
    private final Lock lockB = new ReentrantLock(true);

    public void workerOne() {
        lockA.lock();
        System.out.println("Worker One acquired lockA");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lockB.lock();
        System.out.println("Worker One acquired lockB");
        lockA.unlock();
        lockB.unlock();
    }

    public void workerTwo() {
        lockB.lock();
        System.out.println("Worker Two acquired lockB");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lockA.lock();
        System.out.println("Worker Two acquired lockA");
        lockA.unlock();
        lockB.unlock();
    }

    public static void main(String[] args) {
        DeadLockDemo demo = new DeadLockDemo();

        new Thread(demo::workerOne, "Worker One").start();
        new Thread(demo::workerTwo, "Worker Two").start();

        new Thread(() -> {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
            while(true) {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if(threadIds != null) {
                    System.out.println("Deadlock detected!");
                    ThreadInfo[] threadInfo = mxBean.getThreadInfo(threadIds);
                    for (long threadId : threadIds) {
                        System.out.println("Thread with ID " + threadId + " is in Deadlock");
                    }

                    break;
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
