# SwasthyaSaarthi - Dependency Troubleshooting Guide

## 🔧 Common Dependency Issues & Solutions

### Backend (Spring Boot) Issues

#### 1. **Spring AI Dependency Issues**
**Problem**: Spring AI is still in milestone releases and may cause compatibility issues.

**Solution**: ✅ **FIXED** - Replaced with stable OpenAI Java client:
```xml
<dependency>
    <groupId>com.theokanning.openai-gpt3-java</groupId>
    <artifactId>service</artifactId>
    <version>0.18.2</version>
</dependency>
```

#### 2. **JWT Dependency Issues**
**Problem**: Missing closing tag in pom.xml

**Solution**: ✅ **FIXED** - Corrected XML syntax:
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
```

#### 3. **Missing Dependencies**
**Problem**: Common Spring Boot dependencies missing

**Solution**: ✅ **ADDED**:
- `spring-boot-starter-mail` - For email notifications
- `spring-boot-starter-cache` - For caching
- `lombok` - For reducing boilerplate code

### Frontend (React) Issues

#### 1. **Outdated Dependencies**
**Problem**: Some packages have newer stable versions

**Solution**: ✅ **UPDATED** to latest stable versions:
- `@testing-library/react`: ^13.4.0
- `react-router-dom`: ^6.20.1
- `axios`: ^1.6.2
- `recharts`: ^2.8.0
- `react-hook-form`: ^7.48.2
- `react-i18next`: ^13.5.0
- `i18next`: ^23.7.6

#### 2. **Missing Development Dependencies**
**Problem**: TypeScript types and ESLint plugins missing

**Solution**: ✅ **ADDED**:
- `@types/react` and `@types/react-dom`
- `eslint-plugin-react` and `eslint-plugin-react-hooks`

#### 3. **Additional State Management**
**Problem**: Need better state management options

**Solution**: ✅ **ADDED**:
- `react-query` - For server state management
- `zustand` - For client state management

## 🚀 Quick Fix Commands

### Backend Setup
```bash
cd backend

# Clean and rebuild
mvn clean install

# Check for dependency conflicts
mvn dependency:tree -Dverbose

# Run tests
mvn test
```

### Frontend Setup
```bash
cd frontend

# Install dependencies
npm install

# Check for security issues
npm audit

# Fix security issues
npm audit fix

# Start development server
npm start
```

### Full Stack Setup
```bash
# From project root
./check-dependencies.sh

# Start with Docker
docker-compose up --build
```

## 🔍 Dependency Validation

### Backend Validation
1. **Java Version**: Ensure Java 17+ is installed
2. **Maven**: Check Maven 3.6+ is available
3. **Database**: PostgreSQL 13+ should be running
4. **Environment Variables**: Set required API keys

### Frontend Validation
1. **Node.js**: Ensure Node.js 16+ is installed
2. **NPM**: Check NPM 8+ is available
3. **Build**: Verify `npm run build` works
4. **Tests**: Run `npm test` to check functionality

## 🛠️ Environment Setup

### Required Environment Variables
```bash
# Backend (.env or system environment)
export TWILIO_ACCOUNT_SID=your_twilio_sid
export TWILIO_AUTH_TOKEN=your_twilio_token
export TWILIO_PHONE_NUMBER=your_twilio_number
export OPENAI_API_KEY=your_openai_key

# Database
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/swasthyasarthi
export SPRING_DATASOURCE_USERNAME=swasthyasarthi
export SPRING_DATASOURCE_PASSWORD=swasthyasarthi123
```

### Database Setup
```sql
-- Create database
CREATE DATABASE swasthyasarthi;

-- Create user
CREATE USER swasthyasarthi WITH PASSWORD 'swasthyasarthi123';

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE swasthyasarthi TO swasthyasarthi;
```

## 🐛 Troubleshooting Common Errors

### 1. **Maven Build Failures**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Rebuild with clean install
mvn clean install -U
```

### 2. **NPM Install Failures**
```bash
# Clear NPM cache
npm cache clean --force

# Delete node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

### 3. **Database Connection Issues**
```bash
# Check PostgreSQL status
sudo systemctl status postgresql

# Start PostgreSQL
sudo systemctl start postgresql

# Test connection
psql -h localhost -U swasthyasarthi -d swasthyasarthi
```

### 4. **Port Conflicts**
```bash
# Check if ports are in use
lsof -i :8080  # Backend
lsof -i :3000  # Frontend
lsof -i :5432  # PostgreSQL

# Kill processes if needed
kill -9 <PID>
```

## 📊 Dependency Health Check

Run the dependency check script:
```bash
./check-dependencies.sh
```

This script will:
- ✅ Validate Java and Maven versions
- ✅ Check Maven POM validity
- ✅ Detect dependency conflicts
- ✅ Verify Node.js and NPM versions
- ✅ Check for security vulnerabilities
- ✅ Validate Docker installation
- ✅ Test PostgreSQL connectivity

## 🔄 Continuous Integration

### GitHub Actions (Optional)
```yaml
name: Dependency Check
on: [push, pull_request]
jobs:
  backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - run: cd backend && mvn clean install
      
  frontend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
        with:
          node-version: '18'
      - run: cd frontend && npm ci
      - run: cd frontend && npm run build
```

## 📈 Performance Optimization

### Backend Optimizations
- **Connection Pooling**: Configure HikariCP for database connections
- **Caching**: Enable Spring Cache for frequently accessed data
- **Compression**: Enable GZIP compression for API responses

### Frontend Optimizations
- **Code Splitting**: Implement lazy loading for routes
- **Bundle Analysis**: Use `npm run build` and analyze bundle size
- **Image Optimization**: Compress and optimize images
- **CDN**: Use CDN for static assets

## 🎯 Next Steps

1. **Run Dependency Check**: `./check-dependencies.sh`
2. **Install Dependencies**: Follow the setup commands above
3. **Start Development**: Use `docker-compose up` for full stack
4. **Test Functionality**: Verify all features work correctly
5. **Monitor Performance**: Use provided monitoring tools

## 📞 Support

If you encounter issues not covered in this guide:
1. Check the logs for specific error messages
2. Verify all environment variables are set
3. Ensure all required services are running
4. Check network connectivity and firewall settings
5. Review the main README.md for additional setup instructions

---

**All dependency issues have been resolved!** 🎉
The project is now ready for development and deployment.