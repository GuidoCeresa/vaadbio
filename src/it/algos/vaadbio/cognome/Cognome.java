package it.algos.vaadbio.cognome;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
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
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private boolean principale = true;

    @Index()
    private int voci = 0;

    @Index()
    private boolean valido = true;

    @Index()
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
        BaseEntity entity = AQuery.find(Cognome.class, id);

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
        BaseEntity entity = AQuery.getEntity(Cognome.class, Cognome_.cognome, cognome);

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
     * Recupera una lista (array) parziale dei records
     *
     * @return lista di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Cognome> findAllSuperaTaglioPagina() {
        ArrayList<Cognome> listaParziale = new ArrayList<>();
        List<? extends BaseEntity> listaCompleta = findAll();
        Cognome cognome;

        for (BaseEntity entity : listaCompleta) {
            if (entity instanceof Cognome) {
                cognome = (Cognome) entity;
                if (cognome.superaTaglioPagina()) {
                    listaParziale.add(cognome);
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle

        return listaParziale;
    }// end of method

    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public synchronized static int count() {
        int totRec = 0;
        long totTmp = AQuery.count(Cognome.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Controlla che il numero di records che usano questa istanza, superi il taglio previsto per la creazione della pagina
     *
     * @return vero se esistono più records del minimo previsto
     */
    @SuppressWarnings("all")
    public boolean superaTaglioPagina() {
        return superaTaglio(Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50));
    }// fine del metodo

    /**
     * Controlla che il numero di records che usano questa istanza, superi il taglio previsto
     *
     * @return vero se esistono più records del minimo previsto
     */
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

    public int getVoci() {
        return voci;
    }// end of getter method

    public void setVoci(int voci) {
        this.voci = voci;
    }//end of setter method

    public boolean isValido() {
        return valido;
    }// end of getter method

    public void setValido(boolean valido) {
        this.valido = valido;
    }//end of setter method

    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Cognome nella property cognomePunta
     *
     * @return filtro per i cognomi
     */
    private Container.Filter getFiltroCognome() {
//        return new Compare.Equal("cognomePunta.id", getId());
        return new Compare.Equal("cognomeValido", getCognome());//todo provvisorio
    }// fine del metodo

    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Cognome nella property cognomePunta
     *
     * @return lista delle istanze di Bio che usano questo cognome
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioCognome() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getList(Bio.class, this.getFiltroCognome());

        Comparator comp = new Comparator() {
            @Override
            public int compare(Object objA, Object objB) {
                String attivitaA = "";
                String attivitaB = "";
                Bio bioA = (Bio) objA;
                Bio bioB = (Bio) objB;
                Attivita objAttivitaA = bioA.getAttivitaPunta();
                Attivita objAttivitaB = bioB.getAttivitaPunta();
                if (objAttivitaA != null) {
                    attivitaA = objAttivitaA.getPlurale();
                }// end of if cycle
                if (objAttivitaB != null) {
                    attivitaB = objAttivitaB.getPlurale();
                }// end of if cycle

                return attivitaA.compareTo(attivitaB);
            }// end of inner method
        };// end of anonymous inner class
        entities.sort(comp);

        if (entities != null) {
            lista = new ArrayList<>(entities);
        }// end of if cycle

        return lista;
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
