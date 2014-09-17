package test;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class TestJMXServer {
    public static void main(String[] args) throws IOException {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            TestBean mbean = new TestBean("hello JMX from server!");
            ObjectName testMBeanName = new ObjectName("service:name=testMBean");
            mbs.registerMBean(mbean, testMBeanName);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

