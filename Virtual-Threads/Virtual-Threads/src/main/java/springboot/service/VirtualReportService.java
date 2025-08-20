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

@Slf4j
@Service
public class VirtualReportService {

    private final CustomerRepository repository;

    private final Executor virtualThreadExecutor;

    @Autowired
    public VirtualReportService(CustomerRepository repository, @Qualifier("virtualThreadExecutor") Executor virtualThreadExecutor) {
        this.repository = repository;
        this.virtualThreadExecutor = virtualThreadExecutor;
    }


    public void generateReportForRegion(String region) {

        virtualThreadExecutor.execute(() -> {
            log.info("Virtual generating report for region: {} | {}", region, Thread.currentThread());

            List<Customer> customers = repository.findByRegion(region);//1
            try {
                CsvReportUtil.writeCustomersToCsv("virtual_" + region, customers);//2
            } catch (Exception e) {
                System.out.println("❌ Virtual Error writing report for region: " + region);
            }
        });
    }

    // Asynchronous
    public CompletableFuture<Void> generateReportForRegionAsync(String region) {
        return CompletableFuture.runAsync(() -> {
            log.info("Virtual generating report for region: {} | {}", region, Thread.currentThread());
            List<Customer> customers = repository.findByRegion(region);
            try {
                CsvReportUtil.writeCustomersToCsv("virtual_" + region, customers);
            } catch (Exception e) {
                log.error("❌ Virtual Error writing report for region: {}", region, e);
            }
        }, virtualThreadExecutor);
    }

}