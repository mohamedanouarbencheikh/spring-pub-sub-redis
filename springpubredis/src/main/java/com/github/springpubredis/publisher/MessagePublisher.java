package com.github.springpubredis.publisher;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project springredis
 */
public interface MessagePublisher {

    void publish(final String message);
}
