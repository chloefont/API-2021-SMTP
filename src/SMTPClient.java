import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class SMTPClient {
    private String ip;
    private int port;

    public SMTPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean sendMail(Mail mail) {
        Socket socket = null;

        try {
            socket = new Socket(ip, port);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // EHLO
            checkIfRecieveCode(reader, "220");
            writer.write("EHLO example.com\n");
            writer.flush();
            checkIfRecieveCode(reader, "250 ");

            // LOGIN
            writer.write("AUTH LOGIN\r\n");
            writer.flush();
            checkIfRecieveCode(reader, "334 ");

            writer.write("N2YwNDdjM2FjYzcyZTY=\n");
            writer.flush();
            checkIfRecieveCode(reader, "334 ");

            writer.write("NWQ4Njk0ODAxZWRiZjU=\n");
            writer.flush();
            checkIfRecieveCode(reader, "235 ");

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

                System.out.println(message);

                writer.write(message);
                writer.flush();
                checkIfRecieveCode(reader, "250");
            }

            writer.write("quit\r\n");
            writer.flush();
            checkIfRecieveCode(reader, "221 ");


        } catch (UnknownHostException e) {
            System.out.println("Error while connecting : " + e);
        } catch (IOException e) {
            System.out.println("Error while connecting : " + e);
        } catch (Error e) {
            System.out.println(e);
        }


        return true;
    }

    private void checkIfRecieveCode(BufferedReader reader, String code) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.contains(code)) {
                return;
            } else if (line.contains("40") || line.contains("50")) {
                // lever exception
            }
        }
    }
}
