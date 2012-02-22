package cz.cvut.fit.mi_mpr_dip.admission.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fit.mi_mpr_dip.admission.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping("/viewproperties")
	public ModelAndView viewApplicationProperties() {
		ModelAndView modelMap = new ModelAndView("properties");
		Map<String, String> applicationProperties = getAdminService().getApplicationProperties();
		modelMap.addObject("applicationProperties", applicationProperties);
		modelMap.addObject("buildNumber", getAdminService().getBuildNumber());

		return modelMap;
	}

	public AdminService getAdminService() {
		return adminService;
	}

}
