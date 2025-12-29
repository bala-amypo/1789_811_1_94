package com.example.demo.security;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, DemoUser> users = new ConcurrentHashMap<>();
 
    public CustomUserDetailsService() {
        users.put("admin@city.com",
                new DemoUser("Admin", "admin@city.com", "admin123", "ADMIN"));
    }

    public DemoUser getByEmail(String email) {
        DemoUser user = users.get(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
//     public DemoUser registerUser(String name, String email, String password, String role) {
//     if (users.containsKey(email)) {
//         throw new RuntimeException("User already exists");
//     }

//     // Default role safety
//     if (role == null || role.isBlank()) {
//         role = "USER";
//     }

//     DemoUser user = new DemoUser(name, email, password, role.toUpperCase());
//     users.put(email, user);
//     return user;
// }


    public DemoUser registerUser(String name, String email, String password) {
        if (users.containsKey(email)) {
            throw new RuntimeException("User already exists");
        }
        DemoUser user = new DemoUser(name, email, password, "USER");
        users.put(email, user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        DemoUser user = users.get(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    public static class DemoUser {
        private final String name;
        private final String email;
        private final String password;
        private final String role;

        public DemoUser(String name, String email, String password, String role) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
    }
}
