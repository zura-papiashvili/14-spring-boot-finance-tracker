package com.example.finance_tracker.modules.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.finance_tracker.modules.user.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            User userObj = user.get();
            UserBuilder builder = org.springframework.security.core.userdetails.User
                    .withUsername(userObj.getUsername());
            builder.password(userObj.getPassword());
            return builder.build();

        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
