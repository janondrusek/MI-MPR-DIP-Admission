package cz.cvut.fit.mi_mpr_dip.admission.exception.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@RooJavaBean
public class BusinessExceptionHelper implements ExceptionHelper<BusinessException> {

	private Map<Integer, String> messages;

	public void throwException(Integer code) {
		throwException(code, getMessages().get(code));
	}

	public void throwException(Integer code, String message) {
		throw new BusinessException(code, message);
	}

	@Required
	public void setMessages(Map<Integer, String> messages) {
		this.messages = messages;
	}
}
