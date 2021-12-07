package SMTP_Client;

/**
 * Cette classe contient uniquement le mail d'une personne.
 * Mais il est possible grâce à elle d'étendre les fonctionalités. Par exemple ajouter le nom dans le mail généré.
 */
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
