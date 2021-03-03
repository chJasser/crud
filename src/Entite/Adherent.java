package Entite;

import java.io.InputStream;
import java.sql.Blob;

public class Adherent {

    private int idad;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
    private Blob userImage ;
    private int phone;

    public Adherent() {
    }

    public Adherent(String firstName, String lastName, String email,String password,Blob userImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userImage=userImage;
    }

    public Adherent(int idad, String firstName, String lastName, String email, String password, int age, Blob userImage, int phone) {
        this.idad = idad;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.userImage = userImage;
        this.phone = phone;
    }

    public int getIdad() {
        return idad;
    }

    public void setIdad(int idad) {
        this.idad = idad;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Blob getUserImage() {
        return userImage;
    }

    public void setUserImage(Blob userImage) {
        this.userImage = userImage;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Adherent{" + "idad=" + idad + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", age=" + age + ", userImage=" + userImage + ", phone=" + phone + '}';
    }
    
}
