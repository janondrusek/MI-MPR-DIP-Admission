// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.TermEndpointHelperImpl;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;

privileged aspect TermEndpointHelperImpl_Roo_JavaBean {
    
    public BusinessExceptionUtil TermEndpointHelperImpl.getBusinessExceptionUtil() {
        return this.businessExceptionUtil;
    }
    
    public void TermEndpointHelperImpl.setBusinessExceptionUtil(BusinessExceptionUtil businessExceptionUtil) {
        this.businessExceptionUtil = businessExceptionUtil;
    }
    
    public TermDateUtils TermEndpointHelperImpl.getTermDateUtils() {
        return this.termDateUtils;
    }
    
    public void TermEndpointHelperImpl.setTermDateUtils(TermDateUtils termDateUtils) {
        this.termDateUtils = termDateUtils;
    }
    
}
