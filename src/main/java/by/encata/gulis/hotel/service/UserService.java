package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.exception.user.UserExistsException;
import by.encata.gulis.hotel.repository.UserRepo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = userRepo.findByUsername(username);
            String role = user.getRole();
            if(user.getId() == null){
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(role))
                );
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }

    public void addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            throw new UserExistsException("User " + user.getUsername() + " already exists!");
        }

//        user.setRole(String.valueOf(Role.USER));
        user.setUserZoneId(ZoneId.of("Europe/Minsk"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

}
