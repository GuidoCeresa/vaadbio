package it.algos.vaadbio.cognome;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolena al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 * <p>
 * Considerata la quantità di records da elaborare ad ogni ciclo (giornaliero), procedo come segue:
 * Eseguo ogni settimana (nel giorno di 'manutenzione', magari la domenica) il metodo CognomeService.creaAggiorna
 * La prima volta 'crea' i nuovi record
 * Le volte succesive aggiunge nuovi records e segna come nonUsato/deprecato/nonValido quelli non più validi
 * Esegue ogni settimana il metodo Elabora (generale per Bio) per sincronizzare i puntaCognome
 * Esegue ogni settimana il metodo CognomeService.contaCognomi per sapere ogni cognome quante voci ha
 * Esegue ogni giorno il metodo CognomeService.uploadCognomi di quelli che superano una soglia prefissata
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
     * @param cognome della persona
     */
    public Cognome(String cognome) {
        this(cognome, 0);
    }// end of general constructor

    /**
     * Costruttore
     *
     * @param cognome della persona
     */
    public Cognome(String cognome, int voci) {
        this.setCognome(cognome);
        this.setVoci(voci);
    }// end of general constructor


    /**
     * Recupera una istanza di Cognome usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     *
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome find(long id) {
        return (Cognome) AQuery.find(Cognome.class, id);
    }// end of method

    /**
     * Recupera una istanza di Cognome usando la query di una property specifica
     *
     * @param cognome valore della property cognome
     *
     * @return istanza di Cognome, null se non trovata
     */
    public static Cognome getEntityByCognome(String cognome) {
        return (Cognome) AQuery.getEntity(Cognome.class, Cognome_.cognome, cognome);
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
     * Recupera una lista (array) parziale dei records
     *
     * @return lista di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static List<Cognome> getListSuperaTaglioPagina() {
        int taglio = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        return (List<Cognome>) AQuery.getList(Cognome.class, new SortProperty(Cognome_.cognome), getFiltroVoci(taglio));
    }// end of method

    public static Container.Filter getFiltroVoci(int maxVoci) {
        return new Compare.GreaterOrEqual(Cognome_.voci.getName(), maxVoci);
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
     * Recupera una mappa dei cognomi e della loro frequenza
     *
     * @return numero di biografie
     */
    public static Vector findMappa(EntityManager manager) {
        Vector vettore = null;
        Query query;
        String queryTxt = "select bio.cognome,count(bio.cognome) from Bio bio group by bio.cognome order by bio.cognome";

        try { // prova ad eseguire il codice
            query = manager.createQuery(queryTxt);
            vettore = (Vector) query.getResultList();
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        return vettore;
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<Cognome, Integer> findMappaTaglioPagina() {
        return findMappa(Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50));
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<Cognome, Integer> findMappaTaglioListe() {
        return findMappa(Pref.getInt(CostBio.TAGLIO_NOMI_ELENCO, 20));
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
     * Creazione iniziale di una istanza della Entity
     * Filtrato sulla company passata come parametro.
     * La crea SOLO se non esiste già
     *
     * @return istanza della Entity
     */
    public static Cognome crea(String cognomeTxt, int voci, EntityManager manager) {
        Cognome cognome = (Cognome) AQuery.getEntity(Cognome.class, Cognome_.cognome, cognomeTxt, manager);

        if (cognome == null) {
            try { // prova ad eseguire il codice
                cognome = new Cognome(cognomeTxt, voci);
                cognome = (Cognome) cognome.save(manager);
            } catch (Exception unErrore) { // intercetta l'errore
                cognome = null;
            }// fine del blocco try-catch
        }// end of if cycle

        return cognome;
    }// end of static method


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
     * Recupera una lista (array) di records Bio che usano questa istanza di Cognome nella property cognomePunta
     *
     * @return lista delle istanze di Bio che usano questo cognome
     */
    @SuppressWarnings("all")
    public List<Bio> listaBio() {
        Container.Filter filter = new Compare.Equal("cognome", getCognome());//todo provvisorio
        SortProperty sorts = new SortProperty(Bio_.attivita.getName(), Bio_.cognomeValido.getName(), Bio_.nomeValido.getName());

        return (List<Bio>) AQuery.getList(Bio.class, sorts, filter);
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
