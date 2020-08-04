package com.whs.cybsec;

/**
 * @author Satvik Dasari
 *  Entity Class for User
 */
public class User {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String organization;
    private String phoneNumber;
    private String grade;
    private String dietaryRestrictions;
    private String shirtSize;
    private String role;

    public User(String firstName, String lastName, String password, String email, String organization, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.organization = organization;
        this.role = role;
    }

    public User(String firstName, String lastName, String password, String email, String organization, String phoneNumber, String grade, String dietaryRestrictions, String shirtSize, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.organization = organization;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.dietaryRestrictions = dietaryRestrictions;
        this.shirtSize = shirtSize;
        this.role = role;
    }

    public User() {
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", grade='" + grade + '\'' +
                ", dietaryRestrictions='" + dietaryRestrictions + '\'' +
                ", shirtSize='" + shirtSize + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
