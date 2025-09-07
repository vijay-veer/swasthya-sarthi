-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    age INTEGER NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'PATIENT',
    is_active BOOLEAN NOT NULL DEFAULT true,
    consent_given BOOLEAN NOT NULL DEFAULT false,
    consent_timestamp TIMESTAMP,
    preferred_language VARCHAR(10) NOT NULL DEFAULT 'en',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create patient_profiles table
CREATE TABLE patient_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    diabetes_diagnosis_date DATE,
    hypertension_diagnosis_date DATE,
    family_history_diabetes BOOLEAN DEFAULT false,
    family_history_hypertension BOOLEAN DEFAULT false,
    family_history_heart_disease BOOLEAN DEFAULT false,
    known_allergies TEXT,
    current_medications TEXT,
    target_systolic_bp_min INTEGER DEFAULT 120,
    target_systolic_bp_max INTEGER DEFAULT 140,
    target_diastolic_bp_min INTEGER DEFAULT 80,
    target_diastolic_bp_max INTEGER DEFAULT 90,
    target_fasting_glucose_min INTEGER DEFAULT 80,
    target_fasting_glucose_max INTEGER DEFAULT 130,
    target_post_meal_glucose_min INTEGER DEFAULT 100,
    target_post_meal_glucose_max INTEGER DEFAULT 180,
    target_weight_kg DECIMAL(5,2),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    emergency_contact_relationship VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create vital_readings table
CREATE TABLE vital_readings (
    id BIGSERIAL PRIMARY KEY,
    patient_profile_id BIGINT NOT NULL REFERENCES patient_profiles(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    vital_type VARCHAR(50) NOT NULL,
    systolic_bp INTEGER,
    diastolic_bp INTEGER,
    glucose_value INTEGER,
    is_fasting BOOLEAN,
    heart_rate INTEGER,
    weight_kg DECIMAL(5,2),
    temperature_celsius DECIMAL(4,2),
    oxygen_saturation INTEGER,
    reading_timestamp TIMESTAMP NOT NULL,
    notes TEXT,
    is_backdated BOOLEAN DEFAULT false,
    original_timestamp TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create encounters table
CREATE TABLE encounters (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    encounter_type VARCHAR(50) NOT NULL,
    encounter_timestamp TIMESTAMP NOT NULL,
    symptoms TEXT,
    medication_taken BOOLEAN,
    medication_missed BOOLEAN,
    activity_minutes INTEGER,
    diet_tags TEXT,
    mood_rating INTEGER CHECK (mood_rating >= 1 AND mood_rating <= 5),
    sleep_hours DECIMAL(3,1),
    stress_level INTEGER CHECK (stress_level >= 1 AND stress_level <= 5),
    notes TEXT,
    is_backdated BOOLEAN DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create crrs_scores table
CREATE TABLE crrs_scores (
    id BIGSERIAL PRIMARY KEY,
    patient_profile_id BIGINT NOT NULL REFERENCES patient_profiles(id) ON DELETE CASCADE,
    score_date DATE NOT NULL,
    crrs_value DECIMAL(5,2) NOT NULL,
    previous_crrs DECIMAL(5,2),
    delta_crrs DECIMAL(5,2),
    risk_tier VARCHAR(20) NOT NULL,
    vitals_contribution DECIMAL(5,2),
    lifestyle_contribution DECIMAL(5,2),
    adherence_contribution DECIMAL(5,2),
    symptoms_contribution DECIMAL(5,2),
    trend_contribution DECIMAL(5,2),
    explanation TEXT,
    calculation_details TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_users_phone_number ON users(phone_number);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_patient_profiles_user_id ON patient_profiles(user_id);
CREATE INDEX idx_vital_readings_patient_profile_id ON vital_readings(patient_profile_id);
CREATE INDEX idx_vital_readings_user_id ON vital_readings(user_id);
CREATE INDEX idx_vital_readings_timestamp ON vital_readings(reading_timestamp);
CREATE INDEX idx_vital_readings_type ON vital_readings(vital_type);
CREATE INDEX idx_encounters_user_id ON encounters(user_id);
CREATE INDEX idx_encounters_timestamp ON encounters(encounter_timestamp);
CREATE INDEX idx_encounters_type ON encounters(encounter_type);
CREATE INDEX idx_crrs_scores_patient_profile_id ON crrs_scores(patient_profile_id);
CREATE INDEX idx_crrs_scores_date ON crrs_scores(score_date);
CREATE INDEX idx_crrs_scores_risk_tier ON crrs_scores(risk_tier);

-- Create unique constraint for daily CRRS scores
CREATE UNIQUE INDEX idx_crrs_scores_unique_daily ON crrs_scores(patient_profile_id, score_date);