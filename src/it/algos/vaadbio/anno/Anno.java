package it.algos.vaadbio.anno;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.lib.Secolo;
import it.algos.webbase.web.query.AQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
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
    @Index
    private String nome = "";

    private String secolo = "";

    @Index
    private int ordinamento = 0;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Anno() {
        this("", "", 0);
    }// end of nullary constructor

    /**
     * Costruttore completo
     *
     * @param nome        dell'anno
     * @param secolo      dell'anno
     * @param ordinamento nelle categorie
     */
    public Anno(String nome, String secolo, int ordinamento) {
        super();
        this.setNome(nome);
        this.setSecolo(secolo);
        this.setOrdinamento(ordinamento);
    }// end of general constructor

    /**
     * Recupera una lista (array) di records Bio che usano questa istanza di Anno
     *
     * @return lista delle istanze di Bio
     */
    public ArrayList<Bio> bioNati() {
        ArrayList<Bio> lista = null;
        List entities = null;
        Container.Filter filtro;

        filtro = new Compare.Equal("annoNatoPunta", this);
        entities = AQuery.getList(Bio.class, filtro);

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
                        primo = giorno.getBisestile();
                    }// end of if cycle
                }// end of if cycle
                if (bioB.getGiornoNatoPunta() != null) {
                    giorno = bioB.getGiornoNatoPunta();
                    if (giorno != null) {
                        ultimo = giorno.getBisestile();
                    }// end of if cycle
                }// end of if cycle

                if (primo == ultimo) {
                    return (bioA.getCognome()).compareTo(bioB.getCognome());
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
     * Recupera una lista (array) di records Bio che usano questa istanza di Anno
     *
     * @return lista delle istanze di Bio
     */
    public ArrayList<Bio> bioMorti() {
        ArrayList<Bio> lista = null;
        List entities = null;
        Container.Filter filtro;

        filtro = new Compare.Equal("annoMortoPunta", this);
        entities = AQuery.getList(Bio.class, filtro);

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
                        primo = giorno.getBisestile();
                    }// end of if cycle
                }// end of if cycle
                if (bioB.getGiornoMortoPunta() != null) {
                    giorno = bioB.getGiornoMortoPunta();
                    if (giorno != null) {
                        ultimo = giorno.getBisestile();
                    }// end of if cycle
                }// end of if cycle

                if (primo == ultimo) {
                    return (bioA.getCognome()).compareTo(bioB.getCognome());
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
     * Recupera il valore del numero di records di Bio che usano questo giorno
     *
     * @return numero di records che usano questo giorno
     */
    public int countBioNati() {
        int numRecords = 0;
        ArrayList<Bio> lista = bioNati();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo

    /**
     * Recupera il valore del numero di records di Bio che usano questo giorno
     *
     * @return numero di records che usano questo giorno
     */
    public int countBioMorti() {
        int numRecords = 0;
        ArrayList<Bio> lista = bioMorti();

        if (lista != null) {
            numRecords = lista.size();
        }// end of if cycle

        return numRecords;
    }// fine del metodo

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
        return (ArrayList<Anno>) AQuery.getLista(Anno.class);
    }// end of method

    /**
     * Titolo della pagina Nati/Morti da creare/caricare su wikipedia
     */
    public String getTitoloLista(String tag) {
        String titoloLista = CostBio.VUOTO;
        String articolo = "nel";
        String articoloBis = "nell'";


        if (!nome.equals(CostBio.VUOTO)) {
            if (nome.equals("1")
                    || nome.equals("1" + Secolo.TAG_AC)
                    || nome.equals("11")
                    || nome.equals("11" + Secolo.TAG_AC)
                    || nome.startsWith("8")
                    ) {
                titoloLista = tag + articoloBis + nome;
            } else {
                titoloLista = tag + articolo + CostBio.SPAZIO + nome;
            }// fine del blocco if-else
        }// fine del blocco if

        return titoloLista;
    }// fine del metodo

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

    public int getOrdinamento() {
        return ordinamento;
    }// end of getter method

    public void setOrdinamento(int ordinamento) {
        this.ordinamento = ordinamento;
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
