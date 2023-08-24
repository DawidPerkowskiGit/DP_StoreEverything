package com.dmrl.storeverything.informationShare;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for Information share entries
 */
public interface ShareInformationRepository extends JpaRepository<ShareInformation, Long>{

        @Query("SELECT s FROM ShareInformation s WHERE s.shareId = ?1")
        ShareInformation findShareById(Long id);

        @Query("SELECT s FROM ShareInformation s WHERE s.userReceiving.id = ?1")
        List<ShareInformation> findInfosReceivedByUser(Long id);
}
