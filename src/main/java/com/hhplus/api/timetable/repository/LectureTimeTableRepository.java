package com.hhplus.api.timetable.repository;

import com.hhplus.api.timetable.domain.LectureTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureTimeTableRepository extends JpaRepository<LectureTimeTable, Long> {
}
