public class Main {
    public static void main(String[] args) {
        String serverHost = "smtp.mailtrap.io";
        int serverPort = 2525;

        SMTPClient smtpClient = new SMTPClient(serverHost, serverPort);
        String[] tos = {"luca.coduri@heig-vd.ch", "pouetpouet@gmail.com"};
        Mail mail = new Mail(tos, "chloe.fontaine@heig-vd.ch", "wesh", "jme demandais si sur l'avenue on se retrouverait.");

        smtpClient.sendMail(mail);
    }
}
