package springboot.service;

import com.google.protobuf.DescriptorProtos;
import com.grpc.StockRequest;
import com.grpc.StockResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import com.grpc.StockTradingServiceGrpc;

@Service
public class StockClientService {

    @GrpcClient("stockService")
    // Unary RPC call
    //private StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;

    // Streaming RPC call
    private StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

    /*public StockResponse getStockPrice(String stockSymbol) {
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(stockSymbol)
                .build();
        return serviceBlockingStub.getStockPrice(request);
    }
     */

    public void subscribeStockPrices(String stockSymbol) {
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(stockSymbol)
                .build();
        stockTradingServiceStub.subscribeStockPrices(request, new StreamObserver<StockResponse>() {
            @Override
            public void onNext(StockResponse stockResponse) {
                System.out.println("Stock Price Update: " + stockResponse.getStockSymbol() +
                        " Price: " + stockResponse.getPrice() +
                        " Timestamp: " + stockResponse.getTimestamp());

            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error receiving stock price updates: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Stock Price Update Stream Completed");
            }
        });
    }
}
