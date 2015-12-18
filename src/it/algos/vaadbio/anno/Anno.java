package it.algos.vaadbio.anno;

import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

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
public class Anno extends BaseEntity {

    @NotEmpty
    private String nome = "";

    @NotEmpty
    private String secolo = "";

    @Index
    private int progressivo = 0;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Anno() {
        this("");
    }// end of nullary constructor

    public Anno(String nome) {
        super();
        this.setNome(nome);
    }// end of general constructor

    /**
     * Recupera una istanza di Bolla usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Bolla, null se non trovata
     */
    public static Anno find(long id) {
        Anno instance = null;
        BaseEntity entity = AQuery.queryById(Anno.class, id);

        if (entity != null) {
            if (entity instanceof Anno) {
                instance = (Anno) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Bolla usando la query di una property specifica
     *
     * @param nome valore della property Nome
     * @return istanza di Bolla, null se non trovata
     */
    public static Anno findByNome(String nome) {
        Anno instance = null;
        BaseEntity entity = AQuery.queryOne(Anno.class, Anno_.nome, nome);

        if (entity != null) {
            if (entity instanceof Anno) {
                instance = (Anno) entity;
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
        long totTmp = AQuery.getCount(Anno.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Anno
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Anno> findAll() {
        return (ArrayList<Anno>) AQuery.getList(Anno.class);
    }// end of method

    @Override
    public String toString() {
        return nome;
    }// end of method

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }// end of getter method

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }// end of setter method

    public String getSecolo() {
        return secolo;
    }// end of getter method

    public void setSecolo(String secolo) {
        this.secolo = secolo;
    }//end of setter method

    public int getProgressivo() {
        return progressivo;
    }// end of getter method

    public void setProgressivo(int progressivo) {
        this.progressivo = progressivo;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Anno con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Anno clone() throws CloneNotSupportedException {
        try {
            return (Anno) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
