package by.encata.gulis.hotel.config;

import by.encata.gulis.hotel.domain.Role;
import by.encata.gulis.hotel.domain.User;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class MongoConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "hotel";
    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);

        String password = passwordEncoder.encode("321");
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN));
        User admin = new User("1", "hotelAdmin", password, roles);
        mongoTemplate.save(admin);

        String passwordUser = passwordEncoder.encode("123");
        Set<Role> roleUser = new HashSet<>(Collections.singletonList(Role.USER));
        User user = new User("2", "hotelUser", passwordUser, roleUser);
        mongoTemplate.save(user);

        return mongoTemplate;
    }

/*   @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return new SimpleMongoDbFactory(mongoClient, "hotel");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        String password = passwordEncoder.encode("321");
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN));
        User admin = new User("1", "hotelAdmin", password, roles);
        mongoTemplate.save(admin);

        String passwordUser = passwordEncoder.encode("123");
        Set<Role> roleUser = new HashSet<>(Collections.singletonList(Role.USER));
        User user = new User("2", "hotelUser", passwordUser, roleUser);
        mongoTemplate.save(user);

        return mongoTemplate;
    }*/
}
