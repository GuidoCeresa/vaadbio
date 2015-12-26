package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by gac on 21 dic 2015.
 * .
 */
public abstract class ListaBio {

    public static String PAGINA_PROVA = "Utente:Biobot/2";
    protected static String TAG_INDICE = "__FORCETOC__";
    protected static String TAG_NO_INDICE = "__NOTOC__";

    protected String titoloPagina;
    protected ArrayList<String> listaBiografie;
    protected LinkedHashMap<String, ArrayList<String>> mappaBiografie;
    protected int numPersone = 0;

    protected boolean usaTavolaContenuti = true; //@todo mettere la preferenza
//    protected boolean usaTavolaContenuti = Pref.getBool(LibBio.USA_TAVOLA_CONTENUTI, true)

    protected boolean usaDoppiaColonna = true; //@todo mettere la preferenza
    protected boolean usaRaggruppamentoRigheMultiple = true;//@todo mettere la preferenza

    protected boolean usaHeadRitorno = true; // prima del template di avviso
    protected String tagTemplateBio = "ListaBio"; // in alternativa 'StatBio'//@todo mettere la preferenza
    protected boolean usaInclude = true; // vero per Giorni ed Anni
    protected boolean usaHeadIncipit = true; // dopo il template di avviso
    protected String titoloPaginaMadre = "";
    private Object oggetto;  //Giorno, Anno, Attivita, Nazionalita, Antroponimo, ecc

    /**
     * Costruttore
     */
    public ListaBio(Object oggetto) {
        super();
        this.oggetto = oggetto;
        doInit();
    }// fine del costruttore

    /**
     * Trim iniziale
     * <p>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     */
    private static String finale(String testoIn) {
        String testoOut = testoIn;

        // trim finale
        if (!testoIn.equals(CostBio.VUOTO)) {
            testoOut = testoIn.trim();
        }// fine del blocco if

        // valore di ritorno
        return testoOut;
    }// fine del metodo

    protected void doInit() {
//        numDidascalie = 0
//        elaboraParametri()
        elaboraTitolo();

//        elaboraListaBiografie();
        elaboraMappaBiografie();
        elaboraPagina();
    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    protected void elaboraTitolo() {
    }// fine del metodo

//    /**
//     * Costruisce una lista di biografie che hanno una valore valido per il link specifico
//     */
//    private void elaboraListaBiografie() {
//        List vettore;
//        String queryTxt = getQueryCrono();
//        EntityManager manager = EM.createEntityManager();
//        Query query = manager.createQuery(queryTxt);
//
//        vettore = query.getResultList();
//        if (vettore != null) {
//            listaBiografie = new ArrayList<String>(vettore);
//        }// end of if cycle
//        manager.close();
//
//        if (listaBiografie != null) {
//            numPersone = listaBiografie.size();
//        }// fine del blocco if
//    }// fine del metodo

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    private void elaboraMappaBiografie() {
        List vettore;
        String queryTxt = getQueryCrono();
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        Object[] riga;
        String anno;
        String didascalia;
        String didascaliaShort;
        ArrayList<String> lista;

        vettore = query.getResultList();
        if (vettore != null) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (Object rigaTmp : vettore) {
                if (rigaTmp instanceof Object[]) {
                    riga = (Object[]) rigaTmp;
                    anno = (String) riga[0];
                    didascalia = (String) riga[1];
                    didascaliaShort = didascalia.substring(didascalia.indexOf(CostBio.TAG_SEPARATORE) + CostBio.TAG_SEPARATORE.length());
                    if (mappaBiografie.containsKey(anno)) {
                        lista = mappaBiografie.get(anno);
                        lista.add(didascaliaShort);
                    } else {
                        lista = new ArrayList<>();
                        lista.add(didascaliaShort);
                        mappaBiografie.put(anno, lista);
                    }// end of if/else cycle
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle
        manager.close();

        if (mappaBiografie != null) {
            numPersone = mappaBiografie.size();
        }// fine del blocco if
    }// fine del metodo

    /**
     * Costruisce la query specifica per la ricerca della lista biografica
     * Sovrascritto
     */
    protected String getQueryCrono() {
        return CostBio.VUOTO;
    }// fine del metodo

    /**
     * Elaborazione principale della pagina
     * <p>
     * Costruisce head <br>
     * Costruisce body <br>
     * Costruisce footer <br>
     * Ogni blocco esce trimmato (inizio e fine) <br>
     * Gli spazi (righe) di separazione vanno aggiunti qui <br>
     * Registra la pagina <br>
     */
    protected void elaboraPagina() {
        boolean debug = Pref.getBool(CostBio.USA_DEBUG, false);
        String summary = LibWiki.getSummary();
        String testo = CostBio.VUOTO;
//        EditBio paginaModificata
//        Risultato risultato

        if (numPersone > 0) {
            //header
            testo += this.elaboraHead();

            //body
            //a capo, ma senza senza righe di separazione
            testo += CostBio.A_CAPO;
            testo += this.elaboraBody();

            //footer
            //di fila nella stessa riga, senza ritorno a capo (se inizia con <include>)
            testo += this.elaboraFooter();
        }// fine del blocco if

        //registra la pagina
        if (!testo.equals(CostBio.VUOTO)) {
            testo = testo.trim();
            if (debug) {
                testo = titoloPagina + CostBio.A_CAPO + CostBio.A_CAPO + testo;
                Api.scriveVoce(PAGINA_PROVA, testo);
//                testo = LibWiki.setBold(titoloPagina) + CostBio.A_CAPO + testo;
//                paginaModificata = new EditBio(PAGINA_PROVA, testo, summary);
//                registrata = paginaModificata.registrata;
            } else {
//                paginaModificata = new EditBio(titoloPagina, testo, summary);
//                registrata = paginaModificata.registrata;
            }// fine del blocco if-else
        }// fine del blocco if

    }// fine del metodo

    /**
     * Costruisce il testo iniziale della pagina (header)
     * <p>
     * Non sovrascrivibile <br>
     * Posiziona il TOC <br>
     * Posiziona il ritorno (eventuale) <br>
     * Posizione il template di avviso <br>
     * Posiziona l'incipit della pagina (eventuale) <br>
     * Ritorno ed avviso vanno (eventualmente) protetti con 'include' <br>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     * Eventuali spazi gestiti da chi usa il metodo <br>
     */
    private String elaboraHead() {
        String text = CostBio.VUOTO;
        String testoIncluso = CostBio.VUOTO;

        // Posiziona il TOC
        testoIncluso += elaboraTOC();

        // Posiziona il ritorno
        testoIncluso += elaboraRitorno();

        // Posizione il template di avviso
        testoIncluso += elaboraTemplateAvviso();

        // Ritorno ed avviso vanno (eventualmente) protetti con 'include'
        text += elaboraInclude(testoIncluso);

        // Posiziona l'incipit della pagina
        text += elaboraIncipit();

        // valore di ritorno
        return finale(text);
    }// fine del metodo

    /**
     * Corpo della pagina
     * Decide se c'è la doppia colonna
     * Controlla eventuali template di rinvio
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        boolean usaColonne = this.usaDoppiaColonna;
        int maxRigheColonne = 10;//@todo mettere la preferenza
        boolean usaRaggruppamentoRigheMultiple = this.usaRaggruppamentoRigheMultiple;

        if (mappaBiografie != null && mappaBiografie.size() > 0) {
            if (usaRaggruppamentoRigheMultiple) {
                text = righeRaggruppate();
            } else {
                text = righeSemplici();
            }// end of if/else cycle
        }// end of if cycle

        if (usaColonne && (numPersone > maxRigheColonne)) {
            text = LibWiki.listaDueColonne(text.trim());
        }// fine del blocco if

        text = elaboraTemplate(text);

        return text;
    }// fine del metodo

    /**
     * Raggruppa le didascalie per anno uguale
     */
    protected String righeRaggruppate() {
        String text = CostBio.VUOTO;
        ArrayList<String> lista;

        for (Map.Entry<String, ArrayList<String>> mappa : mappaBiografie.entrySet()) {
            if (mappa.getValue().size() == 1) {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(mappa.getKey());
                text += CostBio.TAG_SEPARATORE;
                text += mappa.getValue().get(0);
                text += CostBio.A_CAPO;
            } else {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(mappa.getKey());
                text += CostBio.A_CAPO;
                lista = mappa.getValue();
                for (String didascalia : lista) {
                    text += CostBio.ASTERISCO;
                    text += CostBio.ASTERISCO;
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo


    /**
     * Nessun raggruppamento
     */
    protected String righeSemplici() {
        String text = CostBio.VUOTO;
        ArrayList<String> lista;

        for (Map.Entry<String, ArrayList<String>> mappa : mappaBiografie.entrySet()) {
            if (mappa.getValue().size() == 1) {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(mappa.getKey());
                text += CostBio.TAG_SEPARATORE;
                text += mappa.getValue();
                text += CostBio.A_CAPO;
            } else {
                lista = mappa.getValue();
                for (String didascalia : lista) {
                    text += CostBio.ASTERISCO;
                    text += LibWiki.setQuadre(mappa.getKey());
                    text += CostBio.TAG_SEPARATORE;
                    text += didascalia;
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo


    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Se non viene incapsulato, lascia una riga vuota iniziale
     * Sovrascritto
     */
    protected String elaboraTemplate(String testoIn) {
        return CostBio.A_CAPO + testoIn;
    }// fine del metodo

    /**
     * Piede della pagina
     * <p>
     * Aggiungere (di solito) inizialmente la chiamata al metodo elaboraFooterSpazioIniziale <br>
     * Sovrascritto
     */
    protected String elaboraFooter() {
        return CostBio.VUOTO;
    }// fine del metodo

    /**
     * Costruisce il TOC (tavola contenuti)
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo di una delle due possibilità <br>
     */
    private String elaboraTOC() {
        String text = CostBio.VUOTO;

        if (usaTavolaContenuti) {
            text += TAG_INDICE;
        } else {
            text += TAG_NO_INDICE;
        }// fine del blocco if-else

        return text;
    }// fine del metodo

    /**
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    protected String elaboraRitorno() {
        String text = CostBio.VUOTO;

        if (usaHeadRitorno) {
            if (!titoloPaginaMadre.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPaginaMadre;
                text = LibWiki.setGraffe(text);
            }// fine del blocco if
        }// fine del blocco if

        return text;
    }// fine del metodo

    /**
     * Costruisce il template di avviso
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) il nome del template da usare <br>
     */
    private String elaboraTemplateAvviso() {
        String text = CostBio.VUOTO;
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date());
        String personeTxt = LibNum.format(numPersone);

        text += tagTemplateBio;
        text += "|bio=";
        text += personeTxt;
        text += "|data=";
        text += dataCorrente.trim();
        text = LibWiki.setGraffe(text);

        return text;
    }// fine del metodo

    /**
     * Incorpora ilo testo nel tag 'include'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo <br>
     */
    private String elaboraInclude(String testoIn) {
        String testoOut = testoIn;

        if (usaInclude) {
            testoOut = LibBio.setNoInclude(testoIn);
        }// fine del blocco if

        return testoOut;
    }// fine del metodo

    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Non sovrascrivibile <br>
     */
    private String elaboraIncipit() {
        String text = CostBio.VUOTO;

        if (usaHeadIncipit) {
            text += elaboraIncipitSpecifico();
        }// fine del blocco if

        return text;
    }// fine del metodo



    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    protected String elaboraIncipitSpecifico() {
        return CostBio.VUOTO;
    }// fine del metodo

    protected String getTitoloPagina() {
        return titoloPagina;
    }// end of getter method

    protected ArrayList<String> getListaBiografie() {
        return listaBiografie;
    }// end of getter method

    protected int getNumPersone() {
        return numPersone;
    }// end of getter

    protected Object getOggetto() {
        return oggetto;
    }// end of getter method

}// fine della classe
