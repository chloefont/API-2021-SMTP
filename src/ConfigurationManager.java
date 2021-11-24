import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager {
    String victimsFilename;
    String pranksFilenaame;

    BufferedReader victimReader;
    BufferedReader pranksReader;

    ConfigurationManager(String victimsFilename, String pranksFilenaame) throws IOException {
        this.victimsFilename = victimsFilename;
        this.pranksFilenaame = pranksFilenaame;

        File victimsFile = new File("victims.txt");
        victimsFile.createNewFile();
        File pranksFile = new File("pranks.txt");
        pranksFile.createNewFile();

        victimReader = new BufferedReader(new FileReader(victimsFile));
        pranksReader = new BufferedReader(new FileReader(pranksFile));

    }

    public Person[] getPersons(String filename) throws IOException {
        List<Person> list = new ArrayList<>();
        String line;

        while((line = victimReader.readLine()) != null){
            String[] infos = line.split(" ");
            list.add(new Person(infos[0], infos[1], infos[2]));
        }

        return list.toArray(new Person[0]);
    }

    public Prank[] getPranks(String filename) throws IOException {
        List<Prank> list = new ArrayList<>();
        String line;


        while((line = pranksReader.readLine()) != null) {
            String subject = line.substring("subject:".length());
            StringBuilder content = new StringBuilder();
            while ((line = pranksReader.readLine()).compareTo("==") != 0) {
                content.append(line).append("\n");
            }
            list.add(new Prank(subject, content.toString()));
        }

        return list.toArray(new Prank[0]);
    }
}
