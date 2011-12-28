package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Map;

public interface AdminService {

	public Map<String, String> getApplicationProperties();

	public String getBuildNumber();
}