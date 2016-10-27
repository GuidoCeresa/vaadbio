package it.algos.vaadbio.professione;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

/**
 * Classe di tipo JavaBean.
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolena al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Professione extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(length = 100)
    @Index
    private String singolare = "";

    @NotEmpty
    @Column(length = 100)
    @Index
    private String pagina = "";

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Professione() {
        this("", "");
    }// end of constructor

    /**
     * Costruttore completo
     *
     * @param singolare maschiele o femminile della professione
     * @param pagina    di riferimento su wiki
     */
    public Professione(String singolare, String pagina) {
        super();
        this.setSingolare(singolare);
        this.setPagina(pagina);
    }// end of general constructor

    /**
     * Recupera una istanza di Professione usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Professione, null se non trovata
     */
    public static Professione find(long id) {
        Professione instance = null;
        BaseEntity entity = AQuery.find(Professione.class, id);

        if (entity != null) {
            if (entity instanceof Professione) {
                instance = (Professione) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Professione usando la query di una property specifica
     *
     * @param singolare maschile o femminile della professione
     * @return istanza di Professione, null se non trovata
     */
    public static Professione findBySingolare(String singolare) {
        Professione instance = null;
        BaseEntity entity = AQuery.getEntity(Professione.class, Professione_.singolare, singolare);

        if (entity != null) {
            if (entity instanceof Professione) {
                instance = (Professione) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Professione usando la query di una property specifica
     *
     * @param pagina di riferimento su wiki
     * @return istanza di Professione, null se non trovata
     */
    public static Professione findByPagina(String pagina) {
        Professione instance = null;
        BaseEntity entity = AQuery.getEntity(Professione.class, Professione_.pagina, pagina);

        if (entity != null) {
            if (entity instanceof Professione) {
                instance = (Professione) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Professione usando la query di una property specifica
     *
     * @param attivita della professione
     * @return istanza di Professione, null se non trovata
     */
    public static Professione findByAttivita(Attivita attivita) {
        Professione instance = null;
        String singolare;

        if (attivita != null) {
            singolare = attivita.getSingolare();
            instance = findBySingolare(singolare);
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
        long totTmp = AQuery.count(Professione.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Professione
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Professione> findAll() {
        return (ArrayList<Professione>) AQuery.getList(Professione.class);
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

    public String getPagina() {
        return pagina;
    }// end of getter method

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Professione con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Professione clone() throws CloneNotSupportedException {
        try {
            return (Professione) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
