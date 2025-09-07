import React from 'react';
import { useTranslation } from 'react-i18next';

const VitalsPage = () => {
  const { t } = useTranslation();

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">{t('vitals')}</h1>
      <div className="card">
        <p className="text-gray-600">Vitals tracking functionality will be implemented here</p>
      </div>
    </div>
  );
};

export default VitalsPage;