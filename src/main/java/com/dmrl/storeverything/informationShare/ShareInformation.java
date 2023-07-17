package com.dmrl.storeverything.informationShare;

import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.user.User;

import javax.persistence.*;

@Entity
@Table(name = "shareInformation")
public class ShareInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "share_id")
    private Long shareId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "information_id")
    private Information information;

    @ManyToOne
    @JoinColumn(name = "receiving_user", referencedColumnName = "id")
    private User userReceiving;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public User getUserReceiving() {
        return userReceiving;
    }

    public void setUserReceiving(User userReceiving) {
        this.userReceiving = userReceiving;
    }
}
