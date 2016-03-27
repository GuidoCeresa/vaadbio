package it.algos.vaadbio.nome;


import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.query.AQuery;
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
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 */
@Entity
public class Nome extends BaseEntity {

    @NotEmpty
    @Index()
    private String nome = "";

    /**
     * Di norma gli accenti vengono rispettati. Pertanto: María, Marià, Maria, Mária, Marìa, Mariâ sono nomi diversi
     * Volendo (con un flag) possono essere considerati lo stesso nome.
     * In questo caso (stesso nome), il parametro principale diventa false ed il parametro riferimento punta a Maria (senza accento)
     */
    @Index()
    private boolean principale;

    /**
     * Di norma (con un flag) i nomi doppi vengono troncati
     * Forzando questo parametro questo nome viene mantenuto com'è anche se doppio
     */
    @Index()
    private boolean nomeDoppio;


    @ManyToOne
    private Nome riferimento;


    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Nome() {
        this("");
    }// end of nullary constructor


    /**
     * Costruttore
     *
     * @param nome della persona
     */
    public Nome(String nome) {
        this(nome, true, false, null);
    }// end of general constructor

    /**
     * Costruttore completo
     *
     * @param nome        della persona
     * @param principale  flag
     * @param nomeDoppio  flag
     * @param riferimento al nome che raggruppa le varie dizioni
     */
    public Nome(String nome, boolean principale, boolean nomeDoppio, Nome riferimento) {
        super();
        this.setNome(nome);
        this.setPrincipale(principale);
        this.setNomeDoppio(nomeDoppio);
        this.setRiferimento(riferimento);
    }// end of full constructor


    /**
     * Recupera una istanza di Nome usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Nome, null se non trovata
     */
    public static Nome find(long id) {
        Nome instance = null;
        BaseEntity entity = AQuery.queryById(Nome.class, id);

        if (entity != null) {
            if (entity instanceof Nome) {
                instance = (Nome) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Nome usando la query di una property specifica
     *
     * @param nome valore della property nome
     * @return istanza di Nome, null se non trovata
     */
    public static Nome findByNome(String nome) {
        Nome instance = null;
        BaseEntity entity = AQuery.queryOne(Nome.class, Nome_.nome, nome);

        if (entity != null) {
            if (entity instanceof Nome) {
                instance = (Nome) entity;
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
        long totTmp = AQuery.getCount(Nome.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records della Domain Class
     *
     * @return lista di tutte le istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Nome> findAll() {
        return (ArrayList<Nome>) AQuery.getLista(Nome.class);
    }// end of method

    /**
     * Recupera una lista (array) di tutti i records di Nome NON doppi
     *
     * @return lista di tutte le istanze di Nome
     */
    @SuppressWarnings("unchecked")
    public synchronized static ArrayList<Nome> findAllNotDoppi() {
        Container.Filter filtro = new Compare.Equal("nomeDoppio", false);
        return (ArrayList<Nome>) AQuery.getLista(Nome.class, filtro);
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

    public boolean isPrincipale() {
        return principale;
    }// end of getter method

    public void setPrincipale(boolean principale) {
        this.principale = principale;
    }//end of setter method


    public boolean isNomeDoppio() {
        return nomeDoppio;
    }// end of getter method

    public void setNomeDoppio(boolean nomeDoppio) {
        this.nomeDoppio = nomeDoppio;
    }//end of setter method

    public Nome getRiferimento() {
        return riferimento;
    }// end of getter method

    public void setRiferimento(Nome riferimento) {
        this.riferimento = riferimento;
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
     * Recupera una lista (array) di records Bio che usano questa istanza di Nome nella property nomePunta
     *
     * @return lista delle istanze di Bio che usano questo nome
     */
    @SuppressWarnings("all")
    public ArrayList<Bio> bioNome() {
        ArrayList<Bio> lista = null;
        List entities = AQuery.getList(Bio.class, this.getFiltroNome());

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

}// end of entity class
