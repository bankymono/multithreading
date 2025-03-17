package race.condition;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

            incrementingThread.join();
            decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");

    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }


        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }


        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class InventoryCounter {
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        private int items = 0;

        public void increment(){
            synchronized (lock1) {
                items++;
            }

        }

        public void decrement(){
            synchronized (lock2){
                items--;
            }

        }

        public int getItems(){
            synchronized (lock1){
                return items;
            }

        }

    }
}
