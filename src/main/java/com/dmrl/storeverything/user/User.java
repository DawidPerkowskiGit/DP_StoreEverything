package com.dmrl.storeverything.user;

import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.informationShare.ShareInformation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 45)
    private String email;
    @Column(nullable = false, unique = true, length = 30)
    private String login;
    @Column(nullable = false, length = 64)
    private String password;
    @Column(nullable = false, length = 20)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false, length = 10)
    private String birthday;
    @Column(name = "regDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate = new Timestamp(System.currentTimeMillis());
    @Column(name = "enabled", nullable = false, length = 2)
    private Integer enabled = 1;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() { return roles; }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @OneToMany(mappedBy = "user")
    private List<Information> information;

    @OneToMany(mappedBy = "userReceiving")
    private List<ShareInformation> shareInformations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getEnabled() { return enabled; }

    public void setEnabled(Integer enabled) { this.enabled = enabled;    }


    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information.add(information);
    }

    public String getFullName() {
        return this.getFirstName()+" "+getLastName();
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    public List<ShareInformation> getShareInformations() {
        return shareInformations;
    }

    public void setShareInformations(List<ShareInformation> shareInformations) {
        this.shareInformations = shareInformations;
    }

    public String toString() {
        return "id : " + getId() + ", firstName : " + getFirstName() + ", lastName : " + getLastName() + ", email : " + getEmail() + ", birthday : " + getBirthday();
    }

}
