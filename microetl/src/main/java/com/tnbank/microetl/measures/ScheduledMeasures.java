package com.tnbank.microetl.measures;

import com.tnbank.microetl.entities.DimTransaction;
import com.tnbank.microetl.entities.DimTransactionRequest;
import com.tnbank.microetl.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduledMeasures {
    private final AccountTypeMeasureRepository accountTypeMeasureRepository;
    private final RequestDayMeasureRepository requestDayMeasureRepository;
    private final TransactionDayMeasureRepository transactionDayMeasureRepository;
    private final DimAccountRepository dimAccountRepository;
    private final DimTransactionRepository dimTransactionRepository;
    private final DimTransactionRequestRepository dimTransactionRequestRepository;

    private AccountTypeMeasure accountTypeMeasure;
    private RequestDayMeasure requestDayMeasure;
    private TransactionDayMeasure transactionDayMeasure;

    @Autowired
    public ScheduledMeasures(
            AccountTypeMeasureRepository accountTypeMeasureRepository,
            RequestDayMeasureRepository requestDayMeasureRepository,
            TransactionDayMeasureRepository transactionDayMeasureRepository,
            DimAccountRepository dimAccountRepository,
            DimTransactionRepository dimTransactionRepository,
            DimTransactionRequestRepository dimTransactionRequestRepository
    ) {
        this.accountTypeMeasureRepository = accountTypeMeasureRepository;
        this.requestDayMeasureRepository = requestDayMeasureRepository;
        this.transactionDayMeasureRepository = transactionDayMeasureRepository;
        this.dimAccountRepository = dimAccountRepository;
        this.dimTransactionRepository = dimTransactionRepository;
        this.dimTransactionRequestRepository = dimTransactionRequestRepository;
        accountTypeMeasure = new AccountTypeMeasure();
        requestDayMeasure = new RequestDayMeasure();
        transactionDayMeasure = new TransactionDayMeasure();
    }

    @Scheduled(cron = "0 0 */2 * * *") // Every 2 Hours
    public void accountTypeMeasure() {
        accountTypeMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        accountTypeMeasure.setTodayCount(dimAccountRepository.findAllByBeginTimestampAfter(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN)).size());
        accountTypeMeasure.setTodayNormCount(dimAccountRepository.findAllByBeginTimestampAfterAndTypeCode(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN),"NORM").size());
        accountTypeMeasure.setTodayNentCount(dimAccountRepository.findAllByBeginTimestampAfterAndTypeCode(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN),"NENT").size());
        accountTypeMeasure.setTodayPremCount(dimAccountRepository.findAllByBeginTimestampAfterAndTypeCode(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN),"PREM").size());
        accountTypeMeasure.setTodayPentCount(dimAccountRepository.findAllByBeginTimestampAfterAndTypeCode(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN),"PENT").size());
        accountTypeMeasureRepository.save(accountTypeMeasure);
    }

    @Scheduled(cron = "*/17 * * * * *") // Every 17 seconds
    public void instantMeasures() {
        requestDayMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        requestDayMeasure.setTodayReqCount(
                dimTransactionRequestRepository.findAllByTimestampAfter(LocalDateTime.of(LocalDate.now(Clock.systemUTC()),LocalTime.MIN)).size()
        );
        requestDayMeasure.setTodayReqAmount(
                dimTransactionRequestRepository.findAllByTimestampAfter(LocalDateTime.of(LocalDate.now(Clock.systemUTC()),LocalTime.MIN))
                        .stream()
                        .mapToLong(DimTransactionRequest::getAmount)
                        .sum()
        );
        requestDayMeasureRepository.save(requestDayMeasure);

        transactionDayMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        transactionDayMeasure.setTodayNet(
                dimTransactionRepository.findAllByTypeCodeAndTimestampAfter("DEP", LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN))
                        .stream()
                        .mapToLong(DimTransaction::getAmount)
                        .sum() - dimTransactionRepository.findAllByTypeCodeAndTimestampAfter("WIT", LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN))
                        .stream()
                        .mapToLong(DimTransaction::getAmount)
                        .sum()
        );
        transactionDayMeasure.setTodayCount(
                dimTransactionRepository.findAllByTimestampAfter(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN)).size()
        );
        transactionDayMeasure.setTodayAmount(
                dimTransactionRepository.findAllByTimestampAfter(LocalDateTime.of(LocalDate.now(Clock.systemUTC()), LocalTime.MIN))
                .stream()
                .mapToLong(DimTransaction::getAmount)
                .sum()
        );
        transactionDayMeasureRepository.save(transactionDayMeasure);
    }

    @Scheduled(cron = "0 0 0 * * *") // Every Day
    public void dailyMeasure() {
        accountTypeMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        accountTypeMeasure.setAllCount(dimAccountRepository.count());
        accountTypeMeasure.setAllNormCount(dimAccountRepository.findAllByTypeCode("NORM").size());
        accountTypeMeasure.setAllNentCount(dimAccountRepository.findAllByTypeCode("NENT").size());
        accountTypeMeasure.setAllPremCount(dimAccountRepository.findAllByTypeCode("PREM").size());
        accountTypeMeasure.setAllPentCount(dimAccountRepository.findAllByTypeCode("PENT").size());
        accountTypeMeasureRepository.save(accountTypeMeasure);

        transactionDayMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        transactionDayMeasure.setAllCount(dimTransactionRepository.count());
        transactionDayMeasure.setAllAmount(
                ((List<DimTransaction>)dimTransactionRepository.findAll())
                        .stream()
                        .mapToLong(DimTransaction::getAmount)
                        .sum()
        );
        transactionDayMeasure.setAllNet(
                dimTransactionRepository.findAllByTypeCode("DEP")
                        .stream()
                        .mapToLong(DimTransaction::getAmount)
                        .sum() - dimTransactionRepository.findAllByTypeCode("WIT")
                        .stream()
                        .mapToLong(DimTransaction::getAmount)
                        .sum()
        );
        transactionDayMeasureRepository.save(transactionDayMeasure);

        requestDayMeasure.setDay(LocalDate.now(Clock.systemUTC()));
        requestDayMeasure.setAllReqCount(dimTransactionRequestRepository.count());
        requestDayMeasure.setAllReqAmount(((List<DimTransactionRequest>)dimTransactionRequestRepository.findAll())
                .stream()
                .mapToLong(DimTransactionRequest::getAmount)
                .sum()
        );
        requestDayMeasureRepository.save(requestDayMeasure);
    }

}
