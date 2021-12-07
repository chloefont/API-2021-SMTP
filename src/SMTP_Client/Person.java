package SMTP_Client;

public class Person {
    private final String email;

    public Person(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "SMTP_Client.Person{" +
                ", email='" + email + '\'' +
                '}';
    }
}
