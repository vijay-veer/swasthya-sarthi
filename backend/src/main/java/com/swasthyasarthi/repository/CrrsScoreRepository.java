package com.swasthyasarthi.repository;

import com.swasthyasarthi.model.CrrsScore;
import com.swasthyasarthi.model.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrrsScoreRepository extends JpaRepository<CrrsScore, Long> {
    
    List<CrrsScore> findByPatientProfileOrderByScoreDateDesc(PatientProfile patientProfile);
    
    Optional<CrrsScore> findTopByPatientProfileAndScoreDateBeforeOrderByScoreDateDesc(
        PatientProfile patientProfile, LocalDate scoreDate);
    
    @Query("SELECT c FROM CrrsScore c WHERE c.patientProfile = :profile AND c.scoreDate BETWEEN :startDate AND :endDate ORDER BY c.scoreDate DESC")
    List<CrrsScore> findByPatientProfileAndScoreDateBetween(
        @Param("profile") PatientProfile patientProfile,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    Optional<CrrsScore> findByPatientProfileAndScoreDate(PatientProfile patientProfile, LocalDate scoreDate);
}