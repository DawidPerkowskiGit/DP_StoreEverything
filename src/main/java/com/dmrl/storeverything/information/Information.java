package com.dmrl.storeverything.information;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.informationShare.ShareInformation;
import com.dmrl.storeverything.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "informations")
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "information_id")
    private Long informationId;

    //@ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "add_by_user", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, length = 500)
    private String link = "www.com/link1"; //TBD

    @Column(name = "addDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addDate = new Date();

    @Column(name = "rememberDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rememberDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "information")
    private List<ShareInformation> shareInformation;

    public Long getInformationId() {
        return informationId;
    }

    public void setInformationId(Long id) {
        this.informationId = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userID) {
        this.user = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getAddDate() { return this.addDate; }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getRememberDate() { return this.rememberDate; }

    public void setRememberDate(Date rememberDate) {
        this.rememberDate = rememberDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ShareInformation> getShareInformation() {
        return shareInformation;
    }

    public void setShareInformation(List<ShareInformation> shareInformation) {
        this.shareInformation = shareInformation;
    }

    public String getFormatedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    public Date formatStringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(date);
    }

    public String toString() {
        return "informationId : " + getInformationId() + ", Added by user : " + getUser().getFirstName() + " " + getUser().getLastName() + ", title : " + getTitle() + ", content : " + getContent() + ", category : " + getCategory().getCategoryName() + ", Add date : " + getFormatedDate(getAddDate()) + ", Remember date : " + getFormatedDate(getRememberDate());
    }
}
