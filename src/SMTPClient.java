import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class SMTPClient {
    private String ip;
    private int port;
    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    public SMTPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean sendMail(Mail mail) {
        Socket socket = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;

        try {
            socket = new Socket(ip, port);

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            try {
                LOG.info("Connecting...");

                // EHLO
                checkIfRecieveCode(reader, "220");
                writer.write("EHLO example.com\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "250 ");

                // LOGIN
                writer.write("AUTH LOGIN\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "334 ");

                writer.write("N2YwNDdjM2FjYzcyZTY=\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "334 ");

                writer.write("NWQ4Njk0ODAxZWRiZjU=\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "235 ");

                LOG.info("Connection made");

                for (String to : mail.getTo()) {
                    // MAIL ADRESSES
                    writer.write("MAIL FROM:<" + mail.getFrom() + ">\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "250 ");

                    writer.write("RCPT TO: <" + to + ">\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "250 ");

                    // DATA
                    writer.write("DATA\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "354 ");

                    String message = "To: " + to + "\r\n" +
                            "From: " + mail.getFrom() + "\r\n" +
                            "Subject: " + mail.getSubject() + "\r\n\r\n" +
                            mail.getContent() + "\r\n\r\n" + "." + "\r\n";

                    writer.write(message);
                    writer.flush();
                    checkIfRecieveCode(reader, "250");
                }

                writer.write("quit\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "221 ");

            } catch (Error e) {
                System.out.println(e);
            } finally {
                writer.close();;
                reader.close();
                socket.close();
            }



        } catch (UnknownHostException e) {
            System.out.println("Error while connecting : " + e);
        } catch (IOException e) {
            System.out.println("Error while connecting : " + e);
        }

        LOG.info("All mails sent");
        return true;
    }

    private void checkIfRecieveCode(BufferedReader reader, String code) throws IOException, Error {
        String line;
        while((line = reader.readLine()) != null) {
            if (line.contains(code)) {
                return;
            }
            // Raise exception if status code is 4XX or 5XX
            Pattern p = Pattern.compile("(5|4)[0-9]{2}.*");
            Matcher m = p.matcher(line);
            if(m.matches())
                throw new Error(line);
        }

        throw new Error("Status code wasn't found");
    }
}
