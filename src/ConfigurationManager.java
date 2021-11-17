public class ConfigurationManager {

    private String filename;

    ConfigurationManager(String filename){
        this.filename = filename;
    }

    public Person[] getPersons(){
        return new Person[0];
    }

    public String[] getPranks(){
        return new String[0];
    }
}
