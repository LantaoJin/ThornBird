/*
 * QueueSamplerMXBean.java - MXBean interface describing the management
 * operations and attributes for the QueueSampler MXBean. In this case
 * there is a read-only attribute "QueueSample" and an operation "clearQueue".
 */

package org.jmx.example;

public interface QueueSamplerMXBean {
    public QueueSample getQueueSample();
    public void clearQueue();
}
