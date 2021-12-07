package SMTP_Client;

public class Prank {
    String subject;
    String content;

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
