package dev.local.springjwt.services.security;

import dev.local.springjwt.dao.ProfilDao;
import dev.local.springjwt.dao.UserDao;
import dev.local.springjwt.model.Profil;
import dev.local.springjwt.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsImp implements UserDetailsService {
    final UserDao userDao;
    final ProfilDao profilDao;
    UserDetailsImp(ProfilDao profil,UserDao userDao){
        this.profilDao = profil;
        this.userDao = userDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = this.userDao.findByUsername(username);
        System.out.println("USER DETAIL"+ u.getUsername());
        return new User(u.getUsername(),u.getPassword(),
                true,true,true,true,getAuthorities(u.getProfils()));

    }
    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Profil> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Profil role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
