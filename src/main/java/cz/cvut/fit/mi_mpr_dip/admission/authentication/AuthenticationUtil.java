package cz.cvut.fit.mi_mpr_dip.admission.authentication;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;

public interface AuthenticationUtil {

	public Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> roles);
}
