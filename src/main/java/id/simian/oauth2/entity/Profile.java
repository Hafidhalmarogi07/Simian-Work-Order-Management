package id.simian.oauth2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.simian.oauth2.util.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_profile")
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String fullname;

    @Column(unique = true)
    private String username;

    private String photoProfileLarge;

    private String photoProfileSmall;

    private String videoProfile;

    private String description;

    private String primaryJob;

    private String jobTittle;

    @JsonIgnore
    private String verifyToken;

    @JsonIgnore
    private Date expiredVerifyToken;

    @JsonIgnore
    private Integer chance;

    private Boolean verify;

    private Boolean isTalent;

    private Float rating;

    @Column(name = "_group")
    private String group;

    @JsonIgnore
    @Column(columnDefinition = "text")
    private String follow;

    private String idDevice;

    private Integer totalOrder ;

    private Long rate;

    private Integer rejected,accepted,notResponded,completed,missed ;

    private String phoneNumber;

    private Long numberOfFollower, accountNumber;

    private String socialMedia, socialMediaAccount;

    private Boolean instruction_flag = false;

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollow() {
        return follow;
    }

    @Transient
    @JsonProperty("following")
    public  List<Long> getFollowing(){
        List<Long> following = new ArrayList<>();
        if(this.follow != null){
            for(String follow: this.follow.split(",")){
                following.add(Long.valueOf(follow));
            }
        }
        return following;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPhotoProfileSmall() {
        return photoProfileSmall;
    }

    public void setPhotoProfileSmall(String photoProfileSmall) {
        this.photoProfileSmall = photoProfileSmall;
    }

    public Boolean getIsTalent() {
        return isTalent;
    }

    public void setIsTalent(Boolean talent) {
        isTalent = talent;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public Date getExpiredVerifyToken() {
        return expiredVerifyToken;
    }

    public void setExpiredVerifyToken(Date expiredVerifyToken) {
        this.expiredVerifyToken = expiredVerifyToken;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoProfileLarge() {
        return photoProfileLarge;
    }

    public void setPhotoProfileLarge(String photoProfileLarge) {
        this.photoProfileLarge = photoProfileLarge;
    }

    public String getVideoProfile() {
        return videoProfile;
    }

    public void setVideoProfile(String videoProfile) {
        this.videoProfile = videoProfile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryJob() {
        return primaryJob;
    }

    public void setPrimaryJob(String primaryJob) {
        this.primaryJob = primaryJob;
    }

    public String getJobTittle() {
        return jobTittle;
    }

    public void setJobTittle(String jobTittle) {
        this.jobTittle = jobTittle;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }

    public Integer getNotResponded() {
        return notResponded;
    }

    public void setNotResponded(Integer notResponded) {
        this.notResponded = notResponded;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getNumberOfFollower() {
        return numberOfFollower;
    }

    public void setNumberOfFollower(Long numberOfFollower) {
        this.numberOfFollower = numberOfFollower;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String findYou) {
        this.socialMedia = findYou;
    }

    public String getSocialMediaAccount() {
        return socialMediaAccount;
    }

    public void setSocialMediaAccount(String findYouAccount) {
        this.socialMediaAccount = findYouAccount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getMissed() {
        return missed;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public Boolean getInstruction_flag() {
        return instruction_flag;
    }

    public void setInstruction_flag(Boolean instruction_flag) {
        this.instruction_flag = instruction_flag;
    }

    public Boolean getTalent() {
        return isTalent;
    }

    public void setTalent(Boolean talent) {
        isTalent = talent;
    }

}
