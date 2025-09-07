import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../contexts/AuthContext';
import { 
  Heart, 
  Activity, 
  Scale, 
  TrendingUp, 
  AlertTriangle, 
  CheckCircle,
  Plus,
  Calendar,
  Target
} from 'lucide-react';
import axios from 'axios';

const DashboardPage = () => {
  const { t } = useTranslation();
  const { user } = useAuth();
  const [dashboardData, setDashboardData] = useState({
    latestVitals: null,
    crrsScore: null,
    recentEncounters: [],
    healthTips: [],
    loading: true
  });

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [vitalsResponse, crrsResponse, encountersResponse] = await Promise.all([
        axios.get('/api/vitals/latest'),
        axios.get('/api/crrs/latest'),
        axios.get('/api/encounters/recent')
      ]);

      setDashboardData({
        latestVitals: vitalsResponse.data,
        crrsScore: crrsResponse.data,
        recentEncounters: encountersResponse.data,
        healthTips: generateHealthTips(),
        loading: false
      });
    } catch (error) {
      console.error('Failed to fetch dashboard data:', error);
      setDashboardData(prev => ({ ...prev, loading: false }));
    }
  };

  const generateHealthTips = () => {
    return [
      {
        id: 1,
        title: 'Stay Hydrated',
        description: 'Drink at least 8 glasses of water daily',
        icon: '💧'
      },
      {
        id: 2,
        title: 'Regular Exercise',
        description: 'Aim for 30 minutes of moderate activity daily',
        icon: '🏃‍♂️'
      },
      {
        id: 3,
        title: 'Monitor Your Numbers',
        description: 'Log your vitals regularly for better tracking',
        icon: '📊'
      }
    ];
  };

  const getRiskTierClass = (riskTier) => {
    switch (riskTier) {
      case 'LOW': return 'risk-low';
      case 'MODERATE': return 'risk-moderate';
      case 'HIGH': return 'risk-high';
      case 'CRITICAL': return 'risk-critical';
      default: return 'risk-low';
    }
  };

  const getRiskTierText = (riskTier) => {
    switch (riskTier) {
      case 'LOW': return t('lowRisk');
      case 'MODERATE': return t('moderateRisk');
      case 'HIGH': return t('highRisk');
      case 'CRITICAL': return t('criticalRisk');
      default: return t('lowRisk');
    }
  };

  if (dashboardData.loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Welcome Header */}
      <div className="bg-gradient-to-r from-blue-600 to-purple-600 rounded-lg p-6 text-white">
        <h1 className="text-2xl font-bold mb-2">
          {t('welcomeBack')}, {user?.firstName}!
        </h1>
        <p className="text-blue-100">
          {t('todaysVitals')} - {new Date().toLocaleDateString()}
        </p>
      </div>

      {/* CRRS Score Card */}
      {dashboardData.crrsScore && (
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold flex items-center">
              <TrendingUp className="w-5 h-5 mr-2" />
              {t('cardioRenalRiskScore')}
            </h2>
            <span className={`px-3 py-1 rounded-full text-sm font-medium ${getRiskTierClass(dashboardData.crrsScore.riskTier)}`}>
              {getRiskTierText(dashboardData.crrsScore.riskTier)}
            </span>
          </div>
          
          <div className="flex items-center space-x-6">
            <div className="text-4xl font-bold text-gray-900">
              {dashboardData.crrsScore.crrsValue?.toFixed(1)}
            </div>
            <div className="flex-1">
              <div className="w-full bg-gray-200 rounded-full h-3 mb-2">
                <div 
                  className={`h-3 rounded-full transition-all duration-300 ${
                    dashboardData.crrsScore.riskTier === 'LOW' ? 'bg-green-500' :
                    dashboardData.crrsScore.riskTier === 'MODERATE' ? 'bg-yellow-500' :
                    dashboardData.crrsScore.riskTier === 'HIGH' ? 'bg-orange-500' : 'bg-red-500'
                  }`}
                  style={{ width: `${dashboardData.crrsScore.crrsValue}%` }}
                ></div>
              </div>
              <p className="text-sm text-gray-600">
                {dashboardData.crrsScore.explanation || 'Your health risk assessment'}
              </p>
            </div>
          </div>
        </div>
      )}

      {/* Vitals Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {/* Blood Pressure */}
        <div className="vital-card">
          <div className="flex items-center justify-between mb-3">
            <Heart className="w-6 h-6 text-red-500" />
            <span className="text-sm text-gray-500">{t('bloodPressure')}</span>
          </div>
          {dashboardData.latestVitals?.bloodPressure ? (
            <div>
              <div className="text-2xl font-bold text-gray-900">
                {dashboardData.latestVitals.bloodPressure.systolic}/{dashboardData.latestVitals.bloodPressure.diastolic}
              </div>
              <div className="text-sm text-gray-500">mmHg</div>
              <div className="flex items-center mt-2">
                <CheckCircle className="w-4 h-4 text-green-500 mr-1" />
                <span className="text-sm text-green-600">{t('inRange')}</span>
              </div>
            </div>
          ) : (
            <div className="text-center py-4">
              <div className="text-gray-400 mb-2">--/--</div>
              <button className="text-blue-600 text-sm hover:text-blue-700">
                <Plus className="w-4 h-4 inline mr-1" />
                {t('logVitals')}
              </button>
            </div>
          )}
        </div>

        {/* Glucose */}
        <div className="vital-card">
          <div className="flex items-center justify-between mb-3">
            <Activity className="w-6 h-6 text-blue-500" />
            <span className="text-sm text-gray-500">{t('glucose')}</span>
          </div>
          {dashboardData.latestVitals?.glucose ? (
            <div>
              <div className="text-2xl font-bold text-gray-900">
                {dashboardData.latestVitals.glucose.value}
              </div>
              <div className="text-sm text-gray-500">mg/dL</div>
              <div className="flex items-center mt-2">
                <CheckCircle className="w-4 h-4 text-green-500 mr-1" />
                <span className="text-sm text-green-600">{t('inRange')}</span>
              </div>
            </div>
          ) : (
            <div className="text-center py-4">
              <div className="text-gray-400 mb-2">--</div>
              <button className="text-blue-600 text-sm hover:text-blue-700">
                <Plus className="w-4 h-4 inline mr-1" />
                {t('logVitals')}
              </button>
            </div>
          )}
        </div>

        {/* Weight */}
        <div className="vital-card">
          <div className="flex items-center justify-between mb-3">
            <Scale className="w-6 h-6 text-green-500" />
            <span className="text-sm text-gray-500">{t('weight')}</span>
          </div>
          {dashboardData.latestVitals?.weight ? (
            <div>
              <div className="text-2xl font-bold text-gray-900">
                {dashboardData.latestVitals.weight.value}
              </div>
              <div className="text-sm text-gray-500">kg</div>
              <div className="flex items-center mt-2">
                <CheckCircle className="w-4 h-4 text-green-500 mr-1" />
                <span className="text-sm text-green-600">{t('inRange')}</span>
              </div>
            </div>
          ) : (
            <div className="text-center py-4">
              <div className="text-gray-400 mb-2">--</div>
              <button className="text-blue-600 text-sm hover:text-blue-700">
                <Plus className="w-4 h-4 inline mr-1" />
                {t('logVitals')}
              </button>
            </div>
          )}
        </div>

        {/* Heart Rate */}
        <div className="vital-card">
          <div className="flex items-center justify-between mb-3">
            <Heart className="w-6 h-6 text-purple-500" />
            <span className="text-sm text-gray-500">{t('heartRate')}</span>
          </div>
          {dashboardData.latestVitals?.heartRate ? (
            <div>
              <div className="text-2xl font-bold text-gray-900">
                {dashboardData.latestVitals.heartRate.value}
              </div>
              <div className="text-sm text-gray-500">bpm</div>
              <div className="flex items-center mt-2">
                <CheckCircle className="w-4 h-4 text-green-500 mr-1" />
                <span className="text-sm text-green-600">{t('inRange')}</span>
              </div>
            </div>
          ) : (
            <div className="text-center py-4">
              <div className="text-gray-400 mb-2">--</div>
              <button className="text-blue-600 text-sm hover:text-blue-700">
                <Plus className="w-4 h-4 inline mr-1" />
                {t('logVitals')}
              </button>
            </div>
          )}
        </div>
      </div>

      {/* Recent Activity and Health Tips */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Activity */}
        <div className="card">
          <h3 className="text-lg font-semibold mb-4 flex items-center">
            <Calendar className="w-5 h-5 mr-2" />
            {t('recentActivity')}
          </h3>
          {dashboardData.recentEncounters.length > 0 ? (
            <div className="space-y-3">
              {dashboardData.recentEncounters.slice(0, 5).map((encounter) => (
                <div key={encounter.id} className="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg">
                  <div className="w-2 h-2 bg-blue-500 rounded-full"></div>
                  <div className="flex-1">
                    <p className="text-sm font-medium text-gray-900">
                      {encounter.encounterType === 'DAILY_LOG' ? 'Daily Log' : 
                       encounter.encounterType === 'SYMPTOM_REPORT' ? 'Symptom Report' :
                       encounter.encounterType}
                    </p>
                    <p className="text-xs text-gray-500">
                      {new Date(encounter.encounterTimestamp).toLocaleDateString()}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="text-center py-8 text-gray-500">
              <Calendar className="w-12 h-12 mx-auto mb-3 text-gray-300" />
              <p>{t('noDataYet')}</p>
            </div>
          )}
        </div>

        {/* Health Tips */}
        <div className="card">
          <h3 className="text-lg font-semibold mb-4 flex items-center">
            <Target className="w-5 h-5 mr-2" />
            {t('healthTips')}
          </h3>
          <div className="space-y-3">
            {dashboardData.healthTips.map((tip) => (
              <div key={tip.id} className="flex items-start space-x-3 p-3 bg-blue-50 rounded-lg">
                <span className="text-2xl">{tip.icon}</span>
                <div>
                  <h4 className="font-medium text-gray-900">{tip.title}</h4>
                  <p className="text-sm text-gray-600">{tip.description}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;