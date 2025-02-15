package executorService;

import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> result = service.submit(new ReturnValueTask());

        System.out.println(result.get());
    }
}

class ReturnValueTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 13;
    }
}
