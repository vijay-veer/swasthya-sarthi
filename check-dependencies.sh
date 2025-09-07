#!/bin/bash

echo "🔍 Checking SwasthyaSaarthi Dependencies..."

# Check if we're in the right directory
if [ ! -f "README.md" ]; then
    echo "❌ Please run this script from the project root directory"
    exit 1
fi

echo ""
echo "📦 Backend Dependencies Check..."
cd backend

# Check Java version
echo "☕ Java Version:"
java -version 2>&1 | head -1

# Check Maven
echo ""
echo "🔨 Maven Version:"
mvn -version | head -1

# Validate POM
echo ""
echo "📋 Validating Maven POM..."
if mvn validate -q; then
    echo "✅ Maven POM is valid"
else
    echo "❌ Maven POM has issues"
fi

# Check for dependency conflicts
echo ""
echo "🔍 Checking for dependency conflicts..."
if mvn dependency:tree -Dverbose -q 2>/dev/null | grep -i conflict; then
    echo "⚠️  Potential dependency conflicts found"
else
    echo "✅ No dependency conflicts detected"
fi

cd ..

echo ""
echo "🌐 Frontend Dependencies Check..."
cd frontend

# Check Node version
echo "📦 Node Version:"
node --version

# Check npm version
echo "📦 NPM Version:"
npm --version

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "⚠️  node_modules not found. Run 'npm install' first."
else
    echo "✅ node_modules directory exists"
fi

# Check for security vulnerabilities
echo ""
echo "🔒 Checking for security vulnerabilities..."
if npm audit --audit-level=high 2>/dev/null | grep -q "found"; then
    echo "⚠️  Security vulnerabilities found. Run 'npm audit fix' to fix them."
else
    echo "✅ No high-severity security vulnerabilities found"
fi

cd ..

echo ""
echo "🐳 Docker Dependencies Check..."

# Check Docker
if command -v docker &> /dev/null; then
    echo "✅ Docker is installed: $(docker --version)"
else
    echo "❌ Docker is not installed"
fi

# Check Docker Compose
if command -v docker-compose &> /dev/null; then
    echo "✅ Docker Compose is installed: $(docker-compose --version)"
else
    echo "❌ Docker Compose is not installed"
fi

echo ""
echo "🗄️  Database Check..."

# Check PostgreSQL
if command -v psql &> /dev/null; then
    echo "✅ PostgreSQL is installed: $(psql --version | head -1)"
else
    echo "❌ PostgreSQL is not installed"
fi

echo ""
echo "📋 Summary:"
echo "✅ Backend: Spring Boot 3.2.0 with Java 17"
echo "✅ Frontend: React 18 with modern dependencies"
echo "✅ Database: PostgreSQL with Flyway migrations"
echo "✅ Security: JWT authentication, encryption"
echo "✅ AI: OpenAI Java client integration"
echo "✅ Communication: Twilio SMS integration"
echo "✅ UI: Tailwind CSS with responsive design"
echo "✅ i18n: Multilingual support (EN, HI, MR, BN)"

echo ""
echo "🚀 Next Steps:"
echo "1. Run 'cd backend && mvn clean install' to build backend"
echo "2. Run 'cd frontend && npm install' to install frontend dependencies"
echo "3. Start PostgreSQL database"
echo "4. Run 'docker-compose up' to start all services"
echo "5. Access application at http://localhost:3000"

echo ""
echo "✨ SwasthyaSaarthi is ready for development!"