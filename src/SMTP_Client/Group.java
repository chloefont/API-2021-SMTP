package SMTP_Client;

import SMTP_Client.Person;

/**
 * Cette classe contient l'envoyeur d'un mail ainsi que les receveurs du mail.
 */
public class Group {
    private Person sender;
    private Person[] receivers;

    Group(Person sender, Person[] receivers) throws Exception {
        if(receivers.length == 0 || sender == null){
            throw new Exception("Il n'y a pas assez de gens dans le groupe");
        }

        this.receivers = receivers;
        this.sender = sender;
    }

    public Person getSender() {
        return sender;
    }

    public Person[] getReceivers() {
        return receivers;
    }
}
