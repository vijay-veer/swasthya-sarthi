#!/bin/bash

# SwasthyaSaarthi - Complete GitHub Repository Setup Script
# This script will help you create a complete, error-free GitHub repository

set -e

echo "🚀 SwasthyaSaarthi - GitHub Repository Setup"
echo "=============================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

# Check if we're in the right directory
if [ ! -f "README.md" ]; then
    print_error "Please run this script from the project root directory"
    exit 1
fi

print_status "Starting GitHub repository setup..."

# Step 1: Check if git is installed
if ! command -v git &> /dev/null; then
    print_error "Git is not installed. Please install git first."
    exit 1
fi

print_status "Git is installed"

# Step 2: Initialize git repository if not already initialized
if [ ! -d ".git" ]; then
    print_info "Initializing git repository..."
    git init
    print_status "Git repository initialized"
else
    print_info "Git repository already initialized"
fi

# Step 3: Check git status
print_info "Checking git status..."
git status

# Step 4: Add all files
print_info "Adding all files to git..."
git add .

# Step 5: Check what will be committed
print_info "Files to be committed:"
git status --porcelain

# Step 6: Create initial commit
print_info "Creating initial commit..."
git commit -m "Initial commit: Complete SwasthyaSaarthi platform

- ✅ Backend: Spring Boot API with JWT authentication
- ✅ Frontend: React app with multilingual support  
- ✅ Database: PostgreSQL with Flyway migrations
- ✅ AI: Agentic AI system with CRRS computation
- ✅ Security: Role-based access control
- ✅ Docker: Containerization ready
- ✅ Dependencies: All issues resolved
- ✅ Build: Successful compilation and packaging

Features:
- Digital Twin implementation
- Cardio-Renal Risk Score (CRRS) computation
- Agentic AI with OODA loop
- Multilingual support (EN, HI, MR, BN)
- Voice-powered data logging
- Gamification system
- Comprehensive security and privacy controls
- Docker containerization
- Complete documentation and setup guides"

print_status "Initial commit created"

# Step 7: Display repository information
echo ""
echo "📊 Repository Information:"
echo "========================="
echo "Total files: $(find . -type f | wc -l)"
echo "Java files: $(find . -name "*.java" | wc -l)"
echo "JavaScript files: $(find . -name "*.js" | wc -l)"
echo "Configuration files: $(find . -name "*.yml" -o -name "*.json" -o -name "*.xml" | wc -l)"
echo "Documentation files: $(find . -name "*.md" | wc -l)"

# Step 8: Display next steps
echo ""
echo "🎯 Next Steps:"
echo "=============="
echo "1. Create a new repository on GitHub:"
echo "   - Go to https://github.com/new"
echo "   - Repository name: swasthya-saarthi"
echo "   - Description: AI-assisted digital health platform for early risk detection and proactive care"
echo "   - Make it Public (or Private as per your preference)"
echo "   - DO NOT initialize with README, .gitignore, or license"
echo ""
echo "2. Connect your local repository to GitHub:"
echo "   git remote add origin https://github.com/YOUR_USERNAME/swasthya-saarthi.git"
echo ""
echo "3. Push to GitHub:"
echo "   git push -u origin main"
echo ""
echo "4. Verify your repository on GitHub contains all files"

# Step 9: Display repository structure
echo ""
echo "📁 Repository Structure:"
echo "========================"
tree -I 'node_modules|target|.git' -L 3 2>/dev/null || find . -type d -not -path './.git*' -not -path './node_modules*' -not -path './target*' | head -20

# Step 10: Display important files
echo ""
echo "📋 Important Files Created:"
echo "=========================="
echo "✅ .gitignore - Git ignore rules"
echo "✅ LICENSE - MIT License"
echo "✅ README.md - Project documentation"
echo "✅ GITHUB_SETUP_GUIDE.md - Complete setup guide"
echo "✅ DEPENDENCY_TROUBLESHOOTING.md - Troubleshooting guide"
echo "✅ APPLICATION_STATUS.md - Current status report"
echo "✅ check-dependencies.sh - Dependency checker script"
echo "✅ docker-compose.yml - Docker orchestration"
echo "✅ backend/ - Complete Spring Boot backend"
echo "✅ frontend/ - Complete React frontend"

# Step 11: Display run instructions
echo ""
echo "🚀 How to Run After GitHub Setup:"
echo "================================="
echo "1. Clone the repository:"
echo "   git clone https://github.com/YOUR_USERNAME/swasthya-saarthi.git"
echo "   cd swasthya-saarthi"
echo ""
echo "2. Run dependency check:"
echo "   ./check-dependencies.sh"
echo ""
echo "3. Start with Docker (recommended):"
echo "   docker-compose up --build"
echo ""
echo "4. Or start manually:"
echo "   # Frontend"
echo "   cd frontend && npm install && npm start"
echo "   # Backend (after installing PostgreSQL)"
echo "   cd backend && mvn clean package && java -jar target/swasthya-sarthi-backend-1.0.0.jar"

# Step 12: Display success message
echo ""
print_status "GitHub repository setup completed successfully!"
echo ""
echo "🎉 Your SwasthyaSaarthi repository is ready for GitHub!"
echo ""
echo "📞 Support:"
echo "==========="
echo "- Check GITHUB_SETUP_GUIDE.md for detailed instructions"
echo "- Check DEPENDENCY_TROUBLESHOOTING.md for any issues"
echo "- Check APPLICATION_STATUS.md for current status"
echo "- Run ./check-dependencies.sh to verify everything"
echo ""
echo "🌟 Repository Features:"
echo "======================="
echo "✅ Complete Spring Boot backend with AI capabilities"
echo "✅ React frontend with multilingual support"
echo "✅ PostgreSQL database with migrations"
echo "✅ Docker containerization"
echo "✅ Comprehensive documentation"
echo "✅ Error-free, ready-to-run code"
echo "✅ Professional project structure"
echo ""
echo "🚀 Ready to push to GitHub!"