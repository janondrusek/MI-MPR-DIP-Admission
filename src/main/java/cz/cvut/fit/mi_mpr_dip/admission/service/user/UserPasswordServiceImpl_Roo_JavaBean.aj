// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.PersonDao;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.PasswordGenerator;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordServiceImpl;

privileged aspect UserPasswordServiceImpl_Roo_JavaBean {
    
    public PasswordGenerator UserPasswordServiceImpl.getPasswordGenerator() {
        return this.passwordGenerator;
    }
    
    public void UserPasswordServiceImpl.setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
    
    public AdmissionDao UserPasswordServiceImpl.getAdmissionDao() {
        return this.admissionDao;
    }
    
    public void UserPasswordServiceImpl.setAdmissionDao(AdmissionDao admissionDao) {
        this.admissionDao = admissionDao;
    }
    
    public PersonDao UserPasswordServiceImpl.getPersonDao() {
        return this.personDao;
    }
    
    public void UserPasswordServiceImpl.setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }
    
}
