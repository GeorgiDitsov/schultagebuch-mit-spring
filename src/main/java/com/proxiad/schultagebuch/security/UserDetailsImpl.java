package com.proxiad.schultagebuch.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.service.BenutzerService;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private BenutzerService benutzerService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Benutzer benutzer = benutzerService.findByBenutzerName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(benutzer.getBenutzerName(), benutzer.getPasswort(), getAuthorities(benutzer));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Benutzer benutzer) {
		return AuthorityUtils.createAuthorityList(benutzer.getRolle().getName());
	}
}
