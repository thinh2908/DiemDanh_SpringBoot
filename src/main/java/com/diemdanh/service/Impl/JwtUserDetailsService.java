package com.diemdanh.service.Impl;
import java.util.List;

import com.diemdanh.Utils.SessionUser;
import com.diemdanh.model.Users;
import com.diemdanh.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Users> usr = userRepository.findByUsername(username);
        if(usr.size()>0) {
            return new SessionUser(usr.get(0));// new User(usr.get(0).getEmail(), usr.get(0).getPassword(),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
