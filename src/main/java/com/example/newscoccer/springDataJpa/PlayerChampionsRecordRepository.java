package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.PlayerChampionsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerChampionsRecordRepository extends JpaRepository<PlayerChampionsRecord,Long> {
}
