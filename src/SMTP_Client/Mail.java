package SMTP_Client;

import java.util.Arrays;

/**
 * Cette classe contient toutes les informations nécessaires pour l'envoie d'un mail.
 */
public class Mail {
    final private String[] to;
    final private String from;
    final private String subject;
    final private String content;

    public Mail(String[] to, String from, String subject, String content) {
        this.to = to.clone();
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String[] getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "SMTP_Client.Mail{" +
                "to=" + Arrays.toString(to) +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
