package com.dmrl.storeverything.DP_Testy;

import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.information.InformationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class deleteInformationTest {

    @Autowired
    InformationRepository informationRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void whenDeleteFromCustomQuery_thenDeletingShouldBeSuccessful() {
        //informationRepository.deleteInformationById(6L);
        System.out.println("stuff");
        informationRepository.deleteById(2L);

        //informationRepository.delete(informationRepository.getById(2L).setCategory(null));
        //assertThat(informationRepository.count()).isEqualTo(1);
    }
    @Test
    public void deleteCategory() {

        categoryRepository.deleteById(9L);
    }
}
