package cz.cvut.fit.mi_mpr_dip.admission.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPermission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;

@Component
public class AuthenticationUtilImpl implements AuthenticationUtil {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> roles) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (roles != null) {
			for (UserRole role : roles) {
				authorities.addAll(getAuthorities(role));
			}
		}
		return authorities;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(UserRole role) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		Set<UserPermission> permissions = role.getPermissions();
		if (permissions != null) {
			for (UserPermission permission : permissions) {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		return authorities;
	}

}
