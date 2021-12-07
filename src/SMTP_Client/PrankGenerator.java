package SMTP_Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class PrankGenerator {
    public PrankGenerator(){}

    /**
     * Permet de générer des groupes de personnes recevant le même mail.
     * @param persons La liste des personnes.
     * @param nbGroup Le nombre de groupe à créer.
     * @param pranks Les prank à envoyer.
     * @return Un tableau de mail.
     * @throws IOException
     */
    public Mail[] generateMails(Person[] persons, int nbGroup, Prank[] pranks) throws IOException {

        ArrayList<Person> personsList = new ArrayList<Person>(Arrays.asList(persons));

        Group[] groups = createGroups(nbGroup, personsList);

        return getMail(groups, pranks);
    }

    /**
     * Permet d'assigner un mail à un groupe.
     * @param groups la liste des groupes créé.
     * @param pranks la liste des pranks à assigner aux groupes.
     * @return
     * @throws IOException
     */
    private Mail[] getMail(Group[] groups, Prank[] pranks) throws IOException {
        List<Mail> mails = new ArrayList<>();

        //create mail
        for (Group g: groups) {
            String[] toEmails = new String[g.getReceivers().length];
            int i = 0;
            for (Person p : g.getReceivers()) {
                toEmails[i] = p.getEmail();
                i++;
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, pranks.length);
            Prank prank = pranks[randomNum];
            mails.add(new Mail(toEmails, g.getSender().getEmail(), prank.subject, prank.content ));
        }
        return mails.toArray(new Mail[0]);
    }

    /**
     * Permet de créer des groupes aléatoirement.
     * @param nbGroup Le nombre de groupes à créer.
     * @param personsList La liste des personnes.
     * @return Un tableau de groupes de personnes.
     */
    private Group[] createGroups(int nbGroup, ArrayList<Person> personsList) {
        Group[] groups = new Group[nbGroup];
        int groupSize = personsList.size() / nbGroup;
        int addToLastGroup = personsList.size() % nbGroup;

        for(int i = 0; i < nbGroup; i++){
            ArrayList<Person> group = new ArrayList<Person>();

            // Ajoute les personnes aux groupes (pour le dernier groupe prendre en compte les personnes restantes)
            for(int j = 0; j < (i == nbGroup -1 ? groupSize + addToLastGroup : groupSize); j++){
                int randomNum = ThreadLocalRandom.current().nextInt(0, personsList.size());
                group.add(personsList.get(randomNum));
                personsList.remove(randomNum);
            }

            try{
                groups[i] = new Group(group.get(0), group.subList(1, group.size()).toArray(new Person[0]));

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            group.clear();
        }
        return groups;
    }
}
