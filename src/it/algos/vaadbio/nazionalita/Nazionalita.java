package it.algos.vaadbio.nazionalita;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Nazionalita extends BaseEntity {

    @NotEmpty
    @Index
    private String singolare = "";

    @NotEmpty
    private String plurale = "";

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Nazionalita() {
        this("", "");
    }// end of nullary constructor

    public Nazionalita(String singolare, String plurale) {
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
    public static Nazionalita find(long id) {
        Nazionalita instance = null;
        BaseEntity entity = AQuery.queryById(Nazionalita.class, id);

        if (entity != null) {
            if (entity instanceof Nazionalita) {
                instance = (Nazionalita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Nazionalita usando la query di una property specifica
     *
     * @param singolare valore della property Singolare
     * @return istanza di Bolla, null se non trovata
     */
    public static Nazionalita findBySingolare(String singolare) {
        Nazionalita instance = null;
        BaseEntity entity = AQuery.queryOne(Nazionalita.class, Nazionalita_.singolare, singolare);

        if (entity != null) {
            if (entity instanceof Nazionalita) {
                instance = (Nazionalita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Nazionalita usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @return istanza di Bolla, null se non trovata
     */
    public static Nazionalita findByPlurale(String plurale) {
        Nazionalita instance = null;
        BaseEntity entity = AQuery.queryOne(Nazionalita.class, Nazionalita_.plurale, plurale);

        if (entity != null) {
            if (entity instanceof Nazionalita) {
                instance = (Nazionalita) entity;
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
        long totTmp = AQuery.getCount(Nazionalita.class);

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
    public synchronized static int countDistinctPlurale() {
        return LibBio.queryCountDistinct("Nazionalita", "plurale");
    }// end of method


    /**
     * Recupera una lista (array) del plurale di records distinti di Nazionalità
     *
     * @return lista dei valori distinti di plurale
     */
    public synchronized static ArrayList<String> findDistinctPlurale() {
        return LibBio.queryFindDistinctStx("Nazionalita", "plurale");
    }// end of method


    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Nazionalita
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Nazionalita> findAll() {
        return (ArrayList<Nazionalita>) AQuery.getLista(Nazionalita.class);
    }// end of method

    /**
     * Recupera una lista (array) dei records con la property indicata
     *
     * @return lista di tutte le istanze di Nazionalita che rispondo al requisito
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Nazionalita> findAllByPlurale(String plurale) {
        ArrayList<Nazionalita> lista = null;
        Container.Filter filtro;
        List<? extends BaseEntity> entities = null;

        filtro = new Compare.Equal(Nazionalita_.plurale.getName(), plurale);
        entities = AQuery.getList(Nazionalita.class, filtro);

        if (entities != null) {
            lista = new ArrayList(entities);
        }// end of if cycle

        return lista;
    }// end of method

    /**
     * Recupera il valore del numero di records di Bio che usano questa nazionalità
     *
     * @return numero di records che usano questa nazionalità
     */
    public int countBio() {
        int numRecords = 0;
        List<? extends BaseEntity> entities = null;
        Container.Filter filtro;

        filtro = new Compare.Equal("nazionalitaPunta", this);
        entities = AQuery.getList(Bio.class, filtro);

        if (entities != null) {
            numRecords = entities.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo

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
     * @return nuova istanza di Nazionalita con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Nazionalita clone() throws CloneNotSupportedException {
        try {
            return (Nazionalita) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
