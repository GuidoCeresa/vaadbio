package it.algos.vaadbio.attivita;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.Bio_;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.vaadbio.nazionalita.Nazionalita_;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.lib.LibTime;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.query.SortProperty;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
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
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Attivita extends BaseEntity {

    @NotEmpty
    @Column(length = 100)
    @Index
    private String singolare = "";

    @NotEmpty
    @Column(length = 100)
    @Index
    private String plurale = "";

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Attivita() {
        this("", "");
    }// end of nullary constructor

    public Attivita(String singolare, String plurale) {
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
    public static Attivita find(long id) {
        Attivita instance = null;
        BaseEntity entity = AQuery.find(Attivita.class, id);

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Attivita usando la query di una property specifica
     *
     * @param singolare valore della property Singolare
     * @return istanza di Bolla, null se non trovata
     */
    public static Attivita findBySingolare(String singolare) {
        Attivita instance = null;
        BaseEntity entity = AQuery.getEntity(Attivita.class, Attivita_.singolare, singolare);

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Attivita usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @return istanza di Attivita, null se non trovata
     */
    public static Attivita findByPlurale(String plurale) {
        Attivita instance = null;
        BaseEntity entity = AQuery.getEntity(Attivita.class, Attivita_.plurale, plurale.toLowerCase());

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera la prima istanza di Attivita usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @return istanza di Attivita, null se non trovata
     */
    public static Attivita findByFirstPlurale(String plurale) {
        Attivita instance = null;
        BaseEntity entity = AQuery.findFirst(Attivita.class, Attivita_.plurale, plurale.toLowerCase());

        if (entity != null) {
            if (entity instanceof Attivita) {
                instance = (Attivita) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una lista di Attivita (id) usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @return lista di ID delle attività che hanno la property plurale indicata
     */
    public static ArrayList<Long> findAllSingolari(String plurale) {
        if (plurale != null) {
            return LibBio.queryFind("select att.id from Attivita att where att.plurale = '" + plurale.toLowerCase() + "' order by att.id");
        } else {
            return null;
        }// end of if/else cycle
    }// end of method

    /**
     * Recupera una lista di Attivita (id) usando la query di una property specifica
     *
     * @param plurale valore della property plurale
     * @param sesso   delle persone (solo M o F)
     * @return lista di ID delle attività che hanno la property plurale indicata
     */
    public static ArrayList<Long> findAllSingolari(String plurale, String sesso) {
        ArrayList<Long> lista = null;
        ArrayList<String> listaGenere;
        Attivita attivita;
        String queryGen = CostBio.VUOTO;

        if (plurale == null || plurale.equals(CostBio.VUOTO)) {
            return null;
        }// end of if cycle
        if (sesso == null || sesso.equals(CostBio.VUOTO)) {
            return null;
        }// end of if cycle
        if (!sesso.equals("M") && !sesso.equals("F")) {
            return null;
        }// end of if cycle

        queryGen += "select gen.singolare from Genere gen where gen.plurale = '";
        queryGen += plurale.toLowerCase();
        queryGen += "' and gen.sesso = '";
        queryGen += sesso;
        queryGen += "' order by gen.singolare asc";
        listaGenere = LibBio.queryFindTxt(queryGen);

        if (listaGenere != null && listaGenere.size() > 0) {
            lista = new ArrayList<>();
            for (String singolare : listaGenere) {
                attivita = Attivita.findBySingolare(singolare);
                if (attivita != null) {
                    lista.add(attivita.getId());
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Recupera una mappa con occorrenze dei records che rispettano il criterio
     *
     * @return mappa di alcune istanze
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<Attivita, ArrayList> findMappaTaglioListe() {
        return findMappa((EntityManager) null, Pref.getInt(CostBio.TAGLIO_LISTE_ELENCO, 20));
    }// end of method

    /**
     * Recupera una mappa completa (ordinata) dei nomi e della loro frequenza
     *
     * @return mappa di alcune istanze
     */
    static LinkedHashMap<Attivita, ArrayList> findMappa(EntityManager manager, int taglio) {
        return LibBio.findMappaAtt(findVettoreBase(manager), taglio);
    }// end of method

    /**
     * Recupera una mappa completa dei nomi e della loro frequenza
     *
     * @return mappa di tutte le istanze
     */
    private static Vector findVettoreBase(EntityManager manager) {
        Vector vettore = null;
        Query query;
        String queryTxt = "select bio.attivitaPunta,count(bio.attivitaPunta),bio.attivita2Punta,count(bio.attivita2Punta) from Bio bio group by bio.attivitaPunta,bio.attivita2Punta order by bio.attivitaValida";

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
     * Recupera il valore del numero totale di records della Domain Class
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        int totRec = 0;
        long totTmp = AQuery.count(Attivita.class);

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
    public static int countDistinct() {
        String query = "select distinct attivita.plurale from Attivita attivita";
        return LibBio.queryCount(query);
    }// end of method


    /**
     * Recupera una lista (array) del plurale di records distinti di Attivita
     *
     * @return lista dei valori distinti di plurale
     */
    public static ArrayList<String> findDistinctPlurale() {
        return LibBio.queryFindDistinctStx("Attivita", "plurale");
    }// end of method


    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Attivita
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Attivita> findAll() {
        return (ArrayList<Attivita>) AQuery.getList(Attivita.class);
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Attivita
     */
    @SuppressWarnings("unchecked")
    public static List<Attivita> findAllDistinct() {
        return (List<Attivita>) LibBio.getDistinct(Attivita.class, "plurale");
    }// end of method

    /**
     * Recupera una lista (array) dei records con la property indicata
     *
     * @return lista di tutte le istanze di Attivita che rispondo al requisito
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Attivita> findAllByPlurale(String plurale) {
        ArrayList<Attivita> lista = null;
        Container.Filter filtro;
        List<? extends BaseEntity> entities = null;

        filtro = new Compare.Equal(Attivita_.plurale.getName(), plurale);
        entities = AQuery.getList(Attivita.class, filtro);

        if (entities != null) {
            lista = new ArrayList(entities);
        }// end of if cycle

        return lista;
    }// end of method

    /**
     * Recupera una lista (array) di singolari relativi al plurale di questa attivita
     * Cioè i noimi di tutte le attività che hanno questo plurale
     *
     * @return lista delle istanze di Attivita
     */
    @SuppressWarnings("unchecked")
    public List<String> getListSingolari() {
        Container.Filter filter = new Compare.Equal(Attivita_.plurale.getName(), plurale);
        return AQuery.getListStr(Attivita.class, Attivita_.singolare, filter);
    }// end of method


    /**
     * Recupera il valore del numero di records di Bio che usano questa attivita
     * Vengono analizzate tutte le 3 attività
     *
     * @return numero di records che usano questa attivita
     */
    public int countBio() {
        int numRecords = 0;
        Container.Filter filtro;

        filtro = new Compare.Equal("attivitaPunta", this);
        numRecords += AQuery.count(Bio.class, filtro);

        filtro = new Compare.Equal("attivita2Punta", this);
        numRecords += AQuery.count(Bio.class, filtro);

        filtro = new Compare.Equal("attivita3Punta", this);
        numRecords += AQuery.count(Bio.class, filtro);

        return numRecords;
    }// fine del metodo


    /**
     * Recupera il valore del numero di records di Bio che usano questa attivita
     *
     * @return numero di records che usano questa attivita
     */
    public int countBioAttUno() {
        return AQuery.count(Bio.class, new Compare.Equal("attivitaPunta", this));
    }// fine del metodo

    /**
     * Recupera il valore del numero di records di Bio che usano questa attivita
     *
     * @return numero di records che usano questa attivita
     */
    public int countBioAttDue() {
        return AQuery.count(Bio.class, new Compare.Equal("attivita2Punta", this));
    }// fine del metodo

    /**
     * Recupera il valore del numero di records di Bio che usano questa attivita
     *
     * @return numero di records che usano questa attivita
     */
    public int countBioAttTre() {
        return AQuery.count(Bio.class, new Compare.Equal("attivita3Punta", this));
    }// fine del metodo

    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Attività nella property attivitaValida
     *
     * @return lista delle istanze di Bio che usano questa attività
     */
    @SuppressWarnings("all")
    public List<Bio> bio() {
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> orderList = new ArrayList<>();
        String tag = "'";

        for (String singolare : getListSingolari()) {

            if (singolare.contains(tag)) {
                singolare = singolare.replaceAll(tag, tag + tag);
            }// end of if cycle

            whereList.add("attivitaValida=" + LibBio.setApici(singolare));
            whereList.add("attivita2Valida=" + LibBio.setApici(singolare));
            whereList.add("attivita3Valida=" + LibBio.setApici(singolare));
        }// end of for cycle

//        orderList.add("nazionalitaValida");
        orderList.add("cognomeValido");
        orderList.add("nomeValido");

        return (List<Bio>) LibBio.getList(Bio.class, whereList, orderList);
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
     * @return nuova istanza di Attivita con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Attivita clone() throws CloneNotSupportedException {
        try {
            return (Attivita) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
