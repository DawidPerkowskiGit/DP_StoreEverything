package com.dmrl.storeverything.informationShare;


import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShareInformationRepository extends JpaRepository<ShareInformation, Long>{

        @Query("SELECT s FROM ShareInformation s WHERE s.shareId = ?1")
        ShareInformation findShareById(Long id);

        @Query("SELECT s FROM ShareInformation s WHERE s.userReceiving.id = ?1")
        List<ShareInformation> findInfosReceivedByUser(Long id);
}
