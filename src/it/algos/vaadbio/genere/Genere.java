package it.algos.vaadbio.genere;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di tipo JavaBean.
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolena al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Genere extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(length = 100)
    @Index
    private String singolare = "";

    @NotEmpty
    @Column(length = 100)
    @Index
    private String plurale = "";

    @NotEmpty
    @Column(length = 1)
    @Index
    private String sesso = ""; // M o F

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Genere() {
        this("", "", "");
    }// end of constructor

    /**
     * Costruttore completo
     *
     * @param singolare dell'attivita
     * @param plurale   dell'attivita
     * @param sesso     dell'attivita
     */
    public Genere(String singolare, String plurale, String sesso) {
        super();
        this.setSingolare(singolare);
        this.setPlurale(plurale);
        this.setSesso(sesso);
    }// end of general constructor

    /**
     * Recupera una istanza di Genere usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Genere, null se non trovata
     */
    public static Genere find(long id) {
        Genere instance = null;
        BaseEntity entity = AQuery.find(Genere.class, id);

        if (entity != null) {
            if (entity instanceof Genere) {
                instance = (Genere) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Genere usando la query di una property specifica
     *
     * @param singolare valore della property
     * @return istanza di Genere, null se non trovata
     */
    public static Genere findBySingolare(String singolare) {
        Genere instance = null;
        BaseEntity entity = AQuery.getEntity(Genere.class, Genere_.singolare, singolare);

        if (entity != null) {
            if (entity instanceof Genere) {
                instance = (Genere) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Genere usando la query di due property
     *
     * @param singolare valore della property
     * @param sesso     valore della property
     * @return istanza di Genere, null se non trovata
     */
    public static Genere findBySingolareAndSesso(String singolare, String sesso) {
        Genere instance;
        List<? extends BaseEntity> entities = AQuery.getList(Genere.class, Genere_.singolare, singolare);

        if (entities != null) {
            for (BaseEntity entity : entities) {
                if (entity instanceof Genere) {
                    instance = (Genere) entity;
                    if (instance.getSesso().equals(sesso)) {
                        return instance;
                    }// end of if cycle
                }// end of if cycle
            }// end of for cycle
        } else {
            return null;
        }// end of if/else cycle
        return null;
    }// end of method


    /**
     * Recupera una istanza di Genere usando la query di una property specifica
     *
     * @param plurale valore della property Sigla
     * @return istanza di Genere, null se non trovata
     */
    public static Genere findByPlurale(String plurale) {
        Genere instance = null;
        BaseEntity entity = AQuery.getEntity(Genere.class, Genere_.plurale, plurale);

        if (entity != null) {
            if (entity instanceof Genere) {
                instance = (Genere) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera la prima istanza di Genere usando la query di una property specifica
     *
     * @param plurale valore della property Sigla
     * @return istanza di Genere, null se non trovata
     */
    public static Genere findByFirstPlurale(String plurale) {
        Genere instance = null;
        BaseEntity entity = AQuery.findFirst(Genere.class, Genere_.plurale, plurale);

        if (entity != null) {
            if (entity instanceof Genere) {
                instance = (Genere) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera il valore del numero totale di records della della Entity
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        int totRec = 0;
        long totTmp = AQuery.count(Genere.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Genere
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Genere> findAll() {
        return (ArrayList<Genere>) AQuery.getList(Genere.class);
    }// end of method

    @Override
    public String toString() {
        return singolare;
    }// end of method

    /**
     * @return the sigla
     */
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

    public String getSesso() {
        return sesso;
    }// end of getter method

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Genere con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Genere clone() throws CloneNotSupportedException {
        try {
            return (Genere) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
