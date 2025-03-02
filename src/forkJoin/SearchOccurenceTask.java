package forkJoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchOccurenceTask extends RecursiveTask<Integer> {
    int[] arr;
    int start;
    int end;
    int searchElement;

    public SearchOccurenceTask(int[] arr, int start, int end, int searchElement) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
        int size = end - start + 1;
        if(size > 50) {
            int mid = (start + end) / 2;
            SearchOccurenceTask task1 = new SearchOccurenceTask(arr, start, mid, searchElement);
            SearchOccurenceTask task2 = new SearchOccurenceTask(arr, mid+1, end, searchElement);
            task1.fork();
            task2.fork();
            Integer join = task1.join() + task2.join();
            return join;
        } else {
            return search();
        }
    }

    private Integer search(){
        int count = 0;
        for(int i = start; i <= end; i++) {
            if(arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }
}

class FJPDemo {
    public static void main(String[] args) {
        int[] arr = new int[100];
        Random random = new Random();
        for(int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10) + 1;
        }

        int searchElement = random.nextInt(10) + 1;

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        SearchOccurenceTask task = new SearchOccurenceTask(arr, 0, arr.length - 1, searchElement);
        Integer occurence = pool.invoke(task);
        System.out.println("Array is : " + Arrays.toString(arr));
        System.out.printf("%d found %d times", searchElement, occurence);
    }
}