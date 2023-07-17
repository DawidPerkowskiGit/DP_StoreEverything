package com.dmrl.storeverything.DP_Testy;

        import static org.assertj.core.api.Assertions.assertThat;

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
        import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class InformationRepositoryTest {


    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddInformation() {


        String[] tytulyOgloszen = {"Sprzedam Opla1" , "Sprzedam Opla2" , "Sprzedam Opla 4"};

        for (String tytul: tytulyOgloszen
             ) {
            Information information = new Information();
            information.setUser(userRepository.getById(1L));
            information.setTitle(tytul);
            information.setContent("Sprzedam Opla, stan dobry");
            information.setLink("www.com");
            information.setAddDate(date);
            information.setRememberDate(new Date(2022,06,26));

            SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");

            Date tempDate = new Date(122, 0, 21);

            information.setRememberDate(tempDate);
            information.setCategory(categoryRepository.getById(1L));
            Information saveInformation = informationRepository.save(information);
            Information existInformation = entityManager.find(Information.class, saveInformation.getInformationId());

            assertThat(existInformation.getTitle()).isEqualTo(information.getTitle());
        }
    }

    @Test
    public void testAddInformation2() {


        String[] tytulyOgloszen = {"Odam Opla 4" , "Opla Oddam 10" , "Opel do oddania"};

        for (String tytul: tytulyOgloszen
        ) {
            Information information = new Information();
            information.setUser(userRepository.getById(2L));
            information.setTitle(tytul);
            information.setContent("Oddaje opla za darmo");
            information.setLink("www.com");
            information.setAddDate(date);
            information.setRememberDate(new Date(2022,06,26));

            SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");

            Date tempDate = new Date(122, 0, 21);

            information.setRememberDate(tempDate);
            information.setCategory(categoryRepository.getById(2L));
            Information saveInformation = informationRepository.save(information);
            Information existInformation = entityManager.find(Information.class, saveInformation.getInformationId());

            assertThat(existInformation.getTitle()).isEqualTo(information.getTitle());
        }
    }

    @Test
    public void testFindUserByLogin() {
        String title = "Sprzedam Opla4";

        Information information = informationRepository.findByInformationTitle(title);

        assertThat(information).isNotNull();
    }

    @Test
    public void queryTest() {
        Long userID = 2L;

        List<Information> fewInfos = informationRepository.findFewRecentlyAddedInfos(userID);

        System.out.println(fewInfos);

    }

    @Test
    public void queryTest2() {
        Long userID = 1L;

        List<Information> fewReceives = informationRepository.findFewRecentlyReceivedInfos(userID);

        System.out.println(fewReceives);
    }

}