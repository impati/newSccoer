package com.example.newscoccer.springDataJpa;

import com.example.newscoccer.domain.record.TeamChampionsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamChampionsRecordRepository extends JpaRepository<TeamChampionsRecord,Long> {
}
