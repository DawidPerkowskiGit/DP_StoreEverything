package com.dmrl.storeverything.DP_Testy;

import com.dmrl.storeverything.information.InformationRepository;
import com.dmrl.storeverything.informationShare.ShareInformation;
import com.dmrl.storeverything.informationShare.ShareInformationRepository;
import com.dmrl.storeverything.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ShareTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private ShareInformationRepository shareInformationRepository;

    @Test
    public void testAddShare() {

        Long[] infoIds = {10L, 13L};

        for (Long info: infoIds
             ) {
            ShareInformation shareInformation = new ShareInformation();

            shareInformation.setInformation(informationRepository.findByInformationId(info));

            shareInformation.setUserReceiving(userRepository.findByUserId(1L));

            shareInformationRepository.save(shareInformation);
        }



    }
}
