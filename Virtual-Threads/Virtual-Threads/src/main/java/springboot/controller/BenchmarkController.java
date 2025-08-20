package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.PlatformReportService;
import springboot.service.ReportService;
import springboot.service.VirtualReportService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final ReportService reportService;
    private final PlatformReportService platformReportService;
    private final VirtualReportService virtualReportService;

    @Autowired
    public BenchmarkController(ReportService reportService,
                               PlatformReportService platformReportService,
                               VirtualReportService virtualReportService) {
        this.reportService = reportService;
        this.platformReportService = platformReportService;
        this.virtualReportService = virtualReportService;
    }

    @PostMapping("/{region}")
    public ResponseEntity<Map<String, Object>> benchmarkReports(@PathVariable String region) {
        Map<String, Object> results = new HashMap<>();

        // --- Simple (no threads)
        long startSimple = System.currentTimeMillis();
        reportService.generateReportForRegion(region);
        long durationSimple = System.currentTimeMillis() - startSimple;
        results.put("simple_ms", durationSimple);

        // --- Platform threads (fixed pool)
        long startPlatform = System.currentTimeMillis();
        platformReportService.generateReportForRegion(region);
        long durationPlatform = System.currentTimeMillis() - startPlatform;
        results.put("platform_ms", durationPlatform);

        // --- Virtual threads
        long startVirtual = System.currentTimeMillis();
        virtualReportService.generateReportForRegion(region);
        long durationVirtual = System.currentTimeMillis() - startVirtual;
        results.put("virtual_ms", durationVirtual);

        results.put("region", region);
        results.put("note", "Platform & Virtual are async, file creation may still be running");

        return ResponseEntity.ok(results);
    }

    @PostMapping("/full/{region}")
    public ResponseEntity<Map<String, Object>> fullBenchmark(@PathVariable String region) throws Exception {
        Map<String, Object> results = new HashMap<>();

        // --- Simple (no threads)
        long startSimple = System.currentTimeMillis();
        reportService.generateReportForRegion(region);
        long durationSimple = System.currentTimeMillis() - startSimple;
        results.put("simple_ms", durationSimple);

        // --- Platform threads
        long startPlatform = System.currentTimeMillis();
        CompletableFuture<Void> platformFuture = platformReportService.generateReportForRegionAsync(region);
        platformFuture.get(); // wait for completion
        long durationPlatform = System.currentTimeMillis() - startPlatform;
        results.put("platform_ms", durationPlatform);

        // --- Virtual threads
        long startVirtual = System.currentTimeMillis();
        CompletableFuture<Void> virtualFuture = virtualReportService.generateReportForRegionAsync(region);
        virtualFuture.get(); // wait for completion
        long durationVirtual = System.currentTimeMillis() - startVirtual;
        results.put("virtual_ms", durationVirtual);

        results.put("region", region);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/multiple/{region}/{count}")
    public ResponseEntity<Map<String, Object>> multipleBenchmark(@PathVariable String region,
                                                                 @PathVariable int count) throws Exception {
        Map<String, Object> results = new HashMap<>();

        // --- Platform threads
        long startPlatform = System.currentTimeMillis();
        List<CompletableFuture<Void>> platformFutures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            platformFutures.add(platformReportService.generateReportForRegionAsync(region));
        }
        CompletableFuture.allOf(platformFutures.toArray(new CompletableFuture[0])).get();
        long durationPlatform = System.currentTimeMillis() - startPlatform;
        results.put("platform_ms", durationPlatform);

        // --- Virtual threads
        long startVirtual = System.currentTimeMillis();
        List<CompletableFuture<Void>> virtualFutures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            virtualFutures.add(virtualReportService.generateReportForRegionAsync(region));
        }
        CompletableFuture.allOf(virtualFutures.toArray(new CompletableFuture[0])).get();
        long durationVirtual = System.currentTimeMillis() - startVirtual;
        results.put("virtual_ms", durationVirtual);

        results.put("region", region);
        results.put("report_count", count);

        return ResponseEntity.ok(results);
    }

}
