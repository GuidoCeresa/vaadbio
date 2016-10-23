package it.algos.vaadbio.cognome;

import com.vaadin.data.Container;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolena al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Cognome extends BaseEntity {

    @NotEmpty
    @Index()
    private String cognome = "";

    /**
     */
    @Index()
    private boolean principale;

    @ManyToOne
    private Cognome riferimento;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Cognome() {
        this("");
    }// end of nullary constructor


    /**
     * Costruttore
     *
     * @param cognome della persona
     */
    public Cognome(String cognome) {
        this(cognome, true, null);
    }// end of general constructor

    /**
     * Costruttore completo
     *
     * @param cognome     della persona
     * @param principale  flag
     * @param riferimento al cognome che raggruppa le varie dizioni
     */
    public Cognome(String cognome, boolean principale, Cognome riferimento) {
        super();
        this.setCognome(cognome);
        this.setPrincipale(principale);
        this.setRiferimento(riferimento);
    }// end of full constructor

    /**
     * Recupera una istanza di Cognome usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome find(long id) {
        Cognome instance = null;
        BaseEntity entity = AQuery.queryById(Cognome.class, id);

        if (entity != null) {
            if (entity instanceof Cognome) {
                instance = (Cognome) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Cognome usando la query di una property specifica
     *
     * @param cognome valore della property cognome
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome findByCognome(String cognome) {
        Cognome instance = null;
        BaseEntity entity = AQuery.queryOne(Cognome.class, Cognome_.cognome, cognome);

        if (entity != null) {
            if (entity instanceof Cognome) {
                instance = (Cognome) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Cognome
     */
    @SuppressWarnings("unchecked")
    public synchronized static List<? extends BaseEntity> findAll() {
        return AQuery.getList(Cognome.class, new SortProperty(Cognome_.cognome.getName()), (Container.Filter) null);
    }// end of method

    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public synchronized static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Cognome.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method


    @Override
    public String toString() {
        return cognome;
    }// end of method

    public String getCognome() {
        return cognome;
    }// end of getter method

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }//end of setter method

    public boolean isPrincipale() {
        return principale;
    }// end of getter method

    public void setPrincipale(boolean principale) {
        this.principale = principale;
    }//end of setter method

    public Cognome getRiferimento() {
        return riferimento;
    }// end of getter method

    public void setRiferimento(Cognome riferimento) {
        this.riferimento = riferimento;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Cognome con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Cognome clone() throws CloneNotSupportedException {
        try {
            return (Cognome) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
