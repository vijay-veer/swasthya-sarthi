package com.swasthyasarthi.repository;

import com.swasthyasarthi.model.Encounter;
import com.swasthyasarthi.model.EncounterType;
import com.swasthyasarthi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    
    List<Encounter> findByUserOrderByEncounterTimestampDesc(User user);
    
    List<Encounter> findByUserAndEncounterTimestampBetween(
        User user, LocalDateTime startTime, LocalDateTime endTime);
    
    List<Encounter> findByUserAndEncounterTypeOrderByEncounterTimestampDesc(
        User user, EncounterType encounterType);
    
    @Query("SELECT e FROM Encounter e WHERE e.user = :user AND e.encounterTimestamp >= :since ORDER BY e.encounterTimestamp DESC")
    List<Encounter> findRecentByUser(
        @Param("user") User user,
        @Param("since") LocalDateTime since);
    
    @Query("SELECT e FROM Encounter e WHERE e.user = :user AND e.medicationMissed = true AND e.encounterTimestamp >= :since ORDER BY e.encounterTimestamp DESC")
    List<Encounter> findMissedMedicationsByUser(
        @Param("user") User user,
        @Param("since") LocalDateTime since);
}