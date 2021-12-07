import SMTP_Client.ConfigurationManager;
import SMTP_Client.Mail;
import SMTP_Client.PrankGenerator;
import SMTP_Client.SMTPClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println("Please enter an IP and a Port.");
            return;
        }

        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int nbGroups = Integer.parseInt(args[2]);
        String login = null;
        String password = null;

        if(args.length > 3){
            login = args[3];
            password = args[4];
        }

        SMTPClient smtpClient = login == null ?
                new SMTPClient(serverHost, serverPort):
                new SMTPClient(serverHost, serverPort, login, password);


        try{
            ConfigurationManager cm = new ConfigurationManager("victims.txt", "pranks.txt");
            Mail[] mails = (new PrankGenerator()).generateMails(cm.getPersons(),nbGroups, cm.getPranks());
            for(Mail mail : mails){
                smtpClient.sendMail(mail);
            }

        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
