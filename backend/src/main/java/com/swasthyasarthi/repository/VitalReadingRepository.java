package com.swasthyasarthi.repository;

import com.swasthyasarthi.model.PatientProfile;
import com.swasthyasarthi.model.VitalReading;
import com.swasthyasarthi.model.VitalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VitalReadingRepository extends JpaRepository<VitalReading, Long> {
    
    List<VitalReading> findByPatientProfileOrderByReadingTimestampDesc(PatientProfile patientProfile);
    
    List<VitalReading> findByPatientProfileAndReadingTimestampBetween(
        PatientProfile patientProfile, LocalDateTime startTime, LocalDateTime endTime);
    
    List<VitalReading> findByPatientProfileAndVitalTypeOrderByReadingTimestampDesc(
        PatientProfile patientProfile, VitalType vitalType);
    
    @Query("SELECT v FROM VitalReading v WHERE v.patientProfile = :profile AND v.vitalType = :type AND v.readingTimestamp BETWEEN :startTime AND :endTime ORDER BY v.readingTimestamp DESC")
    List<VitalReading> findByPatientProfileAndVitalTypeAndReadingTimestampBetween(
        @Param("profile") PatientProfile patientProfile,
        @Param("type") VitalType vitalType,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT v FROM VitalReading v WHERE v.patientProfile = :profile AND v.readingTimestamp >= :since ORDER BY v.readingTimestamp DESC")
    List<VitalReading> findRecentByPatientProfile(
        @Param("profile") PatientProfile patientProfile,
        @Param("since") LocalDateTime since);
}