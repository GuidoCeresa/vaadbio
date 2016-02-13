package it.algos.vaadbio.attivita;

import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Attivita extends BaseEntity {

    @NotEmpty
    @Column(length = 100)
    @Index
    private String singolare = "";

    @NotEmpty
    @Column(length = 100)
    @Index
    private String plurale = "";

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Attivita() {
        this("", "");
    }// end of nullary constructor

    public Attivita(String singolare, String plurale) {
        super();
        this.setSingolare(singolare);
        this.setPlurale(plurale);
    }// end of general constructor

    /**
     * Recupera una istanza di Bolla usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Bolla, null se non trovata
     */
    public static Attivita find(long id) {
        Attivita instance = null;
        BaseEntity entity = AQuery.queryById(Attivita.class, id);

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Attivita usando la query di una property specifica
     *
     * @param singolare valore della property Singolare
     * @return istanza di Bolla, null se non trovata
     */
    public static Attivita findBySingolare(String singolare) {
        Attivita instance = null;
        BaseEntity entity = AQuery.queryOne(Attivita.class, Attivita_.singolare, singolare);

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Attivita usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @return istanza di Bolla, null se non trovata
     */
    public static Attivita findByPlurale(String plurale) {
        Attivita instance = null;
        BaseEntity entity = AQuery.queryOne(Attivita.class, Attivita_.plurale, plurale);

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Attivita.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera il valore del numero di records unici per plurale
     *
     * @return numero di records unici per plurale
     */
    public static int countDistinct() {
        String query = "select distinct attivita.plurale from Attivita attivita";
        return LibBio.queryCount(query);
    }// end of method


    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Attivita
     */
    @SuppressWarnings("unchecked")
    public  static ArrayList<Attivita> findAll() {
        return (ArrayList<Attivita>) AQuery.getLista(Attivita.class);
    }// end of method

    @Override
    public String toString() {
        return singolare;
    }// end of method

    public String getSingolare() {
        return singolare;
    }// end of getter method

    public void setSingolare(String singolare) {
        this.singolare = singolare;
    }//end of setter method

    public String getPlurale() {
        return plurale;
    }// end of getter method

    public void setPlurale(String plurale) {
        this.plurale = plurale;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Attivita con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Attivita clone() throws CloneNotSupportedException {
        try {
            return (Attivita) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
