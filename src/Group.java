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
