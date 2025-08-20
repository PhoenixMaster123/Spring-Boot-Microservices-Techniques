package springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springboot.entity.Customer;
import springboot.repository.CustomerRepository;
import springboot.util.CsvReportUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class PlatformReportService {

    private final CustomerRepository repository;


    //Executor executor= Executors.newFixedThreadPool(5);
    private final Executor executor;

    @Autowired
    public PlatformReportService(CustomerRepository repository, @Qualifier("platformThreadExecutor") Executor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    public void generateReportForRegion(String region) {

        executor.execute(()->{
            log.info("Platform generating report for region: {} | {}", region, Thread.currentThread());

            List<Customer> customers = repository.findByRegion(region);//1
            try {
                CsvReportUtil.writeCustomersToCsv("platform_" + region, customers);//2
            } catch (Exception e) {
                System.out.println("❌ Platform Error writing report for region: " + region);
            }
        });
    }

    // Asynchronous
    public CompletableFuture<Void> generateReportForRegionAsync(String region) {
        return CompletableFuture.runAsync(() -> {
            log.info("Platform generating report for region: {} | {}", region, Thread.currentThread());
            List<Customer> customers = repository.findByRegion(region);
            try {
                CsvReportUtil.writeCustomersToCsv("platform_" + region, customers);
            } catch (Exception e) {
                log.error("❌ Platform Error writing report for region: {}", region, e);
            }
        }, executor);
    }

}