package com.example.oop_cw;

// OOP concept: Encapsulation - Hiding the internal state and requiring all interactions through getter/setter methods.
public class User_Register {
    // OOP concept: Data Abstraction - Only the necessary details (attributes) are exposed.
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String username;
    private final String password;
    private final String confirmPassword;

    // OOP concept: Constructor overloading - Allows flexibility in object creation.
    public User_Register(String firstName, String lastName, String email, String phoneNumber, String username, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters (Encapsulation)
    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public String getemail() {
        return email;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getconfirmPassword() {
        return confirmPassword;
    }

}
