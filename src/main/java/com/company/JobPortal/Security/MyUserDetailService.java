package com.company.JobPortal.Security;

import com.company.JobPortal.Model.User.Users;
import com.company.JobPortal.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user=  userRepo.findByEmail(username);
        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        }

        throw new UsernameNotFoundException("User Not Found");
    }
}
