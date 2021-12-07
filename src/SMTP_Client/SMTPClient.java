package SMTP_Client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

/**
 * Permet de d'envoyer un mail à l'aide du protocole SMTP
 */
public class SMTPClient {
    final private String ip;
    final private int port;
    String username = null;
    String password = null;
    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    /**
     * @param ip Adresse ip du serveur ou nom d'hote.
     * @param port port utilisé par le serveur.
     */
    public SMTPClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Ce constructeur prend en charge l'authentification.
     * @param ip Adresse ip du serveur ou nom d'hote.
     * @param port Port utilisé par le serveur.
     * @param username Nom d'utilisateur.
     * @param password Mot de passe.
     */
    public SMTPClient(String ip, int port, String username, String password) {
        this(ip, port);
        this.username = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
        this.password = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Permet d'envoyer un mail.
     * @param mail Objet contenant toutes les informations nécessaires pour l'envoi d'un mail.
     * @return Vrai si l'envoie à réussi.
     */
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
                if (username != null && password != null) {
                    writer.write("AUTH LOGIN\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "334 ");

                    writer.write(username + "\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "334 ");

                    writer.write( password + "\r\n");
                    writer.flush();
                    checkIfRecieveCode(reader, "235 ");
                }

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
                            "Content-Type: text/plain; charset=utf-8" + "\r\n" +
                            "Subject: " + "=?utf-8?B?" +
                            Base64.getEncoder().encodeToString(mail.getSubject().getBytes(StandardCharsets.UTF_8))
                            + "?=" + "\r\n\r\n" + mail.getContent() + "\r\n\r\n" + "." + "\r\n";

                    writer.write(message);
                    writer.flush();
                    checkIfRecieveCode(reader, "250");
                }

                writer.write("quit\r\n");
                writer.flush();
                checkIfRecieveCode(reader, "221 ");

                LOG.info("All mails sent");

            } catch (Error e) {
                LOG.log(Level.SEVERE, e.getMessage());
            } finally {
                writer.close();;
                reader.close();
                socket.close();
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error while connecting : " + e);
        }

        return true;
    }

    /**
     * Permet de vérifier si il y a une erreur durant les requêtes.
     * @param reader Les messages reçu du serveur.
     * @param code Le code à vérifier.
     * @throws IOException
     * @throws Error Dans le cas ou une erreur est trouvée une exception est levée.
     */
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
