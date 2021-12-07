package SMTP_Client;

import SMTP_Client.Person;
import SMTP_Client.Prank;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de récupérer les informations contenues dans les fichiers renseignés dans le constructeur.
 */
public class ConfigurationManager {
    String victimsFilename;
    String pranksFilenaame;

    BufferedReader victimReader;
    BufferedReader pranksReader;

    /**
     *
     * @param victimsFilename Le nom du fichier contenant les victimes.
     * @param pranksFilenaame Le nom du fichier contenant les pranks.
     * @throws IOException
     */
    public ConfigurationManager(String victimsFilename, String pranksFilenaame) throws IOException {
        this.victimsFilename = victimsFilename;
        this.pranksFilenaame = pranksFilenaame;

        File victimsFile = new File(victimsFilename);
        victimsFile.createNewFile();
        File pranksFile = new File(pranksFilenaame);
        pranksFile.createNewFile();

        victimReader = new BufferedReader(new InputStreamReader(new FileInputStream(victimsFile), StandardCharsets.UTF_8));
        pranksReader = new BufferedReader(new InputStreamReader(new FileInputStream(pranksFile), StandardCharsets.UTF_8));

    }

    /**
     * Récupérer les Personnes dans le fichier.
     * @return Un tableau de Personne.
     * @throws IOException
     */
    public Person[] getPersons() throws IOException {
        List<Person> list = new ArrayList<>();
        String line;

        while((line = victimReader.readLine()) != null){
            list.add(new Person(line));
        }

        return list.toArray(new Person[0]);
    }

    /**
     * Permet de récupérer les pranks dans le fichier.
     * @return Un tableau de Pranks.
     * @throws IOException
     */
    public Prank[] getPranks() throws IOException {
        List<Prank> list = new ArrayList<>();
        String line;


        while((line = pranksReader.readLine()) != null) {
            String subject = line.substring("subject:".length());
            StringBuilder content = new StringBuilder();
            while ((line = pranksReader.readLine()) != null && line.compareTo("==") != 0) {
                content.append(line).append("\n");
            }
            list.add(new Prank(subject, content.toString()));
        }

        return list.toArray(new Prank[0]);
    }
}
