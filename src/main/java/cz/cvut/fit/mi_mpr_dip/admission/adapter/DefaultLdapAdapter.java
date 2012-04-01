package cz.cvut.fit.mi_mpr_dip.admission.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

public class DefaultLdapAdapter implements AuthenticationAdapter {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Override
	public boolean authenticate(String username, String password) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("uid", username));
		return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
