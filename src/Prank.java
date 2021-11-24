public class Prank {
    String subject;
    String content;

    Prank(String subject, String content){
        this.subject = subject;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Prank{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
