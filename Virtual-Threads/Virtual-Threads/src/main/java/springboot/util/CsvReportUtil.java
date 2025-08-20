package springboot.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import springboot.entity.Customer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvReportUtil {

    public static void writeCustomersToCsv(String region, List<Customer> customers) throws IOException {
        long start = System.currentTimeMillis(); // ⏱ start timing

        Path path = Paths.get("reports", region + "_report.csv");
        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT
                             .builder()
                             .setHeader("ID", "Name", "Email", "Region")
                             .build())) {

            for (Customer customer : customers) {
                csvPrinter.printRecord(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getRegion()
                );
            }
        }

        long duration = System.currentTimeMillis() - start; // ⏱ end timing
        System.out.println("⏱ Report " + region + " generated in " + duration + " ms");
    }
}
