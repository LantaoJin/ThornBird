package org.jmx.example;

public class MyTriggleThread implements Runnable{
    private HelloMBean hello;
    private boolean running;
    private int i;
    
    public MyTriggleThread(HelloMBean hello) {
        this.running = true;
        this.hello = hello;
    }
    
    public void shutdown() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            hello.setCacheSize(i++);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

}
