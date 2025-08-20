package springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.entity.Customer;
import springboot.repository.CustomerRepository;
import springboot.util.CsvReportUtil;

import java.util.List;

@Slf4j
@Service
public class ReportService {

    private final CustomerRepository customerRepository;

    @Autowired
    public ReportService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void generateReportForRegion(String region) {
        log.info("Generating report for region {} | {}", region, Thread.currentThread().getName());

        List<Customer> customers = customerRepository.findByRegion(region);

        try {
            CsvReportUtil.writeCustomersToCsv("simple_" + region, customers);
        } catch (Exception e) {
            log.error("Error while generating report for region {}", region, e);
        }
    }
}
