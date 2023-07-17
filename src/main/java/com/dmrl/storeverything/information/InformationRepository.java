package com.dmrl.storeverything.information;

import com.dmrl.storeverything.information.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InformationRepository extends JpaRepository<Information, Long> {

    @Query("SELECT i FROM Information i WHERE i.title = ?1")
    Information findByInformationTitle(String title);


    @Query("SELECT i FROM Information i WHERE i.user.id = ?1")
    List<Information> returnAuthorsInformations(Long id);


    @Query("DELETE FROM Information i WHERE i.informationId =:id")
    void deleteInformationById(@Param("id")Long id);

    @Query("SELECT i FROM Information i WHERE i.informationId = ?1")
    Information findByInformationId(Long id);

    @Query("SELECT i FROM Information i WHERE i.rememberDate = ?1 AND i.user.id = ?2")
    List<Information> returnCurrentDayInformations(Date date, Long id);

    @Query(nativeQuery = true, value = "SELECT i.* FROM informations i WHERE i.add_by_user = :userID ORDER BY i.add_date DESC limit 3")
    List<Information> findFewRecentlyAddedInfos(@Param("userID") Long userID);

    @Query(nativeQuery = true, value = "SELECT i.* FROM informations i, share_information si WHERE si.information_information_id = i.information_id AND si.receiving_user = :userID ORDER BY i.add_date DESC limit 3")
    List<Information> findFewRecentlyReceivedInfos(@Param("userID") Long userID);
}
