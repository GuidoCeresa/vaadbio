package it.algos.vaadbio.giorno;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.mese.Mese;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
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
public class Giorno extends BaseEntity {

    @NotEmpty
    @Column(length = 20)
    @Index
    private String titolo = "";

    @NotNull
    @Index
    private int ordinamento = 0;

    @NotNull
    @ManyToOne
    private Mese mese = null;


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Giorno() {
        this("", 0, null);
    }// end of nullary constructor


    /**
     * Costruttore completo
     *
     * @param titolo      del giorno
     * @param ordinamento nelle categorie
     * @param mese        del giorno
     */
    public Giorno(String titolo, int ordinamento, Mese mese) {
        super();
        this.setTitolo(titolo);
        this.setOrdinamento(ordinamento);
        this.setMese(mese);
    }// end of general constructor

    /**
     * Recupera una istanza di Giorno usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Giorno, null se non trovata
     */
    public  static Giorno find(long id) {
        Giorno instance = null;
        BaseEntity entity = AQuery.queryById(Giorno.class, id);

        if (entity != null) {
            if (entity instanceof Giorno) {
                instance = (Giorno) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Giorno usando la query della property più probabile
     *
     * @return istanza di Anno, null se non trovata
     */
    public  static Giorno find(String titolo) {
        return findByTitolo(titolo);
    }// end of method

    /**
     * Recupera una istanza di Giorno usando la query di una property specifica
     *
     * @param titolo valore della property Titolo
     * @return istanza di Giorno, null se non trovata
     */
    public  static Giorno findByTitolo(String titolo) {
        Giorno instance = null;
        BaseEntity entity = AQuery.queryOne(Giorno.class, Giorno_.titolo, titolo);

        if (entity != null) {
            if (entity instanceof Giorno) {
                instance = (Giorno) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method


    /**
     * Recupera il valore del numero totale di records della Entity
     *
     * @return numero totale di records della tavola
     */
    public  static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Giorno.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method


    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Giorno
     */
    @SuppressWarnings("unchecked")
    public  static ArrayList<Giorno> findAll() {
        return (ArrayList<Giorno>) AQuery.getLista(Giorno.class);
    }// end of method


    public static String fix(String testoIn) {
        String testoOut = testoIn;
        String tag = CostBio.PRIMO_GIORNO_MESE;
        String tagA = "1°";
        String tagB = "1º";
        String tagC = "1&ordm;";
        String tagD = "1&nbsp;";
        String tagE = "1&deg;";
        String tagF = "1 ";

        if (testoIn != null && !testoIn.equals("")) {
            testoOut = testoOut.trim();
            if (testoOut.startsWith(tagA)) {
                testoOut = testoOut.replace(tagA, tag);
            }// fine del blocco if
            if (testoOut.startsWith(tagB)) {
                testoOut = testoOut.replace(tagB, tag);
            }// fine del blocco if
            if (testoOut.startsWith(tagC)) {
                testoOut = testoOut.replace(tagC, tag);
            }// fine del blocco if
            if (testoOut.startsWith(tagD)) {
                testoOut = testoOut.replace(tagD, tag);
            }// fine del blocco if
            if (testoOut.startsWith(tagE)) {
                testoOut = testoOut.replace(tagE, tag);
            }// fine del blocco if
            if (testoOut.startsWith(tagF)) {
                testoOut = testoOut.replace(tagF, tag + " ");
            }// fine del blocco if
        }// fine del blocco if

        return testoOut;
    } // fine del metodo


    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Giorno nella property giornoNatoPunta
     *
     * @return lista delle istanze di Bio che usano questo giorno
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioNati() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getLista(Bio.class, this.getFiltroNati());

        Comparator comp = new Comparator() {
            @Override
            public int compare(Object objA, Object objB) {
                Anno anno;
                int primo = 0;
                int ultimo = 0;
                Bio bioA = null;
                Bio bioB = null;
                if (objA instanceof Bio && objB instanceof Bio) {
                    bioA = (Bio) objA;
                    bioB = (Bio) objB;
                }// end of if cycle
                if (bioA.getAnnoNatoPunta() != null) {
                    anno = bioA.getAnnoNatoPunta();
                    if (anno != null) {
                        primo = anno.getOrdinamento();
                    }// end of if cycle
                }// end of if cyclewikipedia


                if (bioB.getAnnoNatoPunta() != null) {
                    anno = bioB.getAnnoNatoPunta();
                    if (anno != null) {
                        ultimo = anno.getOrdinamento();
                    }// end of if cycle
                }// end of if cycle

                if (primo == ultimo) {
                    return (bioA.getCognome().toLowerCase()).compareTo(bioB.getCognome().toLowerCase());
                } else {
                    return primo - ultimo;
                }// end of if/else cycle
            }
        };
        entities.sort(comp);

        if (entities != null) {
            lista = new ArrayList<>(entities);
        }// end of if cycle

        return lista;
    }// fine del metodo

    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Giorno nella property giornoMortoPunta
     *
     * @return lista delle istanze di Bio
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioMorti() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getList(Bio.class, this.getFiltroMorti());

        Comparator comp = new Comparator() {
            @Override
            public int compare(Object objA, Object objB) {
                Anno anno;
                int primo = 0;
                int ultimo = 0;
                Bio bioA = null;
                Bio bioB = null;
                if (objA instanceof Bio && objB instanceof Bio) {
                    bioA = (Bio) objA;
                    bioB = (Bio) objB;
                }// end of if cycle
                if (bioA.getAnnoMortoPunta() != null) {
                    anno = bioA.getAnnoMortoPunta();
                    if (anno != null) {
                        primo = anno.getOrdinamento();
                    }// end of if cycle
                }// end of if cycle
                if (bioB.getAnnoMortoPunta() != null) {
                    anno = bioB.getAnnoMortoPunta();
                    if (anno != null) {
                        ultimo = anno.getOrdinamento();
                    }// end of if cycle
                }// end of if cycle

                if (primo == ultimo) {
                    return (bioA.getCognome().toLowerCase()).compareTo(bioB.getCognome().toLowerCase());
                } else {
                    return primo - ultimo;
                }// end of if/else cycle
            }
        };
        entities.sort(comp);

        if (entities != null) {
            lista = new ArrayList<>(entities);
        }// end of if cycle

        return lista;
    }// fine del metodo


    /**
     * Recupera il valore del numero di records di Bio che usano questa istanza di Anno nella property giornoNatoPunta
     *
     * @return numero di istanze di Bio che usano questo giorno
     */
    public int countBioNati() {
//        return (int) AQuery.getCount(Bio.class, "giornoNatoPunta", this);
        int numRecords = 0;
        ArrayList<Bio> lista = bioNati();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo


    /**
     * Recupera il valore del numero di records di Bio che usano questa istanza di Giorno nella property giornoMortoPunta
     *
     * @return numero di istanze di Bio che usano questo giorno
     */
    public int countBioMorti() {
//        return (int) AQuery.getCount(Anno.class, "giornoMortoPunta", this);
        int numRecords = 0;
        ArrayList<Bio> lista = bioMorti();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo


    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Giorno usa nella property giornoNatoPunta
     *
     * @return filtro per i nati in questo giorno
     */
    private Container.Filter getFiltroNati() {
//        return new Compare.Equal("giornoNatoPunta", this);
        return new Compare.Equal("giornoMeseNascitaValido", titolo);
    }// fine del metodo


    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Giorno usa nella property giornoMortoPunta
     *
     * @return filtro per i morti in questo giorno
     */
    private Container.Filter getFiltroMorti() {
//        return new Compare.Equal("giornoMortoPunta", this);
        return new Compare.Equal("giornoMeseMorteValido", this);
    }// fine del metodo


    /**
     * Titolo della pagina Nati/Morti da creare/caricare su wikipedia
     */
    public String getTitoloLista(String tag) {
        String titoloLista = CostBio.VUOTO;
        String articolo = "il";
        String articoloBis = "l'";

        if (!titolo.equals("")) {
            if (titolo.startsWith("8") || titolo.startsWith("11")) {
                titoloLista = tag + articoloBis + titolo;
            } else {
                titoloLista = tag + articolo + CostBio.SPAZIO + titolo;
            }// fine del blocco if-else
        }// fine del blocco if

        return titoloLista;
    }// fine del metodo

    @Override
    public String toString() {
        return titolo;
    }// end of method

    public String getTitolo() {
        return titolo;
    }// end of getter method

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }//end of setter method

    public int getOrdinamento() {
        return ordinamento;
    }// end of getter method

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
    }//end of setter method

    public Mese getMese() {
        return mese;
    }// end of getter method

    public void setMese(Mese mese) {
        this.mese = mese;
    }//end of setter method

    /**
     * Clone di questa istanza
     * Una DIVERSA istanza (indirizzo di memoria) con gi STESSI valori (property)
     * È obbligatoria invocare questo metodo all'interno di un codice try/catch
     *
     * @return nuova istanza di Giorno con gli stessi valori dei parametri di questa istanza
     */
    @Override
    @SuppressWarnings("all")
    public Giorno clone() throws CloneNotSupportedException {
        try {
            return (Giorno) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }// fine del blocco try-catch
    }// end of method

}// end of domain class
