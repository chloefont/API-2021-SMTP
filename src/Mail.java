public class Mail {
    private String[] to;
    private String from;
    private String subject;
    private String content;

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
}
