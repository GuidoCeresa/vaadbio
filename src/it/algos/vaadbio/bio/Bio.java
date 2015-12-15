package it.algos.vaadbio.bio;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.web.entity.BaseEntity;
import it.algos.webbase.web.lib.LibTime;
import it.algos.webbase.web.query.AQuery;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe di tipo JavaBean
 * <p>
 * 1) la classe deve avere un costruttore senza argomenti
 * 2) le proprietà devono essere private e accessibili solo con get, set e is (usato per i boolenai al posto di get)
 * 3) la classe deve implementare l'interfaccia Serializable (la fa nella superclasse)
 * 4) la classe non deve contenere nessun metodo per la gestione degli eventi
 * <p>
 * Created by gac on 23 nov 2015.
 */
@Entity
public class Bio extends BaseEntity {

    @NotNull
    @Index
    private long pageid;

    @NotEmpty
    @Column(columnDefinition = "text")
    private String title = "";

    @Lob
    private String templateServer = "";

    @Lob
    private String templateStandard = "";

    //--tempo di DOWNLOAD
    //--uso il formato Timestamp, per confrontarla col campo timestamp
    //--molto meglio che siano esattamente dello stesso tipo
    //--ultima lettura/aggiornamento della voce effettuata dal programma VaadBio
    private Timestamp ultimaLettura;

    //--tempo di ELABORAZIONE
    //--momento in cui il record Bio è stato elaborato estraendo i dati dal tmplBioServer e costruendo il tmplBioStandard
    private Timestamp ultimaElaborazione;

    //--esiste la pagina (altrimenti non ci sarebbe il record) con pageid e title, ma templateServer potrebbe essere vuoto
    //--controllato e regolato alla fine di Download
    private boolean templateEsiste;

    //--templateServer potrebbe essere incompleto o non chiuso o mancante di alcuni parametri fondamentali
    //--controllato e regolato alla fine di Download
    private boolean templateValido;

    //-- uguaglianza tra templateServer e templateStandard
    //--controllato e regolato alla fine di Elabora
    private boolean templatesUguali;

    //--parametri originali del template Bio presenti nel template della voce ed estratti pari pari dal tmplBioServer
    //--serve mantenerli separati per ricerche ed ordinamenti
    @Column(columnDefinition = "text")
    private String titolo = "";  //titolo tipo ''dottore'', non TITLE della pagina
    @Column(columnDefinition = "text")
    private String nome = "";
    @Column(columnDefinition = "text")
    private String cognome = "";
    @Column(columnDefinition = "text")
    private String cognomePrima = "";
    @Column(columnDefinition = "text")
    private String postCognome = "";
    @Column(columnDefinition = "text")
    private String postCognomeVirgola = "";
    @Column(columnDefinition = "text")
    private String forzaOrdinamento = "";
    @Column(columnDefinition = "text")
    private String preData = "";
    @Column(columnDefinition = "text")
    private String sesso = "";

    @Column(columnDefinition = "text")
    private String luogoNascita = "";
    @Column(columnDefinition = "text")
    private String luogoNascitaLink = "";
    @Column(columnDefinition = "text")
    private String luogoNascitaAlt = "";
    @Column(columnDefinition = "text")
    private String giornoMeseNascita = "";
    @Column(columnDefinition = "text")
    private String annoNascita = "";
    @Column(columnDefinition = "text")
    private String noteNascita = "";

    @Column(columnDefinition = "text")
    private String luogoMorte = "";
    @Column(columnDefinition = "text")
    private String luogoMorteLink = "";
    @Column(columnDefinition = "text")
    private String luogoMorteAlt = "";
    @Column(columnDefinition = "text")
    private String giornoMeseMorte = "";
    @Column(columnDefinition = "text")
    private String annoMorte = "";
    @Column(columnDefinition = "text")
    private String noteMorte = "";

    @Column(columnDefinition = "text")
    private String preAttivita = "";
    @Column(columnDefinition = "text")
    private String epoca = "";
    @Column(columnDefinition = "text")
    private String epoca2 = "";
    @Column(columnDefinition = "text")
    private String attivita = "";
    @Column(columnDefinition = "text")
    private String attivita2 = "";
    @Column(columnDefinition = "text")
    private String attivita3 = "";
    @Column(columnDefinition = "text")
    private String attivitaAltre = "";

    @Column(columnDefinition = "text")
    private String nazionalita = "";
    @Column(columnDefinition = "text")
    private String nazionalitaNaturalizzato = "";
    @Column(columnDefinition = "text")
    private String cittadinanza = "";
    @Column(columnDefinition = "text")
    private String postNazionalita = "";

    @Column(columnDefinition = "text")
    private String categorie = "";
    @Column(columnDefinition = "text")
    private String fineIncipit = "";
    @Column(columnDefinition = "text")
    private String punto = "";
    @Column(columnDefinition = "text")
    private String immagine = "";
    @Column(columnDefinition = "text")
    private String didascalia = "";
    @Column(columnDefinition = "text")
    private String didascalia2 = "";
    @Column(columnDefinition = "text")
    private String dimImmagine = "";


    //--parametri modificati rispetto ai parametri originali con lo stesso nome, secondo logiche specifiche per ogni parametro
    //--parametri usati per creare i link alle tavole specializzate per costruire le didascalie usate nelle liste
    private String nomeValido = "";
    private String cognomeValido = "";
    private String sessoValido = "";

    private String luogoNascitaValido = "";
    private String luogoNascitaLinkValido = "";
    private String giornoMeseNascitaValido = "";
    private String annoNascitaValido = "";

    private String luogoMorteValido = "";
    private String luogoMorteLinkValido = "";
    private String giornoMeseMorteValido = "";
    private String annoMorteValido = "";

    private String attivitaValido = "";
    private String attivita2Valido = "";
    private String attivita3Valido = "";
    private String nazionalitaValido = "";


    // campi di collegamenti alle altre tavole specializzate
    @ManyToOne
    private Giorno giornoNatoPunta;
    @ManyToOne
    private Giorno giornoMortoPunta ;
//    Giorno giornoMortoLista = null;
//    Anno annoNatoLista = null;
//    Anno annoMortoLista = null;
//    Attivita attivitaLista = null;
//    Attivita attivita2Lista = null;
//    Attivita attivita3Lista = null;
//    Nazionalita nazionalitaLista = null;
//    Localita luogoNatoLista= null;
//    Localita luogoMortoLista = null;
//    Nome nomeLista = null;
//    Cognome cognomeLista = null;

    /**
     * Costruttore senza argomenti
     * Necessario per le specifiche JavaBean
     */
    public Bio() {
        super();
    }// end of general constructor


    public synchronized static int count() {
        int totRec = 0;
        long totTmp = AQuery.getCount(Bio.class);

        if (totTmp > 0) {
            totRec = (int) totTmp;
        }// fine del blocco if

        return totRec;
    }// end of method


    /**
     * Recupera una istanza di Bio usando la query standard della Primary Key
     *
     * @param id valore della Primary Key
     * @return istanza di Bio, null se non trovata
     */
    public synchronized static Bio find(long id) {
        Bio instance = null;
        BaseEntity entity = AQuery.queryById(Bio.class, id);

        if (entity != null) {
            if (entity instanceof Bio) {
                instance = (Bio) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Wikibio usando la query specifica
     *
     * @param pageid valore della key base/standard per la ricerca (indicizzata e più veloce)
     * @return istanza di Bio, null se non trovata
     */
    public synchronized static Bio findByPageid(long pageid) {
        Bio instance = null;
        BaseEntity entity = AQuery.queryOne(Bio.class, Bio_.pageid, pageid);

        if (entity != null) {
            if (entity instanceof Bio) {
                instance = (Bio) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method

    /**
     * Recupera una istanza di Wikibio usando la query specifica
     *
     * @param title valore della key per la ricerca (NON indicizzata e più lenta)
     * @return istanza di Bio, null se non trovata
     */
    public synchronized static Bio findByTitle(String title) {
        Bio instance = null;
        BaseEntity entity = AQuery.queryOne(Bio.class, Bio_.title, title);

        if (entity != null) {
            if (entity instanceof Bio) {
                instance = (Bio) entity;
            }// end of if cycle
        }// end of if cycle

        return instance;
    }// end of method


    /**
     * Recupera i pageids di tutti i records presenti, ordinati per ultimaLettura ascendente
     *
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> findAllPageid() {
        return findAllPageid(0);
    }// end of method


    /**
     * Recupera i pageids dei primi (limit) records, ordinati per ultimaLettura ascendente
     *
     * @param limit di ricerca per la query
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> findAllPageid(int limit) {
        return findAllPageid(limit, 0);
    }// end of method


    /**
     * Recupera i pageids dei primi (limit) records, partendo da offSet, ordinati per ultimaLettura ascendente
     *
     * @param limit  di ricerca per la query
     * @param offSet di inizio per la query
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> findAllPageid(int limit, int offSet) {
        return LibBio.queryFind("select bio.pageid from Bio bio order by bio.ultimaLettura,bio.pageid asc", limit, offSet);
    }// end of method

    /**
     * Recupera la data dell'ultima lettura della voce più vecchia
     *
     * @return la più vecchia data di aggiornamento, in formato testo
     */
    public synchronized static String findOldestLetta() {
        String messaggio;
        ArrayList listaTimestamp;

        listaTimestamp = LibBio.queryFind("select bio.ultimaLettura from Bio bio order by bio.ultimaLettura,bio");

        messaggio = "La voce più vecchia non aggiornata è del " + LibTime.getData(listaTimestamp);
        return messaggio;
    }// fine del metodo

    /**
     * Recupera la data dell'ultima elaborazione della voce più vecchia
     *
     * @return la più vecchia data di aggiornamento, in formato testo
     */
    public synchronized static String findOldestElaborata() {
        String messaggio;
        ArrayList listaTimestamp;

        listaTimestamp = LibBio.queryFind("select bio.ultimaElaborazione from Bio bio order by bio.ultimaElaborazione,bio");

        messaggio = "Il record più vecchio non elaborato è del " + LibTime.getData(listaTimestamp);
        return messaggio;
    }// fine del metodo


    /**
     * Recupera i pageids dei primi (limit) records, ordinati per ultimaElaborazione ascendente
     *
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> findLast() {
        return findLast(0);
    }// end of method

    /**
     * Recupera i pageids dei primi (limit) records, ordinati per ultimaElaborazione ascendente
     *
     * @param limit di ricerca per la query
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> findLast(int limit) {
        return LibBio.queryFind("select bio.pageid from Bio bio order by bio.ultimaElaborazione,bio.pageid asc", limit);
    }// end of method


    public long getPageid() {
        return pageid;
    }// end of getter method

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }//end of setter method

    public String getTitle() {
        return title;
    }// end of getter method

    public void setTitle(String title) {
        this.title = title;
    }//end of setter method

    public String getTemplateServer() {
        return templateServer;
    }// end of getter method

    public void setTemplateServer(String templateServer) {
        this.templateServer = templateServer;
    }//end of setter method

    public String getTemplateStandard() {
        return templateStandard;
    }// end of getter method

    public void setTemplateStandard(String templateStandard) {
        this.templateStandard = templateStandard;
    }//end of setter method

    public Timestamp getUltimaLettura() {
        return ultimaLettura;
    }// end of getter method

    public void setUltimaLettura(Timestamp ultimaLettura) {
        this.ultimaLettura = ultimaLettura;
    }//end of setter method

    public Timestamp getUltimaElaborazione() {
        return ultimaElaborazione;
    }// end of getter method

    public void setUltimaElaborazione(Timestamp ultimaElaborazione) {
        this.ultimaElaborazione = ultimaElaborazione;
    }//end of setter method


    public boolean isTemplateEsiste() {
        return templateEsiste;
    }// end of getter method

    public void setTemplateEsiste(boolean templateEsiste) {
        this.templateEsiste = templateEsiste;
    }//end of setter method

    public boolean isTemplateValido() {
        return templateValido;
    }// end of getter method

    public void setTemplateValido(boolean templateValido) {
        this.templateValido = templateValido;
    }//end of setter method

    public boolean isTemplatesUguali() {
        return templatesUguali;
    }// end of getter method

    public void setTemplatesUguali(boolean templatesUguali) {
        this.templatesUguali = templatesUguali;
    }//end of setter method

    public String getTitolo() {
        return titolo;
    }// end of getter method

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }//end of setter method

    public String getNome() {
        return nome;
    }// end of getter method

    public void setNome(String nome) {
        this.nome = nome;
    }//end of setter method

    public String getCognome() {
        return cognome;
    }// end of getter method

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }//end of setter method

    public String getCognomePrima() {
        return cognomePrima;
    }// end of getter method

    public void setCognomePrima(String cognomePrima) {
        this.cognomePrima = cognomePrima;
    }//end of setter method

    public String getPostCognome() {
        return postCognome;
    }// end of getter method

    public void setPostCognome(String postCognome) {
        this.postCognome = postCognome;
    }//end of setter method

    public String getPostCognomeVirgola() {
        return postCognomeVirgola;
    }// end of getter method

    public void setPostCognomeVirgola(String postCognomeVirgola) {
        this.postCognomeVirgola = postCognomeVirgola;
    }//end of setter method

    public String getForzaOrdinamento() {
        return forzaOrdinamento;
    }// end of getter method

    public void setForzaOrdinamento(String forzaOrdinamento) {
        this.forzaOrdinamento = forzaOrdinamento;
    }//end of setter method

    public String getPreData() {
        return preData;
    }// end of getter method

    public void setPreData(String preData) {
        this.preData = preData;
    }//end of setter method

    public String getSesso() {
        return sesso;
    }// end of getter method

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }//end of setter method

    public String getLuogoNascita() {
        return luogoNascita;
    }// end of getter method

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }//end of setter method

    public String getLuogoNascitaLink() {
        return luogoNascitaLink;
    }// end of getter method

    public void setLuogoNascitaLink(String luogoNascitaLink) {
        this.luogoNascitaLink = luogoNascitaLink;
    }//end of setter method

    public String getLuogoNascitaAlt() {
        return luogoNascitaAlt;
    }// end of getter method

    public void setLuogoNascitaAlt(String luogoNascitaAlt) {
        this.luogoNascitaAlt = luogoNascitaAlt;
    }//end of setter method

    public String getGiornoMeseNascita() {
        return giornoMeseNascita;
    }// end of getter method

    public void setGiornoMeseNascita(String giornoMeseNascita) {
        this.giornoMeseNascita = giornoMeseNascita;
    }//end of setter method

    public String getAnnoNascita() {
        return annoNascita;
    }// end of getter method

    public void setAnnoNascita(String annoNascita) {
        this.annoNascita = annoNascita;
    }//end of setter method

    public String getNoteNascita() {
        return noteNascita;
    }// end of getter method

    public void setNoteNascita(String noteNascita) {
        this.noteNascita = noteNascita;
    }//end of setter method

    public String getLuogoMorte() {
        return luogoMorte;
    }// end of getter method

    public void setLuogoMorte(String luogoMorte) {
        this.luogoMorte = luogoMorte;
    }//end of setter method

    public String getLuogoMorteLink() {
        return luogoMorteLink;
    }// end of getter method

    public void setLuogoMorteLink(String luogoMorteLink) {
        this.luogoMorteLink = luogoMorteLink;
    }//end of setter method

    public String getLuogoMorteAlt() {
        return luogoMorteAlt;
    }// end of getter method

    public void setLuogoMorteAlt(String luogoMorteAlt) {
        this.luogoMorteAlt = luogoMorteAlt;
    }//end of setter method

    public String getGiornoMeseMorte() {
        return giornoMeseMorte;
    }// end of getter method

    public void setGiornoMeseMorte(String giornoMeseMorte) {
        this.giornoMeseMorte = giornoMeseMorte;
    }//end of setter method

    public String getAnnoMorte() {
        return annoMorte;
    }// end of getter method

    public void setAnnoMorte(String annoMorte) {
        this.annoMorte = annoMorte;
    }//end of setter method

    public String getNoteMorte() {
        return noteMorte;
    }// end of getter method

    public void setNoteMorte(String noteMorte) {
        this.noteMorte = noteMorte;
    }//end of setter method

    public String getPreAttivita() {
        return preAttivita;
    }// end of getter method

    public void setPreAttivita(String preAttivita) {
        this.preAttivita = preAttivita;
    }//end of setter method

    public String getEpoca() {
        return epoca;
    }// end of getter method

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }//end of setter method

    public String getEpoca2() {
        return epoca2;
    }// end of getter method

    public void setEpoca2(String epoca2) {
        this.epoca2 = epoca2;
    }//end of setter method

    public String getAttivita() {
        return attivita;
    }// end of getter method

    public void setAttivita(String attivita) {
        this.attivita = attivita;
    }//end of setter method

    public String getAttivita2() {
        return attivita2;
    }// end of getter method

    public void setAttivita2(String attivita2) {
        this.attivita2 = attivita2;
    }//end of setter method

    public String getAttivita3() {
        return attivita3;
    }// end of getter method

    public void setAttivita3(String attivita3) {
        this.attivita3 = attivita3;
    }//end of setter method

    public String getAttivitaAltre() {
        return attivitaAltre;
    }// end of getter method

    public void setAttivitaAltre(String attivitaAltre) {
        this.attivitaAltre = attivitaAltre;
    }//end of setter method

    public String getNazionalita() {
        return nazionalita;
    }// end of getter method

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }//end of setter method

    public String getNazionalitaNaturalizzato() {
        return nazionalitaNaturalizzato;
    }// end of getter method

    public void setNazionalitaNaturalizzato(String nazionalitaNaturalizzato) {
        this.nazionalitaNaturalizzato = nazionalitaNaturalizzato;
    }//end of setter method

    public String getCittadinanza() {
        return cittadinanza;
    }// end of getter method

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }//end of setter method

    public String getPostNazionalita() {
        return postNazionalita;
    }// end of getter method

    public void setPostNazionalita(String postNazionalita) {
        this.postNazionalita = postNazionalita;
    }//end of setter method

    public String getCategorie() {
        return categorie;
    }// end of getter method

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }//end of setter method

    public String getFineIncipit() {
        return fineIncipit;
    }// end of getter method

    public void setFineIncipit(String fineIncipit) {
        this.fineIncipit = fineIncipit;
    }//end of setter method

    public String getPunto() {
        return punto;
    }// end of getter method

    public void setPunto(String punto) {
        this.punto = punto;
    }//end of setter method

    public String getImmagine() {
        return immagine;
    }// end of getter method

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }//end of setter method

    public String getDidascalia() {
        return didascalia;
    }// end of getter method

    public void setDidascalia(String didascalia) {
        this.didascalia = didascalia;
    }//end of setter method

    public String getDidascalia2() {
        return didascalia2;
    }// end of getter method

    public void setDidascalia2(String didascalia2) {
        this.didascalia2 = didascalia2;
    }//end of setter method

    public String getDimImmagine() {
        return dimImmagine;
    }// end of getter method

    public void setDimImmagine(String dimImmagine) {
        this.dimImmagine = dimImmagine;
    }//end of setter method

    public String getNomeValido() {
        return nomeValido;
    }// end of getter method

    public void setNomeValido(String nomeValido) {
        this.nomeValido = nomeValido;
    }//end of setter method

    public String getCognomeValido() {
        return cognomeValido;
    }// end of getter method

    public void setCognomeValido(String cognomeValido) {
        this.cognomeValido = cognomeValido;
    }//end of setter method

    public String getSessoValido() {
        return sessoValido;
    }// end of getter method

    public void setSessoValido(String sessoValido) {
        this.sessoValido = sessoValido;
    }//end of setter method

    public String getLuogoNascitaValido() {
        return luogoNascitaValido;
    }// end of getter method

    public void setLuogoNascitaValido(String luogoNascitaValido) {
        this.luogoNascitaValido = luogoNascitaValido;
    }//end of setter method

    public String getLuogoNascitaLinkValido() {
        return luogoNascitaLinkValido;
    }// end of getter method

    public void setLuogoNascitaLinkValido(String luogoNascitaLinkValido) {
        this.luogoNascitaLinkValido = luogoNascitaLinkValido;
    }//end of setter method

    public String getGiornoMeseNascitaValido() {
        return giornoMeseNascitaValido;
    }// end of getter method

    public void setGiornoMeseNascitaValido(String giornoMeseNascitaValido) {
        this.giornoMeseNascitaValido = giornoMeseNascitaValido;
    }//end of setter method

    public String getAnnoNascitaValido() {
        return annoNascitaValido;
    }// end of getter method

    public void setAnnoNascitaValido(String annoNascitaValido) {
        this.annoNascitaValido = annoNascitaValido;
    }//end of setter method

    public String getLuogoMorteValido() {
        return luogoMorteValido;
    }// end of getter method

    public void setLuogoMorteValido(String luogoMorteValido) {
        this.luogoMorteValido = luogoMorteValido;
    }//end of setter method

    public String getLuogoMorteLinkValido() {
        return luogoMorteLinkValido;
    }// end of getter method

    public void setLuogoMorteLinkValido(String luogoMorteLinkValido) {
        this.luogoMorteLinkValido = luogoMorteLinkValido;
    }//end of setter method

    public String getGiornoMeseMorteValido() {
        return giornoMeseMorteValido;
    }// end of getter method

    public void setGiornoMeseMorteValido(String giornoMeseMorteValido) {
        this.giornoMeseMorteValido = giornoMeseMorteValido;
    }//end of setter method

    public String getAnnoMorteValido() {
        return annoMorteValido;
    }// end of getter method

    public void setAnnoMorteValido(String annoMorteValido) {
        this.annoMorteValido = annoMorteValido;
    }//end of setter method

    public String getAttivitaValido() {
        return attivitaValido;
    }// end of getter method

    public void setAttivitaValido(String attivitaValido) {
        this.attivitaValido = attivitaValido;
    }//end of setter method

    public String getAttivita2Valido() {
        return attivita2Valido;
    }// end of getter method

    public void setAttivita2Valido(String attivita2Valido) {
        this.attivita2Valido = attivita2Valido;
    }//end of setter method

    public String getAttivita3Valido() {
        return attivita3Valido;
    }// end of getter method

    public void setAttivita3Valido(String attivita3Valido) {
        this.attivita3Valido = attivita3Valido;
    }//end of setter method

    public String getNazionalitaValido() {
        return nazionalitaValido;
    }// end of getter method

    public void setNazionalitaValido(String nazionalitaValido) {
        this.nazionalitaValido = nazionalitaValido;
    }//end of setter method

    public Giorno getGiornoNatoPunta() {
        return giornoNatoPunta;
    }// end of getter method

    public void setGiornoNatoPunta(Giorno giornoNatoPunta) {
        this.giornoNatoPunta = giornoNatoPunta;
    }//end of setter method

    public Giorno getGiornoMortoPunta() {
        return giornoMortoPunta;
    }// end of getter method

    public void setGiornoMortoPunta(Giorno giornoMortoPunta) {
        this.giornoMortoPunta = giornoMortoPunta;
    }//end of setter method
}// end of entity class
