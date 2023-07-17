package com.dmrl.storeverything.DP_Testy;

import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.information.InformationRepository;
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
public class AddNewInformations {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Test
    public void addInformation(int optional) {
        Information information = new Information();
        information.setUser(userRepository.getById(25L));
        information.setTitle("tytul " + optional);
        information.setContent("Zawartosc informacji - duża ilość tekstu, Zawartosc informacji - duża ilość tekstu, Zawartosc informacji - duża ilość tekstu , Zawartosc informacji - duża ilość tekstu , Zawartosc informacji - duża ilość tekstu , Zawartosc informacji - duża ilość tekstu ");
        information.setLink("www.com");

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        information.setAddDate(date);
        information.setRememberDate(date);

        SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");

        Date tempDate = new Date(122, 0, 21);

        information.setRememberDate(tempDate);
        information.setCategory(categoryRepository.getById(1L));
        Information saveInformation = informationRepository.save(information);
        Information existInformation = entityManager.find(Information.class, saveInformation.getInformationId());

        assertThat(existInformation.getTitle()).isEqualTo(information.getTitle());
    }

    @Test
    public void addMultipleInformations() {
        for (int i = 0; i < 20 ; i++) {
            addInformation(i);
        }
    }
}
