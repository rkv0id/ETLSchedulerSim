package com.tnbank.microetl.repositories;

import com.tnbank.microetl.entities.DimAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DimAccountRepository extends CrudRepository<DimAccount, String> {
    List<DimAccount> findAllByBeginTimestampAfter(@Param("beginTimestamp") LocalDateTime beginTimestamp);
    List<DimAccount> findAllByBeginTimestampAfterAndTypeCode(@Param("beginTimestamp") LocalDateTime beginTimestamp, @Param("typeCode") String typeCode);
    List<DimAccount> findAllByTypeCode(@Param("typeCode") String typeCode);
}
