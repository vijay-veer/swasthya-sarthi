import React from 'react';
import { useTranslation } from 'react-i18next';

const LogVitalsPage = () => {
  const { t } = useTranslation();

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">{t('logVitals')}</h1>
      <div className="card">
        <p className="text-gray-600">Vital logging functionality will be implemented here</p>
      </div>
    </div>
  );
};

export default LogVitalsPage;