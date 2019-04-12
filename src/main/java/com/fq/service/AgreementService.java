/*
Date: 04/12,2019, 15:07
*/
package com.fq.service;

import com.fq.dao.AgreementDao;
import com.fq.model.Agreement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementService {
    private static Logger logger = LoggerFactory.getLogger(AgreementService.class);

    @Autowired
    private AgreementDao agreementDao;

    public void agree(Agreement agreement) {
        Agreement temp = agreementDao.selectAgreement(agreement.getUserId(), agreement.getEntityId(), agreement.getEntityType());
        if (temp == null) {
            agreementDao.addAgreement(agreement);
        } else {
            agreement.setId(temp.getId());
            logger.info(agreement.getId() + " " + agreement.getStatus());
            agreementDao.updateAgreementStatus(agreement);
        }
    }

    public int getAgreementCount(int entityId, int entityType) {
        return agreementDao.getAgreementCount(entityId, entityType);
    }

    public int getAgreeStatus(int userId, int entityId, int entityType) {
        Agreement agreement = agreementDao.selectAgreement(userId, entityId, entityType);
        if (agreement == null || agreement.getStatus() == 1)
            return 0;
        else
            return 1;
    }
}
