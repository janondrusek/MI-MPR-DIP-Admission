package cz.cvut.fit.mi_mpr_dip.admission.service.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;
import freemarker.template.TemplateException;

@RooJavaBean
@Service
public class AdmissionPasswordResetService implements PasswordResetService {

	private static final Logger log = LoggerFactory.getLogger(AdmissionPasswordResetService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage mailMessage;

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;

	private String resetTemplate;

	private String from;

	@Override
	public void send(String email, UserIdentity userIdentity) {
		getMailMessage().setTo(email);
		try {
			getMailMessage().setText(getText(userIdentity));
			getMailSender().send(getMailMessage());
		} catch (Exception e) {
			log.warn("Unable to send password reset email for [{}] [{}]", email, userIdentity);
			throw new TechnicalException(e);
		}
	}

	private String getText(UserIdentity userIdentity) throws IOException, TemplateException {
		return FreeMarkerTemplateUtils.processTemplateIntoString(
				getFreeMarkerConfig().getConfiguration().getTemplate(getResetTemplate()), getModel(userIdentity));
	}

	private Map<String, String> getModel(UserIdentity userIdentity) {
		Map<String, String> model = new HashMap<String, String>();
		model.put(WebKeys.USERNAME, userIdentity.getUsername());
		model.put(WebKeys.PASSWORD, userIdentity.getUserPassword().getPlaintext());
		return model;
	}

	@Required
	public void setResetTemplate(String resetTemplate) {
		this.resetTemplate = resetTemplate;
	}

}
