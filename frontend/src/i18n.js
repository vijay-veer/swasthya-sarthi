import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

// English translations
const en = {
  translation: {
    // Navigation
    dashboard: 'Dashboard',
    profile: 'Profile',
    vitals: 'Vitals',
    encounters: 'Encounters',
    crrs: 'Risk Score',
    achievements: 'Achievements',
    settings: 'Settings',
    logout: 'Logout',
    
    // Authentication
    login: 'Login',
    register: 'Register',
    phoneNumber: 'Phone Number',
    password: 'Password',
    confirmPassword: 'Confirm Password',
    firstName: 'First Name',
    lastName: 'Last Name',
    age: 'Age',
    gender: 'Gender',
    male: 'Male',
    female: 'Female',
    other: 'Other',
    preferNotToSay: 'Prefer not to say',
    
    // Dashboard
    welcomeBack: 'Welcome back',
    todaysVitals: "Today's Vitals",
    recentActivity: 'Recent Activity',
    healthTips: 'Health Tips',
    noDataYet: 'No data logged yet',
    logYourFirstVital: 'Log your first vital reading',
    
    // Vitals
    bloodPressure: 'Blood Pressure',
    glucose: 'Glucose',
    weight: 'Weight',
    heartRate: 'Heart Rate',
    systolic: 'Systolic',
    diastolic: 'Diastolic',
    fasting: 'Fasting',
    postMeal: 'Post-meal',
    random: 'Random',
    logVitals: 'Log Vitals',
    lastReading: 'Last Reading',
    targetRange: 'Target Range',
    inRange: 'In Range',
    outOfRange: 'Out of Range',
    
    // CRRS
    cardioRenalRiskScore: 'Cardio-Renal Risk Score',
    riskLevel: 'Risk Level',
    lowRisk: 'Low Risk',
    moderateRisk: 'Moderate Risk',
    highRisk: 'High Risk',
    criticalRisk: 'Critical Risk',
    improving: 'Improving',
    worsening: 'Worsening',
    stable: 'Stable',
    
    // Common
    save: 'Save',
    cancel: 'Cancel',
    edit: 'Edit',
    delete: 'Delete',
    loading: 'Loading...',
    error: 'Error',
    success: 'Success',
    required: 'Required',
    optional: 'Optional',
    today: 'Today',
    yesterday: 'Yesterday',
    thisWeek: 'This Week',
    thisMonth: 'This Month',
    
    // Messages
    loginSuccess: 'Login successful',
    loginError: 'Login failed',
    registerSuccess: 'Registration successful',
    registerError: 'Registration failed',
    saveSuccess: 'Saved successfully',
    saveError: 'Failed to save',
    deleteSuccess: 'Deleted successfully',
    deleteError: 'Failed to delete'
  }
};

// Hindi translations
const hi = {
  translation: {
    // Navigation
    dashboard: 'डैशबोर्ड',
    profile: 'प्रोफाइल',
    vitals: 'जीवन संकेत',
    encounters: 'मुलाकातें',
    crrs: 'जोखिम स्कोर',
    achievements: 'उपलब्धियां',
    settings: 'सेटिंग्स',
    logout: 'लॉग आउट',
    
    // Authentication
    login: 'लॉग इन',
    register: 'रजिस्टर',
    phoneNumber: 'फोन नंबर',
    password: 'पासवर्ड',
    confirmPassword: 'पासवर्ड की पुष्टि करें',
    firstName: 'पहला नाम',
    lastName: 'अंतिम नाम',
    age: 'आयु',
    gender: 'लिंग',
    male: 'पुरुष',
    female: 'महिला',
    other: 'अन्य',
    preferNotToSay: 'कहना नहीं चाहते',
    
    // Dashboard
    welcomeBack: 'वापस स्वागत है',
    todaysVitals: 'आज के जीवन संकेत',
    recentActivity: 'हाल की गतिविधि',
    healthTips: 'स्वास्थ्य सुझाव',
    noDataYet: 'अभी तक कोई डेटा नहीं',
    logYourFirstVital: 'अपना पहला जीवन संकेत दर्ज करें',
    
    // Vitals
    bloodPressure: 'रक्तचाप',
    glucose: 'ग्लूकोज',
    weight: 'वजन',
    heartRate: 'हृदय गति',
    systolic: 'सिस्टोलिक',
    diastolic: 'डायस्टोलिक',
    fasting: 'उपवास',
    postMeal: 'भोजन के बाद',
    random: 'यादृच्छिक',
    logVitals: 'जीवन संकेत दर्ज करें',
    lastReading: 'अंतिम रीडिंग',
    targetRange: 'लक्ष्य सीमा',
    inRange: 'सीमा में',
    outOfRange: 'सीमा से बाहर',
    
    // CRRS
    cardioRenalRiskScore: 'हृदय-गुर्दा जोखिम स्कोर',
    riskLevel: 'जोखिम स्तर',
    lowRisk: 'कम जोखिम',
    moderateRisk: 'मध्यम जोखिम',
    highRisk: 'उच्च जोखिम',
    criticalRisk: 'गंभीर जोखिम',
    improving: 'सुधार',
    worsening: 'बिगड़ना',
    stable: 'स्थिर',
    
    // Common
    save: 'सहेजें',
    cancel: 'रद्द करें',
    edit: 'संपादित करें',
    delete: 'हटाएं',
    loading: 'लोड हो रहा है...',
    error: 'त्रुटि',
    success: 'सफलता',
    required: 'आवश्यक',
    optional: 'वैकल्पिक',
    today: 'आज',
    yesterday: 'कल',
    thisWeek: 'इस सप्ताह',
    thisMonth: 'इस महीने',
    
    // Messages
    loginSuccess: 'लॉग इन सफल',
    loginError: 'लॉग इन असफल',
    registerSuccess: 'रजिस्ट्रेशन सफल',
    registerError: 'रजिस्ट्रेशन असफल',
    saveSuccess: 'सफलतापूर्वक सहेजा गया',
    saveError: 'सहेजने में असफल',
    deleteSuccess: 'सफलतापूर्वक हटाया गया',
    deleteError: 'हटाने में असफल'
  }
};

i18n
  .use(initReactI18next)
  .init({
    resources: {
      en,
      hi
    },
    lng: 'en',
    fallbackLng: 'en',
    interpolation: {
      escapeValue: false
    }
  });

export default i18n;