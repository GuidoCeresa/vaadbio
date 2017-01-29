package it.algos.vaadbio.nome;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.vaadin.addon.jpacontainer.util.HibernateUtil;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.DefaultSort;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.sessions.Session;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.beans.Expression;
import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 * <p>
 * Di norma gli accenti vengono rispettati. Pertanto: María, Marià, Maria, Mária, Marìa, Mariâ sono nomi diversi
 * Di norma i nomi doppi vengono troncati
 */
@Entity
//@DefaultSort({"nome,true"})
public class Nome extends BaseEntity {


    @NotEmpty
    @Index()
    private String nome = "";

    @Index()
    private int voci = 0;


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Nome() {
    }// end of nullary constructor


    /**
     * Costruttore
     *
     * @param nomeTxt della persona
     */
    public Nome(String nomeTxt) {
        this(nomeTxt, 0);
    }// end of full constructor

    /**
     * Costruttore completo
     *
     * @param nomeTxt della persona
     * @param numVoci biografiche che hanno nomeValido = nomeTxt
     */
    public Nome(String nomeTxt, int numVoci) {
        super();
        this.setNome(nomeTxt);
        this.setVoci(numVoci);
    }// end of full constructor


    /**
     * Recupera una istanza di Nome usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Nome, null se non trovata
     */
    public static Nome find(long id) {
        return (Nome) AQuery.find(Nome.class, id);
    }// end of method

    /**
     * Recupera una istanza di Nome usando la query di una property specifica
     *
     * @param nomeTxt valore della property nome
     * @return istanza di Nome, null se non trovata
     */
    public static Nome getEntityByNome(String nomeTxt) {
        return (Nome) AQuery.getEntity(Nome.class, Nome_.nome, nomeTxt);
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static List<Nome> getList() {
        return (List<Nome>) AQuery.getList(Nome.class, new SortProperty(Nome_.nome.getName()));
    }// end of method


    /**
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        return AQuery.count(Nome.class);
    }// end of method

    /**
     * Creazione iniziale di una istanza
     * La crea SOLO se non esiste già
     *
     * @param nomeTxt della persona
     * @return istanza della classe
     */
    public static Nome crea(String nomeTxt) {
        return crea(nomeTxt, 0);
    }// end of static method


    /**
     * Creazione iniziale di una istanza
     * La crea SOLO se non esiste già
     *
     * @param nomeTxt della persona
     * @param numVoci biografiche che hanno nomeValido = nomeTxt
     * @return istanza della classe
     */
    public static Nome crea(String nomeTxt, int numVoci) {
        return crea(nomeTxt, numVoci, (EntityManager) null);
    }// end of static method

    /**
     * Creazione iniziale di una istanza della Entity
     * Filtrato sulla company passata come parametro.
     * La crea SOLO se non esiste già
     *
     * @param nomeTxt della persona
     * @param numVoci biografiche che hanno nomeValido = nomeTxt
     * @param manager the EntityManager to use
     * @return istanza della Entity
     */
    public static Nome crea(String nomeTxt, int numVoci, EntityManager manager) {
        Nome instance = (Nome) AQuery.getEntity(Nome.class, Nome_.nome, nomeTxt, manager);

        // se non specificato l'EntityManager, ne crea uno locale
        boolean usaManagerLocale = false;
        if (manager == null) {
            usaManagerLocale = true;
            manager = EM.createEntityManager();
        }// end of if cycle

        if (instance == null) {
            try { // prova ad eseguire il codice
                instance = new Nome(nomeTxt, numVoci);
                instance = (Nome) instance.save(manager);
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
     * Recupera una lista (array) di tutti i records di Nome doppi
     *
     * @return lista di tutte le istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Nome> findAllDoppi() {
        Container.Filter filtro = new Compare.Equal("nomeDoppio", true);
        return (ArrayList<Nome>) AQuery.getList(Nome.class, filtro);
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records di Nome NON doppi
     *
     * @return lista di tutte le istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Nome> findAllNotDoppi() {
        Container.Filter filtro = new Compare.Equal("nomeDoppio", false);
        return (ArrayList<Nome>) AQuery.getList(Nome.class, filtro);
    }// end of method

    /**
     * Recupera una lista (array) parziale dei records
     *
     * @return lista di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Nome> findAllSuperaTaglioPagina() {
        ArrayList<Nome> listaParziale = new ArrayList<>();
        List<? extends BaseEntity> listaCompleta = getList();
        Nome nome;

        for (BaseEntity entity : listaCompleta) {
            if (entity instanceof Nome) {
                nome = (Nome) entity;
                if (nome.superaTaglioPagina()) {
                    listaParziale.add(nome);
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle

        return listaParziale;
    }// end of method

    /**
     * Recupera una lista dei nomi che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<String> findListaTaglioPagina() {
        ArrayList<String> lista = new ArrayList<>();
        LinkedHashMap<String, Integer> mappa = findMappaTaglioPagina();

        for (String nomeTxt : mappa.keySet()) {
            lista.add(nomeTxt);
        }// end of for cycle

        return lista;
    }// end of method

    /**
     * Recupera una lista (array) parziale dei records
     *
     * @return lista di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static List<Nome> getListSuperaTaglioPagina() {
        int taglio = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        return (List<Nome>) AQuery.getList(Nome.class, new SortProperty(Nome_.nome), getFiltroVoci(taglio));
    }// end of method

    private static Container.Filter getFiltroVoci(int maxVoci) {
        return new Compare.GreaterOrEqual(Nome_.voci.getName(), maxVoci);
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Integer> findMappaTaglioPagina() {
        return findMappa((EntityManager) null, Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50));
    }// end of method

    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Integer> findMappaTaglioListe() {
        return findMappa((EntityManager) null, Pref.getInt(CostBio.TAGLIO_NOMI_ELENCO, 20));
    }// end of method

    /**
     * Recupera una mappa completa dei nomi e della loro frequenza
     *
     * @return mappa di tutte le istanze di Nome
     */
    private static Vector findVettoreBase(EntityManager manager) {
        Vector vettore = null;
        Query query;
        String queryTxt = "select bio.nomeValido,count(bio.nomeValido) from Bio bio group by bio.nomeValido order by bio.nomeValido";

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
     * Recupera una mappa completa (ordinata) dei nomi e della loro frequenza
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
    @Deprecated
    @SuppressWarnings("unchecked")
    private static LinkedHashMap<Nome, Integer> findMappa(int maxVoci) {
        LinkedHashMap<Nome, Integer> mappa = new LinkedHashMap<Nome, Integer>();
        long numRecords = 0;
        List<? extends BaseEntity> listaCompleta = getList();
        Nome nome;

        for (BaseEntity entity : listaCompleta) {
            if (entity instanceof Nome) {
                nome = (Nome) entity;
                numRecords = nome.countBioNome();
                if (numRecords >= maxVoci) {
                    mappa.put(nome, (int) numRecords);
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle

        return mappa;
    }// end of method

    /**
     * Removes this entity from the database using a local EntityManager
     * <p>
     * Cancella prima i records linkati qui:
     * nella tavola Bio
     */
    public boolean delete() {
        List<Bio> lista = this.listaBio();

        for (Bio bio : lista) {
            bio.setNomePunta(null);
            bio.save();
        }// end of for cycle

        return super.delete();
    }// end of method

    @Override
    public String toString() {
        return nome;
    }// end of method

    public String getNome() {
        return nome;
    }// end of getter method

    public void setNome(String nome) {
        this.nome = nome;
    }//end of setter method


    public int getVoci() {
        return voci;
    }// end of getter method

    public void setVoci(int voci) {
        this.voci = voci;
    }//end of setter method

    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Nome usa nella property nomePunta
     *
     * @return filtro per i nomi
     */
    private Container.Filter getFiltroNome() {
        return new Compare.Equal("nomePunta.id", getId());
    }// fine del metodo


    /**
     * Recupera il numero di records Bio che usano questa istanza di Nome nella property nomePunta
     *
     * @return numero di records di Bio che usano questo nome
     */
    @SuppressWarnings("all")
    public long countBioNome() {
        long numRecords = 0;
        ArrayList lista = null;
        String query = CostBio.VUOTO;
        String queryBase = "select count(bio.id) from Bio bio";
//        String queryWhere = " where bio.nomePunta.id=" + getId();
        String queryWhere = " where bio.nomeValido=" + "'" + nome + "'";

        query = queryBase + queryWhere;
        try { // prova ad eseguire il codice
            lista = LibBio.queryFind(query);
        } catch (Exception unErrore) { // intercetta l'errore
            int a = 87;
        }// fine del blocco try-catch

        if (lista != null && lista.size() == 1) {
            numRecords = (long) lista.get(0);
        }// end of if cycle

        return numRecords;
    }// fine del metodo

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
     * Controlla che il numero di records che usano questa istanza, superi il taglio previsto per l'inserimento nell'elenco
     *
     * @return vero se esistono più records del minimo previsto
     */
    @SuppressWarnings("all")
    public boolean superaTaglioElenco() {
        return superaTaglio(Pref.getInt(CostBio.TAGLIO_NOMI_ELENCO, 20));
    }// fine del metodo

    /**
     * Controlla che il numero di records che usano questa istanza, superi il taglio previsto
     *
     * @return vero se esistono più records del minimo previsto
     */
    @SuppressWarnings("all")
    private boolean superaTaglio(int maxVoci) {
        boolean status = false;
        long numRecords = countBioNome();

        if (numRecords >= maxVoci) {
            status = true;
        }// end of if cycle

        return status;
    }// fine del metodo


    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Nome nella property nomeValido
     * Non uso un link al record di questa tavola, perché viene ricreata ogno volta (numero di records ed ids variabili)
     *
     * @return lista delle istanze di Bio che usano questo istanza
     */
    @SuppressWarnings("all")
    public List<Bio> listaBio() {
        SortProperty sorts = new SortProperty(Bio_.cognomeValido.getName(), Bio_.nomeValido.getName());
        return (List<Bio>) AQuery.getList(Bio.class, Bio_.nomeValido, getNome(), sorts);
    }// fine del metodo


}// end of entity class
