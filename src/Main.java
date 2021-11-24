import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String serverHost = "smtp.mailtrap.io";
        int serverPort = 2525;

        SMTPClient smtpClient = new SMTPClient(serverHost, serverPort);
        String[] tos = {"luca.coduri@heig-vd.ch", "pouetpouet@gmail.com"};
        Mail mail = new Mail(tos, "chloe.fontaine@heig-vd.ch", "wesh", "jme demandais si sur l'avenue on se retrouverait.");

        //smtpClient.sendMail(mail);
        try{
            ConfigurationManager cm = new ConfigurationManager("victims.txt", "pranks.txt");
            //System.out.println(Arrays.toString(cm.getPersons()));
            //System.out.println(Arrays.toString(cm.getPranks()));
            System.out.println((Arrays.toString((new PrankGenerator()).generateMails(cm.getPersons(),2, cm.getPranks()))));

        }catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
