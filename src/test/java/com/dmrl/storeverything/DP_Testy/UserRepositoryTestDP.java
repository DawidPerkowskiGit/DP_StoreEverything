package com.dmrl.storeverything.DP_Testy;

import com.dmrl.storeverything.user.User;
import com.dmrl.storeverything.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTestDP {


    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setLogin("123123");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("123123");
        user.setPassword(encodedPassword);
        user.setFirstName("Dawid");
        user.setLastName("Perkowski");
        user.setBirthday(formatter.format(date));
        user.setEmail("mail@gmail.com");
        user.setRegDate(date);
        user.setEnabled(1);

        User saveUser = repo.save(user);

        User existUser = entityManager.find(User.class, saveUser.getId());

        assertThat(existUser.getLogin()).isEqualTo(user.getLogin());

    }

    @Test
    public void testFindUserByLogin() {
        String login = "123123";

        User user = repo.findByLogin(login);

        assertThat(user).isNotNull();
    }

    @Test
    public void testEncryptPass() {
        String password = "test";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        System.out.println(encodedPassword);
    }

}