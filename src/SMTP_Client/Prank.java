package SMTP_Client;

/**
 * Un prank contient le sujet et le contenu que l'on souhaite envoyer.
 */
public class Prank {
    String subject;
    String content;

    /**
     *
     * @param subject Le sujet du prank.
     * @param content Le contenu du prank.
     */
    Prank(String subject, String content){
        this.subject = subject;
        this.content = content;
    }

    @Override
    public String toString() {
        return "SMTP_Client.Prank{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
