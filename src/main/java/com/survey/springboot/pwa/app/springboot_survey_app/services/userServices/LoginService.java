package com.survey.springboot.pwa.app.springboot_survey_app.services.userServices;

import com.survey.springboot.pwa.app.springboot_survey_app.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El correo" + email + " no existe"));
        Collection<? extends GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName().name())
        );

        return new User(
                userEntity.getEmail(),
                userEntity.getCredential().getPassword(),
                userEntity.isState(),
                true,
                true,
                true,
                authorities
        );
    }
}
