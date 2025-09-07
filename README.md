# SwasthyaSaarthi - AI-Assisted Digital Health Platform

SwasthyaSaarthi is a comprehensive, AI-assisted digital health platform that creates a practical "digital twin" for each patient to enable early risk detection and proactive care in low-resource settings. The platform combines advanced AI with user-friendly interfaces to provide personalized health monitoring and intervention.

## 🌟 Key Features

### Core Functionality
- **Digital Twin Creation**: Comprehensive patient health modeling with real-time data integration
- **Cardio-Renal Risk Score (CRRS)**: AI-powered risk assessment scoring system (0-100)
- **Agentic AI System**: Autonomous health monitoring with OODA loop implementation
- **Multilingual Support**: English, Hindi, Marathi, and Bengali language support
- **Voice-Powered Logging**: Natural language processing for hands-free data entry
- **Offline-First Design**: Robust data collection with background synchronization

### Health Monitoring
- **Vital Signs Tracking**: Blood pressure, glucose, weight, heart rate monitoring
- **Symptom Reporting**: Comprehensive symptom logging and analysis
- **Medication Adherence**: Smart tracking and reminder systems
- **Lifestyle Monitoring**: Activity, diet, sleep, and stress tracking
- **Trend Analysis**: Advanced pattern recognition and anomaly detection

### AI-Powered Features
- **Anomaly Detection**: Real-time identification of concerning health patterns
- **Predictive Analytics**: Early warning system for health deterioration
- **Personalized Nudges**: Context-aware health recommendations
- **Risk Stratification**: Automated risk tier classification (Low/Moderate/High/Critical)
- **Smart Alerts**: Intelligent notification system with escalation protocols

### Gamification & Engagement
- **Points System**: Reward-based health behavior reinforcement
- **Streaks & Badges**: Achievement system for consistent health practices
- **Health Quests**: Personalized weekly challenges and goals
- **Progress Tracking**: Visual health journey representation
- **Social Features**: Family circle sharing and support

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0 with Java 17
- **Database**: PostgreSQL with Flyway migrations
- **Security**: JWT-based authentication with role-based access control
- **AI Integration**: Spring AI framework for intelligent health analysis
- **Communication**: Twilio SMS integration for critical alerts
- **Architecture**: Microservices-ready with clean separation of concerns

### Frontend (React)
- **Framework**: React 18 with modern hooks and context
- **Styling**: Tailwind CSS with custom health-focused design system
- **Internationalization**: React i18next for multilingual support
- **State Management**: Context API with custom hooks
- **Charts**: Recharts for health data visualization
- **Forms**: React Hook Form with validation

### Database Schema
- **Users**: Patient and caregiver management
- **Patient Profiles**: Comprehensive health baseline data
- **Vital Readings**: Time-series health measurements
- **Encounters**: Daily logs, symptoms, and lifestyle data
- **CRRS Scores**: Risk assessment history and trends
- **Audit Logs**: Security and compliance tracking

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- PostgreSQL 13 or higher
- Maven 3.6 or higher

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd swasthya-sarthi/backend
   ```

2. **Configure database**
   ```bash
   # Create PostgreSQL database
   createdb swasthyasarthi
   
   # Update application.yml with your database credentials
   ```

3. **Install dependencies and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Configure environment variables**
   ```bash
   export TWILIO_ACCOUNT_SID=your_twilio_sid
   export TWILIO_AUTH_TOKEN=your_twilio_token
   export TWILIO_PHONE_NUMBER=your_twilio_number
   export OPENAI_API_KEY=your_openai_key
   ```

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd ../frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm start
   ```

4. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api

## 📊 CRRS Algorithm

The Cardio-Renal Risk Score is computed using a sophisticated rule-based algorithm:

```
CRRS_today = CRRS_yesterday + ΔV + ΔL + ΔA + ΔS + ΔT
```

Where:
- **ΔV (Vitals)**: Impact of current vital readings vs. target ranges
- **ΔL (Lifestyle)**: Activity, diet, sleep, and stress contributions
- **ΔA (Adherence)**: Medication compliance and logging consistency
- **ΔS (Symptoms)**: Reported symptom severity and frequency
- **ΔT (Trends)**: 7-day moving averages and pattern analysis

### Risk Tiers
- **Low (0-25)**: Continue healthy habits
- **Moderate (25-50)**: Focus on lifestyle improvements
- **High (50-75)**: Consult healthcare provider
- **Critical (75-100)**: Immediate medical attention required

## 🤖 AI Agent System

The agentic AI implements the OODA (Observe-Orient-Decide-Act) loop:

### Observe
- Continuous monitoring of digital twin data
- Real-time vital sign analysis
- Lifestyle pattern recognition
- Symptom trend detection

### Orient
- Clinical guideline correlation (RSSDI standards)
- Contextual health assessment
- Risk factor analysis
- Patient profile integration

### Decide
- Severity classification
- Intervention prioritization
- Alert escalation logic
- Personalized action planning

### Act
- Automated notifications
- Caregiver alerts
- Health recommendations
- Quest generation

## 🔒 Security & Privacy

### Data Protection
- **Encryption**: AES-256 at rest, TLS 1.3 in transit
- **Anonymization**: Pseudonymized data for AI training
- **Access Control**: Role-based permissions with audit trails
- **Compliance**: DPDPA 2023 and HIPAA-aligned security

### Privacy Features
- **Consent Management**: Explicit user consent for data processing
- **Data Segregation**: PII separated from health data
- **Audit Logging**: Immutable security event tracking
- **Data Export**: User-controlled data portability

## 🌐 Multilingual Support

### Supported Languages
- **English**: Primary interface language
- **Hindi**: हिन्दी - Native Indian language support
- **Marathi**: मराठी - Regional language support
- **Bengali**: বাংলা - Regional language support

### Voice Features
- **Natural Language Processing**: Speech-to-text conversion
- **Intent Recognition**: Health metric extraction from voice
- **Multi-language Voice**: Support for regional languages
- **Confirmation Workflow**: Voice-to-text validation

## 📱 User Experience

### Patient Interface
- **Dashboard**: Real-time health overview with CRRS display
- **Vital Logging**: Intuitive forms with validation
- **Progress Tracking**: Visual health journey representation
- **Achievement System**: Gamified health engagement

### Caregiver Interface
- **Patient Monitoring**: View-only access to patient data
- **Alert Management**: Critical health event notifications
- **Progress Reports**: Health trend summaries
- **Communication**: Direct patient contact capabilities

## 🔧 API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Token refresh

### Health Data Endpoints
- `GET /api/vitals/latest` - Latest vital readings
- `POST /api/vitals` - Log new vital reading
- `GET /api/encounters/recent` - Recent encounters
- `POST /api/encounters` - Log new encounter

### AI & Analytics Endpoints
- `GET /api/crrs/latest` - Current CRRS score
- `POST /api/crrs/compute` - Trigger CRRS computation
- `GET /api/analytics/trends` - Health trend analysis

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm test
```

### Integration Testing
- API endpoint validation
- Database migration testing
- Authentication flow testing
- AI agent functionality testing

## 📈 Performance Metrics

### Key Performance Indicators
- **User Engagement**: Daily active users, session duration
- **Health Outcomes**: CRRS improvement rates, alert response times
- **System Performance**: API response times, database query optimization
- **AI Accuracy**: Anomaly detection precision, risk prediction accuracy

### Monitoring
- **Application Metrics**: Spring Boot Actuator endpoints
- **Database Performance**: Query execution monitoring
- **AI Model Performance**: Prediction accuracy tracking
- **User Experience**: Frontend performance metrics

## 🚀 Deployment

### Production Deployment
1. **Database Setup**: PostgreSQL cluster configuration
2. **Backend Deployment**: Spring Boot application with Docker
3. **Frontend Deployment**: React build with CDN distribution
4. **Monitoring**: Application and infrastructure monitoring
5. **Security**: SSL certificates and security headers

### Environment Configuration
- **Development**: Local development with hot reload
- **Staging**: Pre-production testing environment
- **Production**: High-availability production deployment

## 🤝 Contributing

### Development Guidelines
1. Follow Spring Boot best practices
2. Implement comprehensive test coverage
3. Use semantic commit messages
4. Maintain API documentation
5. Follow security best practices

### Code Standards
- **Java**: Google Java Style Guide
- **JavaScript**: ESLint with React rules
- **Database**: Consistent naming conventions
- **API**: RESTful design principles

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

### Documentation
- API documentation available at `/api/docs`
- User guide available in multiple languages
- Developer documentation in `/docs` folder

### Contact
- **Technical Support**: support@swasthyasarthi.com
- **Health Questions**: health@swasthyasarthi.com
- **General Inquiries**: info@swasthyasarthi.com

## 🔮 Future Roadmap

### Phase 2 Features
- **Machine Learning Models**: Advanced predictive analytics
- **Wearable Integration**: Smart device data synchronization
- **Telemedicine**: Video consultation integration
- **Advanced Analytics**: Population health insights

### Phase 3 Features
- **Blockchain Integration**: Immutable health records
- **IoT Integration**: Smart home health monitoring
- **Advanced AI**: Deep learning health predictions
- **Global Expansion**: Multi-country deployment

---

**SwasthyaSaarthi** - Empowering health through intelligent technology and compassionate care.