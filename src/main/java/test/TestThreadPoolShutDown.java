package test;

public class TestThreadPoolShutDown {
    
    class PrintTask implements Runnable {
        @Override
        public void run() {
            System.out.println("I am working");
        }
    }
    
    public static void main(String[] args) {
        
    }

}
