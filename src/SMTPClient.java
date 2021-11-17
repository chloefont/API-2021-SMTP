public class SMTPClient {
    private String ip;
    private int port;

    public SMTPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean sendMail(Mail mail) {
        return true;
    }
}
