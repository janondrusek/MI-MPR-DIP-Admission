// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatus;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatusDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect MaritalStatusDataOnDemand_Roo_DataOnDemand {
    
    declare @type: MaritalStatusDataOnDemand: @Component;
    
    private Random MaritalStatusDataOnDemand.rnd = new SecureRandom();
    
    private List<MaritalStatus> MaritalStatusDataOnDemand.data;
    
    public MaritalStatus MaritalStatusDataOnDemand.getNewTransientMaritalStatus(int index) {
        MaritalStatus obj = new MaritalStatus();
        return obj;
    }
    
    public MaritalStatus MaritalStatusDataOnDemand.getSpecificMaritalStatus(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        MaritalStatus obj = data.get(index);
        Long id = obj.getId();
        return MaritalStatus.findMaritalStatus(id);
    }
    
    public MaritalStatus MaritalStatusDataOnDemand.getRandomMaritalStatus() {
        init();
        MaritalStatus obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return MaritalStatus.findMaritalStatus(id);
    }
    
    public boolean MaritalStatusDataOnDemand.modifyMaritalStatus(MaritalStatus obj) {
        return false;
    }
    
    public void MaritalStatusDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = MaritalStatus.findMaritalStatusEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'MaritalStatus' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<MaritalStatus>();
        for (int i = 0; i < 10; i++) {
            MaritalStatus obj = getNewTransientMaritalStatus(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}