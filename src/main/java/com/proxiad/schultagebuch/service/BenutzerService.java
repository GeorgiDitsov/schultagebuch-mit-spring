package com.proxiad.schultagebuch.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxiad.schultagebuch.entity.Benutzer;
import com.proxiad.schultagebuch.repository.BenutzerRepository;

@Service
@Transactional
public class BenutzerService implements UserDetailsService {

	@Autowired
	private BenutzerRepository repo;

	public void save(Benutzer benutzer) {
		repo.save(benutzer);
	}

	public List<Benutzer> findAll() {
		return repo.findAllByOrderByIdAsc();
	}

	public Optional<Benutzer> find(int id) {
		return repo.findById(id);
	}

	public Optional<Benutzer> findByBenutzerName(String benutzername) {
		return repo.findByBenutzerName(benutzername);
	}

	public void delete(Benutzer benutzer) {
		repo.delete(benutzer);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Benutzer benutzer = repo.findByBenutzerName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(benutzer.getBenutzerName(), benutzer.getPasswort(), getAuthorities(benutzer));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Benutzer benutzer) {
		String rolle = benutzer.getRolle().getName();
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(rolle);
		return authorities;
	}
}
