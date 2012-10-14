package cz.cvut.fit.mi_mpr_dip.admission.service.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;
import freemarker.template.TemplateException;

@RooJavaBean
public class PasswordResetServiceImpl implements PasswordResetService {

	private static final Logger log = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private FreeMarkerConfig freeMarkerConfig;

	private String from;
	private String resetTemplate;
	private String subject;

	@Override
	public void send(UserIdentity userIdentity, String[] emails) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessage.setText(getText(userIdentity), "UTF-8", "html");
			mimeMessage.setSubject(getSubject());
			mimeMessage.setFrom(new InternetAddress(getFrom()));
			mimeMessage.setRecipients(RecipientType.TO, getAddresses(emails));
			
			getMailSender().send(mimeMessage);
		} catch (Exception e) {
			log.warn("Unable to send password reset email for [{}] [{}]", emails, userIdentity);
			throw new TechnicalException(e);
		}
	}

	private Address[] getAddresses(String[] emails) throws AddressException {
		InternetAddress[] addresses = new InternetAddress[emails.length];
		for (int i = 0; i < addresses.length; i++) {
			addresses[i] = new InternetAddress(emails[i]);
		}
		return addresses;
	}

	private String getText(UserIdentity userIdentity) throws IOException, TemplateException {
		return FreeMarkerTemplateUtils.processTemplateIntoString(
				getFreeMarkerConfig().getConfiguration().getTemplate(getResetTemplate()), getModel(userIdentity));
	}

	private Map<String, String> getModel(UserIdentity userIdentity) {
		Map<String, String> model = new HashMap<>();
		model.put(WebKeys.USERNAME, userIdentity.getUsername());
		model.put(WebKeys.PASSWORD, userIdentity.getUserPassword().getPlaintext());
		return model;
	}

	@Required
	public void setFrom(String from) {
		this.from = from;
	}

	@Required
	public void setResetTemplate(String resetTemplate) {
		this.resetTemplate = resetTemplate;
	}

	@Required
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
