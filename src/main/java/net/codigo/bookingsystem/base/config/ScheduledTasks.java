package net.codigo.bookingsystem.base.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.codigo.bookingsystem.base.entity.enums.Status;
import net.codigo.bookingsystem.purchase.PurchaseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final PurchaseService purchaseService;
    @Async
    @Scheduled(fixedRate = 600000) // 300,000 milliseconds = 5 minutes -- to test local --
    public void updateStatus() {
        String message = purchaseService.findByStatus(Status.STILL);
        log.info("Message : {}",message);
    }
}
