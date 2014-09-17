package test;

import java.io.IOException;

import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class TestJMXClient {

    public static void main(String[] args) throws IOException {
        JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:8888/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
        try {
            connector.connect();
            MBeanServerConnection client = connector.getMBeanServerConnection();
            ObjectName testMBeanName = ObjectName.getInstance("service:name=testMBean");
//            MBeanInfo mbInfo = client.getMBeanInfo(testMBeanName);
//            MBeanOperationInfo[] ops = mbInfo.getOperations();
            
//            TestBeanMBean fooProxy = JMX.newMBeanProxy(client, testMBeanName, TestBeanMBean.class);
//            fooProxy.fun();
            client.invoke(testMBeanName, "fun", null, null);
            client.setAttribute(testMBeanName, new Attribute("Field", new String(
                    "hello JMX from manager!")));
            System.out.println(client.invoke(testMBeanName, "fun", null, null));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
    }
}