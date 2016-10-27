package it.algos.vaadbio.anno;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.secolo.Secolo;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.lib.SecoloEnum;
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
public class Anno extends BaseEntity {

    @NotEmpty
    @Column(length = 10)
    @Index
    private String titolo = "";

    @NotNull
    @Index
    private int ordinamento = 0;

    @NotNull
    @ManyToOne
    private Secolo secolo = null;


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Anno() {
        this("", 0, null);
    }// end of nullary constructor

    /**
     * Costruttore completo
     *
     * @param titolo      dell'anno
     * @param ordinamento nelle categorie
     * @param secolo      dell'anno
     */
    public Anno(String titolo, int ordinamento, Secolo secolo) {
        super();
        this.setTitolo(titolo);
        this.setOrdinamento(ordinamento);
        this.setSecolo(secolo);
    }// end of general constructor

    /**
     * Recupera una istanza di Anno usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Anno, null se non trovata
     */
    public static Anno find(long id) {
        Anno instance = null;
        BaseEntity entity = AQuery.find(Anno.class, id);

        if (entity != null) {
            if (entity instanceof Anno) {
                instance = (Anno) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method


    /**
     * Recupera una istanza di Anno usando la query della property più probabile
     *
     * @return istanza di Anno, null se non trovata
     */
    public static Anno find(String titolo) {
        return findByTitolo(titolo);
    }// end of method


    /**
     * Recupera una istanza di Anno usando la query di una property specifica
     *
     * @param titolo valore della property Titolo
     * @return istanza di Anno, null se non trovata
     */
    public static Anno findByTitolo(String titolo) {
        Anno instance = null;
        BaseEntity entity = AQuery.getEntity(Anno.class, Anno_.titolo, titolo);

        if (entity != null) {
            if (entity instanceof Anno) {
                instance = (Anno) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method


    /**
     * Recupera il valore del numero totale di records della Entity
     *
     * @return numero totale di records della tavola
     */
    public static int count() {
        int totRec = 0;
        long totTmp = AQuery.count(Anno.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method


    /**
     * Recupera una lista (array) di tutti i records della Entity
     *
     * @return lista di tutte le istanze di Anno
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Anno> findAll() {
        return (ArrayList<Anno>) AQuery.getList(Anno.class);
    }// end of method


    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Anno nella property annoNatoPunta
     *
     * @return lista delle istanze di Bio che usano questo anno
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioNati() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getList(Bio.class, this.getFiltroNati());

        Comparator comp = new Comparator() {
            @Override
            public int compare(Object objA, Object objB) {
                Giorno giorno;
                int primo = 0;
                int ultimo = 0;
                Bio bioA = null;
                Bio bioB = null;
                if (objA instanceof Bio && objB instanceof Bio) {
                    bioA = (Bio) objA;
                    bioB = (Bio) objB;
                }// end of if cycle
                if (bioA.getGiornoNatoPunta() != null) {
                    giorno = bioA.getGiornoNatoPunta();
                    if (giorno != null) {
                        primo = giorno.getOrdinamento();
                    }// end of if cycle
                }// end of if cycle
                if (bioB.getGiornoNatoPunta() != null) {
                    giorno = bioB.getGiornoNatoPunta();
                    if (giorno != null) {
                        ultimo = giorno.getOrdinamento();
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
     * Recupera una lista (array) di records Bio che usano questa istanza di Anno nella property annoMortoPunta
     *
     * @return lista delle istanze di Bio che usano questo anno
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioMorti() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getList(Bio.class, this.getFiltroMorti());

        Comparator comp = new Comparator() {
            @Override
            public int compare(Object objA, Object objB) {
                Giorno giorno;
                int primo = 0;
                int ultimo = 0;
                Bio bioA = null;
                Bio bioB = null;
                if (objA instanceof Bio && objB instanceof Bio) {
                    bioA = (Bio) objA;
                    bioB = (Bio) objB;
                }// end of if cycle
                if (bioA.getGiornoMortoPunta() != null) {
                    giorno = bioA.getGiornoMortoPunta();
                    if (giorno != null) {
                        primo = giorno.getOrdinamento();
                    }// end of if cycle
                }// end of if cycle
                if (bioB.getGiornoMortoPunta() != null) {
                    giorno = bioB.getGiornoMortoPunta();
                    if (giorno != null) {
                        ultimo = giorno.getOrdinamento();
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
     * Recupera il valore del numero di records di Bio che usano questa istanza di Anno nella property annoNatoPunta
     *
     * @return numero di istanze di Bio che usano questo anno
     */
    public int countBioNati() {
//        return (int) AQuery.getCount(Anno.class, "annoNatoPunta", this);
        int numRecords = 0;
        ArrayList<Bio> lista = bioNati();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo


    /**
     * Recupera il valore del numero di records di Bio che usano questa istanza di Anno nella property annoMortoPunta
     *
     * @return numero di istanze di Bio che usano questo anno
     */
    public int countBioMorti() {
//        return (int) AQuery.getCount(Anno.class, "annoMortoPunta", this);
        int numRecords = 0;
        ArrayList<Bio> lista = bioMorti();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo


    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Anno usa nella property annoNatoPunta
     *
     * @return filtro per i nati in questo anno
     */
    private Container.Filter getFiltroNati() {
//        return new Compare.Equal("annoNatoPunta", this);
        return new Compare.Equal("annoNascitaValido", titolo);
    }// fine del metodo


    /**
     * Costruisce il filtro per trovare i records di Bio che questa istanza di Anno usa nella property annoMortoPunta
     *
     * @return filtro per i morti in questo anno
     */
    private Container.Filter getFiltroMorti() {
        return new Compare.Equal("annoMorteValido", titolo);
    }// fine del metodo


    /**
     * Titolo della pagina Nati/Morti da creare/caricare su wikipedia
     */
    public String getTitoloLista(String tag) {
        String titoloLista = CostBio.VUOTO;
        String articolo = "nel";
        String articoloBis = "nell'";


        if (!titolo.equals(CostBio.VUOTO)) {
            if (titolo.equals("1")
                    || titolo.equals("1" + SecoloEnum.TAG_AC)
                    || titolo.equals("11")
                    || titolo.equals("11" + SecoloEnum.TAG_AC)
                    || titolo.startsWith("8")
                    ) {
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

    public Secolo getSecolo() {
        return secolo;
    }// end of getter method

    public void setSecolo(Secolo secolo) {
        this.secolo = secolo;
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
