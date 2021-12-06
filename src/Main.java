import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String serverHost = "smtp.mailtrap.io";
        int serverPort = 2525;

        SMTPClient smtpClient = new SMTPClient(serverHost, serverPort, "7f047c3acc72e6", "5d8694801edbf5");

        try{
            ConfigurationManager cm = new ConfigurationManager("victims.txt", "pranks.txt");
            //System.out.println(Arrays.toString(cm.getPersons()));
            //System.out.println(Arrays.toString(cm.getPranks()));
            Mail[] mails = (new PrankGenerator()).generateMails(cm.getPersons(),1, cm.getPranks());
            for(Mail mail : mails){
                smtpClient.sendMail(mail);
            }

        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
