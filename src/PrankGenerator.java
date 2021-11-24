import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class PrankGenerator {
    private String[] pranks;

    public Mail[] generateMails(Person[] persons, int nbGroup, String[] pranks){

        ArrayList<Person> personsList = new ArrayList<Person>(Arrays.asList(persons));

        // TODO

        int groupSize = persons.length / nbGroup;
        int addToLastGroup = persons.length % nbGroup;


        Group[] groups = createGroups(nbGroup, personsList, groupSize, addToLastGroup);

        Mail[] mails = getMail(groups);


        return mails;
    }

    private Mail[] getMail(Group[] groups) {
        ArrayList<Mail> mails = new ArrayList<>();

        //create mail
        for (Group g: groups) {
            String[] toEmails = new String[g.getReceivers().length];
            int i = 0;
            for (Person p : g.getReceivers()) {
                toEmails[i] = p.getEmail();
                i++;
            }
            //TODO envoyer un prank différent pour chaque groupe.
            mails.add(new Mail(toEmails, g.getSender().getEmail(), "Prank", "coucou" ));
        }
        return (Mail[]) mails.toArray();
    }

    private Group[] createGroups(int nbGroup, ArrayList<Person> personsList, int groupSize, int addToLastGroup) {
        Group[] groups = new Group[nbGroup];

        for(int i = 0; i < nbGroup; i++){
            ArrayList<Person> group = new ArrayList<Person>();

            for(int j = 0; j < (i == nbGroup -1 ? groupSize : groupSize + addToLastGroup); j++){
                int randomNum = ThreadLocalRandom.current().nextInt(0, personsList.size());
                group.add(personsList.get(randomNum));
                personsList.remove(randomNum);
            }

            try{
                groups[i] = new Group(group.get(0), (Person[]) group.subList(1, group.size() - 1).toArray());

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            group.clear();
        }
        return groups;
    }

    public String[] getPranks() {
        return pranks;
    }
}
