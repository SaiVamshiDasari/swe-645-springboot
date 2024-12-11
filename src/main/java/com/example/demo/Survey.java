package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String telephone;
    private String email;
    private LocalDate dateOfSurvey;

    private String likedCampus; // Stored as comma-separated values

    private String howInterested;
    private String recommendLikelihood;

    @Lob
    private String comments;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDateOfSurvey() { return dateOfSurvey; }
    public void setDateOfSurvey(LocalDate dateOfSurvey) { this.dateOfSurvey = dateOfSurvey; }
    public String getLikedCampus() { return likedCampus; }
    public void setLikedCampus(String likedCampus) { this.likedCampus = likedCampus; }
    public String getHowInterested() { return howInterested; }
    public void setHowInterested(String howInterested) { this.howInterested = howInterested; }
    public String getRecommendLikelihood() { return recommendLikelihood; }
    public void setRecommendLikelihood(String recommendLikelihood) { this.recommendLikelihood = recommendLikelihood; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
