package it.algos.vaadbio.delta;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Delta extends BaseEntity {

    @NotEmpty
    @Column(columnDefinition = "text")
    private String sorgente = "";

    @Column(columnDefinition = "text")
    private String valido = "";

    //-- ridondante, lo metto per evitare di costruire una colonna 'calcolata'
    @Column(columnDefinition = "text")
    private String differenza = "";

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Delta() {
        this("", "", "");
    }// end of nullary constructor

    public Delta(String sorgente, String valido, String differenza) {
        super();
        this.setSorgente(sorgente);
        this.setValido(valido);
        this.setDifferenza(differenza);
    }// end of general constructor

    /**
     * Recupera una istanza di Delta usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Delta, null se non trovata
     */
    public static Delta find(long id) {
        Delta instance = null;
        BaseEntity entity = AQuery.queryById(Delta.class, id);

        if (entity != null) {
            if (entity instanceof Delta) {
                instance = (Delta) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Delta usando la query di una property specifica
     *
     * @param nome valore della property Nome
     * @return istanza di Delta, null se non trovata
     */
    public static Delta findBySorgente(String nome) {
        Delta instance = null;
        BaseEntity entity = AQuery.queryOne(Delta.class, Delta_.sorgente, nome);

        if (entity != null) {
            if (entity instanceof Delta) {
                instance = (Delta) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public synchronized static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Delta.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Delta
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Delta> findAll() {
        return (ArrayList<Delta>) AQuery.getLista(Delta.class);
    }// end of method

    @Override
    public String toString() {
        return sorgente;
    }// end of method

    public String getSorgente() {
        return sorgente;
    }// end of getter method

    public void setSorgente(String sorgente) {
        this.sorgente = sorgente;
    }//end of setter method

    public String getValido() {
        return valido;
    }// end of getter method

    public void setValido(String valido) {
        this.valido = valido;
    }//end of setter method

    public String getDifferenza() {
        return differenza;
    }// end of getter method

    public void setDifferenza(String differenza) {
        this.differenza = differenza;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Delta con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Delta clone() throws CloneNotSupportedException {
        try {
            return (Delta) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
