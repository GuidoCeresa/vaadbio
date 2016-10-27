package it.algos.vaadbio.secolo;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolena al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Secolo extends BaseEntity {

    @NotEmpty
    @Column(length = 20)
    @Index
    private String titolo = "";

    @NotNull
    private int inizio;

    @NotNull
    private int fine;

    private boolean anteCristo;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Secolo() {
        this("", 0, 0, false);
    }// end of nullary constructor

    /**
     * Costruttore completo
     *
     * @param titolo     completo
     * @param inizio     anno di inizio del secolo
     * @param fine       anno di fine del secolo
     * @param anteCristo flag per distinguere gli anni prima e dopo cristo
     */
    public Secolo(String titolo, int inizio, int fine, boolean anteCristo) {
        super();
        this.setTitolo(titolo);
        this.setInizio(inizio);
        this.setFine(fine);
        this.setAnteCristo(anteCristo);
    }// end of general constructor

    /**
     * Recupera una istanza di Secolo usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Secolo, null se non trovata
     */
    public static Secolo find(long id) {
        Secolo instance = null;
        BaseEntity entity = AQuery.find(Secolo.class, id);

        if (entity != null) {
            if (entity instanceof Secolo) {
                instance = (Secolo) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Secolo usando la query di una property specifica
     *
     * @param titolo valore della property titolo
     * @return istanza di Secolo, null se non trovata
     */
    public static Secolo findByTitolo(String titolo) {
        Secolo instance = null;
        BaseEntity entity = AQuery.getEntity(Secolo.class, Secolo_.titolo, titolo);

        if (entity != null) {
            if (entity instanceof Secolo) {
                instance = (Secolo) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera il valore del numero totale di records della della Entity
     *
     * @return numero totale di records della tavola
     */
    public  static int count() {
        int totRec = 0;
        long totTmp = AQuery.count(Secolo.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Secolo
     */
    @SuppressWarnings("unchecked")
    public  static ArrayList<Secolo> findAll() {
        return (ArrayList<Secolo>) AQuery.getList(Secolo.class);
    }// end of method

    @Override
    public String toString() {
        return titolo;
    }// end of method

    public String getTitolo() {
        return titolo;
    }// end of getter method

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }//end of setter method

    public int getInizio() {
        return inizio;
    }// end of getter method

    public void setInizio(int inizio) {
        this.inizio = inizio;
    }//end of setter method

    public int getFine() {
        return fine;
    }// end of getter method

    public void setFine(int fine) {
        this.fine = fine;
    }//end of setter method

    public boolean isAnteCristo() {
        return anteCristo;
    }// end of getter method

    public void setAnteCristo(boolean anteCristo) {
        this.anteCristo = anteCristo;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Secolo con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Secolo clone() throws CloneNotSupportedException {
        try {
            return (Secolo) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
