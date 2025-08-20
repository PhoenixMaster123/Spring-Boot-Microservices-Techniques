package springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadExecutorConfig {

    // Platform threads (fixed thread pool, e.g., 20 threads) (Otherwise this Bean is not needed)
    @Bean("platformThreadExecutor")
    public Executor platformThreadExecutor() {
        return Executors.newFixedThreadPool(20);
    }

    @Bean(name = "virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}