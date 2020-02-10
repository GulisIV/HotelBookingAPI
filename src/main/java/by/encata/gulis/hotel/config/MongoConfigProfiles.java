package by.encata.gulis.hotel.config;

import by.encata.gulis.hotel.domain.Role;
import by.encata.gulis.hotel.domain.User;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class MongoConfigProfiles {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return new SimpleMongoDbFactory(mongoClient, "hotel");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        User admin = new User();
        admin.setUsername("hotelAdminTest");
        admin.setPassword(passwordEncoder.encode("321"));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        admin.setRoles(roles);
        mongoTemplate.save(admin);

        return mongoTemplate;
    }
}
