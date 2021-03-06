package it.algos.vaadbio.cognome;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

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


    @Index()
    private int voci = 0;


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Cognome() {
    }// end of nullary constructor


    /**
     * Costruttore
     *
     * @param cognomeTxt della persona
     */
    public Cognome(String cognomeTxt) {
        this(cognomeTxt, 0);
    }// end of general constructor

    /**
     * Costruttore
     *
     * @param cognomeTxt della persona
     * @param numVoci    biografiche che hanno cognomeValido = cognomeTxt
     */
    public Cognome(String cognomeTxt, int numVoci) {
        super();
        this.setCognome(cognomeTxt);
        this.setVoci(numVoci);
    }// end of general constructor


    /**
     * Recupera una istanza di Cognome usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome find(long id) {
        return (Cognome) AQuery.find(Cognome.class, id);
    }// end of method

    /**
     * Recupera una istanza di Cognome usando la query di una property specifica
     *
     * @param cognomeTxt valore della property cognome
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome getEntityByCognome(String cognomeTxt) {
        return (Cognome) AQuery.getEntity(Cognome.class, Cognome_.cognome, cognomeTxt);
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Cognome
     */
    @SuppressWarnings("unchecked")
    public static List<Cognome> getList() {
        return (List<Cognome>) AQuery.getList(Cognome.class, new SortProperty(Cognome_.cognome.getName()));
    }// end of method


    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        return AQuery.count(Cognome.class);
    }// end of method

    /**
     * Creazione iniziale di una istanza della Entity
     * Filtrato sulla company passata come parametro.
     * La crea SOLO se non esiste già
     *
     * @param cognomeTxt della persona
     * @return istanza della Entity
     */
    public static Cognome crea(String cognomeTxt) {
        return crea(cognomeTxt, 0);
    }// end of static method


    /**
     * Creazione iniziale di una istanza della Entity
     * Filtrato sulla company passata come parametro.
     * La crea SOLO se non esiste già
     *
     * @param cognomeTxt della persona
     * @param numVoci    biografiche che hanno cognomeValido = cognomeTxt
     * @return istanza della Entity
     */
    public static Cognome crea(String cognomeTxt, int numVoci) {
        return crea(cognomeTxt, numVoci, (EntityManager) null);
    }// end of static method


    /**
     * Creazione iniziale di una istanza della Entity
     * Filtrato sulla company passata come parametro.
     * La crea SOLO se non esiste già
     *
     * @param cognomeTxt della persona
     * @param numVoci    biografiche che hanno cognomeValido = cognomeTxt
     * @param manager    the EntityManager to use
     * @return istanza della Entity
     */
    public static Cognome crea(String cognomeTxt, int numVoci, EntityManager manager) {
        Cognome instance = (Cognome) AQuery.getEntity(Cognome.class, Cognome_.cognome, cognomeTxt, manager);

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        if (instance == null) {
            try { // prova ad eseguire il codice
                instance = new Cognome(cognomeTxt, numVoci);
                instance = (Cognome) instance.save(manager);
            } catch (Exception unErrore) { // intercetta l'errore
                instance = null;
            }// fine del blocco try-catch
        }// end of if cycle

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return instance;
    }// end of static method


    /**
     * Recupera una lista (array) parziale dei records
     *
     * @return lista di alcune istanze
     */
    @SuppressWarnings("unchecked")
    public static List<Cognome> getListSuperaTaglioPagina() {
        int taglio = Pref.getInt(CostBio.TAGLIO_COGNOMI_PAGINA, 50);
        return (List<Cognome>) AQuery.getList(Cognome.class, getFiltroVoci(taglio));
    }// end of method

    private static Container.Filter getFiltroVoci(int maxVoci) {
        return new Compare.GreaterOrEqual(Cognome_.voci.getName(), maxVoci);
    }// end of method


    /**
     * Recupera una mappa completa dei cognomi e della loro frequenza
     *
     * @return mappa di tutte le istanze di Cognome
     */
    private static Vector findVettoreBase(EntityManager manager) {
        Vector vettore = null;
        Query query;
        String queryTxt = "select bio.cognomeValido,count(bio.cognomeValido) from Bio bio group by bio.cognomeValido order by bio.cognomeValido";

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        try { // prova ad eseguire il codice
            query = manager.createQuery(queryTxt);
            vettore = (Vector) query.getResultList();
        } catch (Exception unErrore) { // intercetta l'errore
            int a = 87;
        }// fine del blocco try-catch

        // eventualmente chiude l'EntityManager locale
        if (usaManagerLocale) {
            manager.close();
        }// end of if cycle

        return vettore;
    }// end of method

    /**
     * Recupera una mappa completa (ordinata) dei cognomi e della loro frequenza
     *
     * @return mappa sigla, numero di voci
     */
    static LinkedHashMap<String, Integer> findMappa(EntityManager manager, int taglio) {
        return LibBio.findMappa(findVettoreBase(manager), taglio);
    }// end of method


    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Integer> findMappaTaglioPagina() {
        return findMappa((EntityManager) null, Pref.getInt(CostBio.TAGLIO_COGNOMI_PAGINA, 50));
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Integer> findMappaTaglioListe() {
        return findMappa((EntityManager) null, Pref.getInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20));
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    private static LinkedHashMap<Cognome, Integer> findMappa(int maxVoci) {
        LinkedHashMap<Cognome, Integer> mappa = new LinkedHashMap<>();
        List<Cognome> lista = (List<Cognome>) AQuery.getList(Cognome.class, getFiltroVoci(maxVoci));

        for (Cognome cognome : lista) {
            mappa.put(cognome, cognome.getVoci());
        }// end of for cycle

        return mappa;
    }// end of method


    /**
     * Controlla che il numero di records che usano questa istanza, superi il taglio previsto
     *
     * @return vero se esistono più records del minimo previsto
     */
    @Deprecated
    @SuppressWarnings("all")
    private boolean superaTaglio(int maxVoci) {
        boolean status = false;
        int numRecords = countBioCognome();

        if (numRecords >= maxVoci) {
            status = true;
        }// end of if cycle

        return status;
    }// fine del metodo

    /**
     * Recupera il numero di records Bio che usano questa istanza di Cognome nella property cognomePunta
     *
     * @return numero di records di Bio che usano questo cognome
     */
    @Deprecated
    @SuppressWarnings("all")
    public int countBioCognome() {
        int numRecords = 0;
        ArrayList lista;
        String query = CostBio.VUOTO;
        String queryBase = "select count(bio.id) from Bio bio";
        String queryWhere = " where bio.cognomePunta.id=" + getId();

        query = queryBase + queryWhere;
        lista = LibBio.queryFind(query);

        if (lista != null && lista.size() == 1) {
            numRecords = (int) lista.get(0);
        }// end of if cycle

        return numRecords;
    }// fine del metodo

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

    public int getVoci() {
        return voci;
    }// end of getter method

    public void setVoci(int voci) {
        this.voci = voci;
    }//end of setter method


    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Cognome nella property cognomeValido
     * Non uso un link al record di questa tavola, perché viene ricreata ogno volta (numero di records ed ids variabili)
     *
     * @return lista delle istanze di Bio che usano questo istanza
     */
    @SuppressWarnings("all")
    public List<Bio> listaBio() {
        SortProperty sorts = new SortProperty(Bio_.cognomeValido.getName(), Bio_.nomeValido.getName());
        return (List<Bio>) AQuery.getList(Bio.class, Bio_.cognomeValido, getCognome(), sorts);
    }// fine del metodo

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
