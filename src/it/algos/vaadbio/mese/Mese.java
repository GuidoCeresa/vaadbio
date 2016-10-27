package it.algos.vaadbio.mese;

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
public class Mese extends BaseEntity {

    @NotEmpty
    @Column(length = 3)
    @Index
    private String titoloBreve = "";

    @NotEmpty
    @Column(length = 10)
    @Index
    private String titoloLungo;

    @NotNull
    private int giorni;

    @NotNull
    private int giorniBisestili;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Mese() {
        this("", "", 0, 0);
    }// end of nullary constructor

    /**
     * Costruttore completo
     *
     * @param titoloBreve     tre caratteri
     * @param titoloLungo     completo
     * @param giorni          per l'anno normale
     * @param giorniBisestili per l'anno bisestile
     */
    Mese(String titoloBreve, String titoloLungo, int giorni, int giorniBisestili) {
        super();
        this.setTitoloBreve(titoloBreve);
        this.setTitoloLungo(titoloLungo);
        this.setGiorni(giorni);
        this.setGiorniBisestili(giorniBisestili);
    }// end of general constructor

    /**
     * Recupera una istanza di Mese usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Mese, null se non trovata
     */
    public static Mese find(long id) {
        Mese instance = null;
        BaseEntity entity = AQuery.find(Mese.class, id);

        if (entity != null) {
            if (entity instanceof Mese) {
                instance = (Mese) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Mese usando la query di una property specifica
     *
     * @param titoloBreve valore della property titoloBreve
     * @return istanza di Mese, null se non trovata
     */
    public static Mese findByTitoloBreve(String titoloBreve) {
        Mese instance = null;
        BaseEntity entity = AQuery.getEntity(Mese.class, Mese_.titoloBreve, titoloBreve);

        if (entity != null) {
            if (entity instanceof Mese) {
                instance = (Mese) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Mese usando la query di una property specifica
     *
     * @param titoloLungo valore della property titoloLungo
     * @return istanza di Mese, null se non trovata
     */
    public static Mese findByTitoloLungo(String titoloLungo) {
        Mese instance = null;
        BaseEntity entity = AQuery.getEntity(Mese.class, Mese_.titoloLungo, titoloLungo);

        if (entity != null) {
            if (entity instanceof Mese) {
                instance = (Mese) entity;
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
        long totTmp = AQuery.count(Mese.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Mese
     */
    @SuppressWarnings("unchecked")
    public  static ArrayList<Mese> findAll() {
        return (ArrayList<Mese>) AQuery.getList(Mese.class);
    }// end of method

    @Override
    public String toString() {
        return getTitoloLungo();
    }// end of method

    public String getTitoloBreve() {
        return titoloBreve;
    }// end of getter method

    public void setTitoloBreve(String titoloBreve) {
        this.titoloBreve = titoloBreve;
    }//end of setter method

    public String getTitoloLungo() {
        return titoloLungo;
    }// end of getter method

    public void setTitoloLungo(String titoloLungo) {
        this.titoloLungo = titoloLungo;
    }//end of setter method

    public int getGiorni() {
        return giorni;
    }// end of getter method

    public void setGiorni(int giorni) {
        this.giorni = giorni;
    }//end of setter method

    public int getGiorniBisestili() {
        return giorniBisestili;
    }// end of getter method

    public void setGiorniBisestili(int giorniBisestili) {
        this.giorniBisestili = giorniBisestili;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Mese con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Mese clone() throws CloneNotSupportedException {
        try {
            return (Mese) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
