package com.harrisonmauseth.petfoodtracker.security;

import com.harrisonmauseth.petfoodtracker.dao.UserDao;
import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Role;
import com.harrisonmauseth.petfoodtracker.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UserEntityDetailsService authenticates a user from the database.
 */
@Service
public class UserEntityDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserEntityDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = null;
        try {
            user = userDao.getUserByUsername(username);
        } catch (DaoException e) {
            throw new UsernameNotFoundException("Cannot connect to user database");
        }
        if (user != null) {
            return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
        throw new UsernameNotFoundException("Username not found");
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
