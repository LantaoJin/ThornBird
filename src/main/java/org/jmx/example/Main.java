/*
 * Main.java - main class for the Hello MBean and QueueSampler MXBean example.
 * Create the Hello MBean and QueueSampler MXBean, register them in the platform
 * MBean server, then wait forever (or until the program is interrupted).
 */

package org.jmx.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main {
    /* For simplicity, we declare "throws Exception".
       Real programs will usually want finer-grained exception handling. */
    public static void main(String[] args) throws Exception {
        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	// Construct the ObjectName for the Hello MBean we will register
	ObjectName mbeanName = new ObjectName("com.jmx.example:type=Hello");

	// Create the Hello World MBean
	Hello mbean = new Hello();

	// Register the Hello World MBean
	mbs.registerMBean(mbean, mbeanName);

        // Construct the ObjectName for the QueueSampler MXBean we will register
        ObjectName mxbeanName = new ObjectName("com.jmx.example:type=QueueSampler");

        // Create the Queue Sampler MXBean
        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        QueueSampler mxbean = new QueueSampler(queue);

        // Register the Queue Sampler MXBean
        mbs.registerMBean(mxbean, mxbeanName);

        // Wait forever
        System.out.println("Waiting for incoming requests...");
        try {
            System.out.println("\nPress <Enter> to begin...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyTriggleThread triggle = new MyTriggleThread(mbean);
        new Thread(triggle).start();
        try {
            System.out.println("\nPress <Enter> to stop...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        triggle.shutdown();
        Thread.sleep(Long.MAX_VALUE);
    }
}
