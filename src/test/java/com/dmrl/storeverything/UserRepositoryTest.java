package com.dmrl.storeverything;

import com.dmrl.storeverything.user.User;
import com.dmrl.storeverything.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {


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
        user.setPassword("123123");
        user.setFirstName("123123");
        user.setLastName("123123");
        user.setBirthday(formatter.format(date));
        user.setEmail("123@123.123");
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

}