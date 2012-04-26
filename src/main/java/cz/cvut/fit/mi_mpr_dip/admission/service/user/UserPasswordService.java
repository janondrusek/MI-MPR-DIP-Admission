package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserPasswordService {

	public Set<UserIdentity> createRandomPassword(String email);

	public UserIdentity createRandomPassword(UserIdentity userIdentity);

	public UserIdentity createRandomPassword(String admissionCode, String email);
}
