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

        String login = null;
        String password = null;

        if(args.length > 2){
            login = args[2];
            password = args[3];
        }

        SMTPClient smtpClient = login == null ?
                new SMTPClient(serverHost, serverPort):
                new SMTPClient(serverHost, serverPort, login, password);


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
