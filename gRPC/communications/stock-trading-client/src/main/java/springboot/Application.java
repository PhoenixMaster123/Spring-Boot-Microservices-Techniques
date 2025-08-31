package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboot.service.StockClientService;

@SpringBootApplication
public class Application /*implements CommandLineRunner*/ {

    private final StockClientService stockClientService;

    @Autowired
    public Application(StockClientService stockClientService) {
        this.stockClientService = stockClientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Note: We implement CommandLineRunner only for quick testing purposes.
    /*@Override
    public void run(String... args) throws Exception {
        //System.out.println("Grpc Client: Stock Price for GOOGL is: " + stockClientService.getStockPrice("GOOGL"));
        stockClientService.subscribeStockPrices("GOOGL");
    }
     */
}
