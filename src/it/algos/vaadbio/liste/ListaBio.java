package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.genere.Genere;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.professione.Professione;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.lib.LibTime;

import java.util.*;

/**
 * Created by gac on 21 dic 2015.
 * .
 */
public abstract class ListaBio {

    public final static String PAGINA_PROVA = "Utente:Biobot/2";
    protected final static String TAG_INDICE = "__FORCETOC__";
    protected final static String TAG_NO_INDICE = "__NOTOC__";
    protected final static String TAG_NON_SCRIVERE = "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->";
    protected final static String ALTRE = "Altre...";
    protected final static String KEY_MAP_PARAGRAFO_TITOLO = "keyMapTitolo";
    protected final static String KEY_MAP_PARAGRAFO_LINK = "keyMapLink";
    protected final static String KEY_MAP_SESSO = "keyMapSesso";
    protected final static String KEY_MAP_LISTA = "keyMapLista";
    protected final static String KEY_MAP_VOCI = "keyMapVoci";
    protected final static String KEY_MAP_ORDINE_ANNO_NATO = "keyMapOrdineAnnoNato";
    protected final static String KEY_MAP_ORDINE_ANNO_MORTO = "keyMapOrdineAnnoMorto";
    protected final static String KEY_MAP_ORDINE_GIORNO_NATO = "keyMapOrdineGiornoNato";
    protected final static String KEY_MAP_ORDINE_GIORNO_MORTO = "keyMapOrdineGiornoMorto";
    protected String titoloPagina;
    protected List<Bio> listaBio;
    protected LinkedHashMap<String, HashMap> mappaBio = new LinkedHashMap<String, HashMap>();
    protected int numPersone = 0;

    protected boolean usaHeadNonScrivere;
    protected boolean usaHeadInclude; // vero per Giorni ed Anni
    protected boolean usaHeadToc;
    protected boolean usaHeadTocIndice;
    protected boolean usaHeadRitorno; // prima del template di avviso
    protected boolean usaHeadTemplateAvviso; // uso del template StatBio
    protected String tagHeadTemplateAvviso; // template 'StatBio'
    protected String tagHeadTemplateProgetto;
    protected boolean usaHeadIncipit; // dopo il template di avviso

    protected boolean usaSortCronologico;
    protected boolean usaSuddivisioneParagrafi;
    protected boolean usaOrdineAlfabeticoParagrafi;
    protected boolean usaBodySottopagine;
    protected boolean usaBodyRigheMultiple;
    protected boolean usaBodyDoppiaColonna;
    protected boolean usaBodyTemplate;
    protected boolean usaSottopagine;
    protected int maxVociParagrafo;
    protected String tagParagrafoNullo;
    protected boolean usaTitoloParagrafoConLink;
    protected boolean usaTaglioVociPagina;
    protected int maxVociPagina;

    //        tagTemplateBio == Pref.getStr(LibBio.NOME_TEMPLATE_AVVISO_LISTE_GIORNI_ANNI, 'ListaBio')
    protected String titoloPaginaMadre = "";
    protected boolean usaFooterPortale;
    protected boolean usaFooterCategorie;
    protected Object oggetto;  //Giorno, Anno, Attivita, Nazionalita, Antroponimo, ecc

    private boolean registrata;

    /**
     * Costruttore senza parametri
     */
    protected ListaBio() {
    }// fine del costruttore

    /**
     * Costruttore
     */
    public ListaBio(Object oggetto) {
        super();
        this.oggetto = oggetto;
        doInit();
    }// fine del costruttore


    protected void doInit() {
        elaboraParametri();
        elaboraTitolo();
        elaboraListaBiografie();
        elaboraMappaBiografie();
        ordinaMappaBiografie();
        elaboraPagina();
    }// fine del metodo

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    protected void elaboraParametri() {
        // head
        usaHeadNonScrivere = Pref.getBool(CostBio.USA_HEAD_NON_SCRIVERE, true);
        usaHeadInclude = true; //--tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
        usaHeadToc = true; //--tipicamente sempre true.
        usaHeadTocIndice = true; //--normalmente true. Sovrascrivibile da preferenze
        usaHeadRitorno = false; //--normalmente false. Sovrascrivibile da preferenze
        usaHeadTemplateAvviso = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        tagHeadTemplateAvviso = "ListaBio"; //--Sovrascrivibile da preferenze
        tagHeadTemplateProgetto = "biografie"; //--Sovrascrivibile da preferenze
        usaHeadIncipit = false; //--normalmente false. Sovrascrivibile da preferenze

        // body
        usaSortCronologico = false;
        usaSuddivisioneParagrafi = false;
        usaOrdineAlfabeticoParagrafi = false;
        usaBodySottopagine = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        usaBodyRigheMultiple = false; //--normalmente false. Sovrascrivibile da preferenze
        usaBodyDoppiaColonna = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        usaBodyTemplate = false; //--normalmente false. Sovrascrivibile nelle sottoclassi
        usaSottopagine = false; //--normalmente false. Sovrascrivibile nelle sottoclassi
        maxVociParagrafo = Pref.getInt(CostBio.MAX_VOCI_PARAGRAFO, 50);//--tipicamente 100. Sovrascrivibile nelle sottoclassi
        tagParagrafoNullo = ALTRE;
        usaTitoloParagrafoConLink = false;
        usaTaglioVociPagina = false;
        maxVociPagina = 0;

        // footer
        usaFooterPortale = false;
        usaFooterCategorie = false;

//        usaSuddivisioneUomoDonna = false
//        usaAttivitaMultiple = false
//        usaTitoloParagrafoConLink = true
//        usaTitoloSingoloParagrafo = false
//        tagLivelloParagrafo = '=='
//        tagParagrafoNullo = 'Altre...'
//        usaSottopaginaAltri == Pref.getBool(LibBio.USA_SOTTOPAGINA_ALTRI, false)
    }// fine del metodo


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    protected void elaboraTitolo() {
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
    }// fine del metodo


    /**
     * Costruisce una mappa di tutte le biografie della pagina,
     * suddividendo la lista in paragrafi (attività, nazionalità, alfabetico, ecc.)
     * Sovrascritto
     */
    protected void elaboraMappaBiografie() {
        if (usaTaglioVociPagina && listaBio.size() < maxVociPagina) {
            return;
        }// end of if cycle

        if (listaBio != null && listaBio.size() > 0) {
            for (Bio bio : listaBio) {
                elaboraMappaSingola(bio);
            }// end of if cycle
        }// end of for cycle

        if (listaBio != null) {
            numPersone = listaBio.size();
        }// end of if cycle
    }// fine del metodo


    /**
     * Costruisce una mappa di tutte le biografie della pagina, suddivisa in paragrafi
     * Sovrascritto
     */
    @SuppressWarnings("all")
    protected void elaboraMappaSingola(Bio bio) {
        String key = getChiave(bio);
        String didascalia;
        ArrayList<Bio> lista;
        HashMap<String, Object> mappa;
        int voci;

        if (mappaBio.containsKey(key)) {
            mappa = mappaBio.get(key);
            lista = (ArrayList<Bio>) mappa.get(KEY_MAP_LISTA);
            voci = (int) mappa.get(KEY_MAP_VOCI);
            lista.add(bio);
            mappa.put(KEY_MAP_VOCI, voci + 1);
        } else {
            mappa = new HashMap<>();
            lista = new ArrayList<>();
            lista.add(bio);
            mappa.put(KEY_MAP_PARAGRAFO_TITOLO, key);
            mappa.put(KEY_MAP_PARAGRAFO_LINK, getTitoloParagrafo(bio));
            mappa.put(KEY_MAP_LISTA, lista);
            mappa.put(KEY_MAP_SESSO, bio.getSesso());
            mappa.put(KEY_MAP_VOCI, 1);

            if (usaSortCronologico) {
                if (bio.getGiornoNatoPunta() != null) {
                    mappa.put(KEY_MAP_ORDINE_GIORNO_NATO, bio.getGiornoNatoPunta().getOrdinamento());
                }// end of if cycle
                if (bio.getGiornoMortoPunta() != null) {
                    mappa.put(KEY_MAP_ORDINE_GIORNO_MORTO, bio.getGiornoMortoPunta().getOrdinamento());
                }// end of if cycle
                if (bio.getAnnoNatoPunta() != null) {
                    mappa.put(KEY_MAP_ORDINE_ANNO_NATO, bio.getAnnoNatoPunta().getOrdinamento());
                }// end of if cycle
                if (bio.getAnnoMortoPunta() != null) {
                    mappa.put(KEY_MAP_ORDINE_ANNO_MORTO, bio.getAnnoMortoPunta().getOrdinamento());
                }// end of if cycle
            }// end of if cycle

            mappaBio.put(key, mappa);
        }// end of if/else cycle
    }// fine del metodo

    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    protected String getChiave(Bio bio) {
        return "";
    }// fine del metodo

    /**
     * Costruisce il titolo del paragrafo
     * <p>
     * Questo deve essere composto da:
     * Professione.pagina
     * Genere.plurale
     */
    protected String getTitoloParagrafo(Bio bio) {
        String titoloParagrafo = tagParagrafoNullo;
        Professione professione = null;
        String professioneTxt;
        String paginaWiki = CostBio.VUOTO;
        Genere genere = null;
        String genereTxt;
        String linkVisibile = CostBio.VUOTO;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();

        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            professione = Professione.findBySingolare(attivitaSingolare);
            genere = Genere.findBySingolareAndSesso(attivitaSingolare, bio.getSesso());
        }// end of if cycle

        if (professione != null) {
            professioneTxt = professione.getPagina();
        } else {
            professioneTxt = attivitaSingolare;
        }// end of if/else cycle
        if (!professioneTxt.equals(CostBio.VUOTO)) {
            paginaWiki = LibText.primaMaiuscola(professioneTxt);
        }// end of if cycle

        if (genere != null) {
            genereTxt = genere.getPlurale();
            if (!genereTxt.equals(CostBio.VUOTO)) {
                linkVisibile = LibText.primaMaiuscola(genereTxt);
            }// end of if cycle
        }// end of if cycle

        if (!paginaWiki.equals(CostBio.VUOTO) && !linkVisibile.equals(CostBio.VUOTO)) {
            titoloParagrafo = costruisceTitolo(paginaWiki, linkVisibile);
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo


    /**
     * Costruisce il link alla pagina linkata da inserire nel titolo del paragrafo
     */
    @Deprecated
    protected String getPaginaLinkata(Bio bio) {
        String paginaWiki = CostBio.VUOTO;
        Professione professione = null;
        String professioneTxt;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();
        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            professione = Professione.findBySingolare(attivitaSingolare);
        }// end of if cycle

        if (professione != null) {
            professioneTxt = professione.getPagina();
        } else {
            professioneTxt = attivitaSingolare;
        }// end of if/else cycle
        if (!professioneTxt.equals(CostBio.VUOTO)) {
            paginaWiki = LibText.primaMaiuscola(professioneTxt);
        }// end of if cycle

        return paginaWiki;
    }// fine del metodo

    /**
     * Costruisce il titolo visibile del paragrafo
     */
    @Deprecated
    protected String getTitoloPar(Bio bio) {
        String titoloParagrafo = tagParagrafoNullo;
        Genere genere = null;
        String genereTxt;
        Attivita attivita = null;
        String attivitaSingolare = CostBio.VUOTO;

        if (bio == null) {
            return CostBio.VUOTO;
        }// end of if cycle

        attivita = bio.getAttivitaPunta();
        if (attivita != null) {
            attivitaSingolare = attivita.getSingolare();
            genere = Genere.findBySingolareAndSesso(attivitaSingolare, bio.getSesso());
        }// end of if cycle

        if (genere != null) {
            genereTxt = genere.getPlurale();
            if (!genereTxt.equals(CostBio.VUOTO)) {
                titoloParagrafo = LibText.primaMaiuscola(genereTxt);
            }// end of if cycle
        }// end of if cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * La mappa delle biografie arriva non ordinata
     * Occorre spostare in basso il paragrafo vuoto
     * Occorre raggruppare i paragrafi con lo stesso link visibile
     * Occorre riordinare in base al link visibile
     * Sovrascritto
     */
    protected void ordinaMappaBiografie() {
        LinkedHashMap<String, HashMap> mappa;
        HashMap mappaTmp;
        ArrayList<String> keyList;
        ArrayList<String> lista;

        if (mappaBio == null) {
            return;
        }// end of if cycle

        mappa = new LinkedHashMap<String, HashMap>();
        keyList = new ArrayList<String>(mappaBio.keySet());
        keyList = LibArray.sort(keyList);
        for (String key : keyList) {
            mappa.put(key, mappaBio.get(key));
        }// end of for cycle
        mappaBio = mappa;

        if (mappaBio.containsKey(tagParagrafoNullo)) {
            mappa = mappaBio;
            mappaTmp = mappa.get(tagParagrafoNullo);
            mappa.remove(tagParagrafoNullo);
            mappa.put(tagParagrafoNullo, mappaTmp);
            mappaBio = mappa;
        }// end of if cycle

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
    private void elaboraPagina() {
        boolean debug = Pref.getBool(CostBio.USA_DEBUG, false);
        String summary = LibWiki.getSummary();
        String testo = CostBio.VUOTO;
        String titolo;

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
                titolo = PAGINA_PROVA;
                testo = titoloPagina + CostBio.A_CAPO + testo;
            } else {
                titolo = titoloPagina;
            }// fine del blocco if-else

            if (checkPossoRegistrare(titolo, testo)) {
                registrata = Api.scriveVoce(titolo, testo, summary);
            }// end of if cycle
        }// fine del blocco if

    }// fine del metodo

    /**
     * Controlla che la modifica sia sostanziale
     * <p>
     * Sovrascritto
     */
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        return false;
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

        // Avviso visibile solo in modifica
        text += elaboraAvvisoScrittura();

        // Posiziona il TOC
        testoIncluso += elaboraTOC();

        // Posiziona il ritorno
        testoIncluso += elaboraRitorno();

        // Posizione il template di avviso
        testoIncluso += elaboraTemplateAvviso();

        // Ritorno ed avviso vanno (eventualmente) protetti con 'include'
        text += elaboraInclude(testoIncluso);

        // Posiziona l'incipit della pagina
        text += CostBio.A_CAPO;
        text += elaboraIncipit();

        // valore di ritorno
        return text;
    }// fine del metodo

    /**
     * Corpo della pagina
     * Decide se c'è la doppia colonna
     * Controlla eventuali template di rinvio
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        boolean usaColonne = this.usaBodyDoppiaColonna;
        int maxRigheColonne = 10;//@todo mettere la preferenza

        if (mappaBio != null && mappaBio.size() > 0) {
            if (usaSuddivisioneParagrafi) {
                text = righeParagrafo();
            } else {
                if (usaBodyRigheMultiple) {
                    text = righeRaggruppate();
                } else {
                    text = righeSemplici();
                }// end of if/else cycle
            }// end of if/else cycle
        }// end of if cycle

        if (usaColonne && (numPersone > maxRigheColonne)) {
            text = LibWiki.listaDueColonne(text.trim());
        }// fine del blocco if

        if (usaBodyTemplate) {
            if (Pref.getBool(CostBio.USA_DEBUG, false)) {
                text = elaboraTemplate("") + text;
            } else {
                text = elaboraTemplate(text);
            }// end of if/else cycle
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Costruisce il paragrafo
     * Sovrascrivibile
     */
    protected String righeParagrafo() {
        String text = CostBio.VUOTO;
        int numVociParagrafo;
        HashMap<String, Object> mappa;
        String titoloParagrafo;
        String titoloSottopagina;
        String paginaLinkata;
        String titoloVisibile;
        List<Bio> lista;

        for (Map.Entry<String, HashMap> mappaTmp : mappaBio.entrySet()) {
            text += CostBio.A_CAPO;

            mappa = mappaTmp.getValue();

            if (usaOrdineAlfabeticoParagrafi) {
                titoloParagrafo = (String) mappa.get(KEY_MAP_PARAGRAFO_TITOLO);
            } else {
                titoloParagrafo = (String) mappa.get(KEY_MAP_PARAGRAFO_LINK);
            }// end of if/else cycle

            titoloVisibile = (String) mappa.get(KEY_MAP_PARAGRAFO_TITOLO);
            lista = (List<Bio>) mappa.get(KEY_MAP_LISTA);
            numVociParagrafo = lista.size();

//            titoloParagrafo = costruisceTitolo(paginaLinkata, titoloVisibile);
            if (Pref.getBool(CostBio.USA_NUMERI_PARAGRAFO, false)) {
                text += LibWiki.setParagrafo(titoloParagrafo, numVociParagrafo);
            } else {
                text += LibWiki.setParagrafo(titoloParagrafo);
            }// end of if/else cycle

            text += CostBio.A_CAPO;

            if (usaSottopagine && numVociParagrafo > maxVociParagrafo) {
                titoloSottopagina = titoloPagina + "/" + titoloVisibile;
                text += "{{Vedi anche|" + titoloSottopagina + "}}";
                creaSottopagina(mappa);
            } else {
                for (Bio bio : lista) {
                    text += CostBio.ASTERISCO;
                    text += bio.getDidascaliaListe();
                    text += CostBio.A_CAPO;
                }// end of for cycle
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo

    /**
     * Costruisce il titolo
     * Controlla se il titolo visibile (link) non esiste già
     * Se esiste, sostituisce la pagina (prima parte del titolo) con quella già esistente
     */
    protected String costruisceTitolo(String paginaWiki, String linkVisibile) {
        String titoloParagrafo = LibWiki.setLink(paginaWiki, linkVisibile);
        String link;

        if (linkVisibile.equals(tagParagrafoNullo)) {
            return linkVisibile;
        }// end of if cycle

        for (String keyCompleta : mappaBio.keySet()) {
            link = keyCompleta.substring(keyCompleta.indexOf("|") + 1);
            link = LibWiki.setNoQuadre(link);
            if (link.equals(linkVisibile)) {
                titoloParagrafo = keyCompleta;
                break;
            }// end of if cycle
        }// end of for cycle

        if (usaTitoloParagrafoConLink) {
            titoloParagrafo = LibBio.fixLink(titoloParagrafo);
        }// end of if/else cycle

        return titoloParagrafo;
    }// fine del metodo

    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
    }// fine del metodo

    /**
     * Raggruppa le biografie
     */
    protected String righeRaggruppate() {
        return CostBio.VUOTO;
    }// fine del metodo


    /**
     * Didascalia specifica della biografia
     * Sovrascritto
     */
    protected String getDidascalia(Bio bio) {
        return null;
    }// fine del metodo

    /**
     * Nessun raggruppamento
     */
    protected String righeSemplici() {
        String text = CostBio.VUOTO;
        ArrayList<String> lista;
        String key;
        HashMap mappa;

        for (Map.Entry<String, HashMap> mappaTmp : mappaBio.entrySet()) {
            lista = null;
            key = mappaTmp.getKey();
            mappa = (HashMap) mappaTmp.getValue();
            if (mappa != null) {
                lista = (ArrayList<String>) mappa.get(KEY_MAP_LISTA);
            }// end of if cycle

            if (lista.size() == 1) {
                text += CostBio.ASTERISCO;
                text += LibWiki.setQuadre(key);
                text += CostBio.TAG_SEPARATORE;
                text += lista.get(0);
                text += CostBio.A_CAPO;
            } else {
                try { // prova ad eseguire il codice
                    for (String didascalia : lista) {
                        text += CostBio.ASTERISCO;
                        text += LibWiki.setQuadre(key);
                        text += CostBio.TAG_SEPARATORE;
                        text += didascalia;
                        text += CostBio.A_CAPO;
                    }// end of for cycle
                } catch (Exception unErrore) { // intercetta l'errore
                    int a = 87;
                }// fine del blocco try-catch
            }// end of if/else cycle

        }// end of for cycle

        return text;
    }// fine del metodo

    /**
     * Trim iniziale
     * <p>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     */

    private String finale(String testoIn) {
        String testoOut = testoIn;

        // trim finale
        if (!testoIn.equals(CostBio.VUOTO)) {
            testoOut = testoIn.trim();
        }// fine del blocco if

        // valore di ritorno
        return testoOut;
    }// fine del metodo

    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Se non viene incapsulato, restituisce il testo in ingresso
     * Sovrascritto
     */
    protected String elaboraTemplate(String testoIn) {
        return testoIn;
    }// fine del metodo

    /**
     * Piede della pagina
     * Sovrascritto
     */
    protected String elaboraFooter() {
        return CostBio.VUOTO;
    }// fine del metodo

    /**
     * Avviso visibile solo in modifica
     * <p>
     * Non sovrascrivibile <br>
     */
    private String elaboraAvvisoScrittura() {
        String text = CostBio.VUOTO;

        if (usaHeadNonScrivere) {
            text += TAG_NON_SCRIVERE;
            text += CostBio.A_CAPO;
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Costruisce il TOC (tavola contenuti)
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo di una delle due possibilità <br>
     */
    private String elaboraTOC() {
        String text = CostBio.VUOTO;

        if (usaHeadToc) {
            if (usaHeadTocIndice) {
                text += TAG_INDICE;
            } else {
                text += TAG_NO_INDICE;
            }// fine del blocco if-else
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    private String elaboraRitorno() {
        String text = CostBio.VUOTO;
        String titoloPaginaMadre = getTitoloPaginaMadre();

        if (usaHeadRitorno) {
            if (!titoloPaginaMadre.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPaginaMadre;
                text = LibWiki.setGraffe(text);
            }// fine del blocco if
        }// fine del blocco if

        return text;
    }// fine del metodo

    /**
     * Titolo della pagina 'madre'
     * <p>
     * Sovrascritto
     */
    protected String getTitoloPaginaMadre() {
        return titoloPaginaMadre;
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

        if (usaHeadTemplateAvviso) {
            text += tagHeadTemplateAvviso;
            text += "|bio=";
            text += personeTxt;
            text += "|data=";
            text += dataCorrente.trim();
            text += "|progetto=";
            text += tagHeadTemplateProgetto;
            text = LibWiki.setGraffe(text);
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Incorpora il testo iniziale nel tag 'include'
     * <p>
     * Non sovrascrivibile <br>
     * Tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
     */
    private String elaboraInclude(String testoIn) {
        String testoOut = testoIn;

        if (usaHeadInclude) {
            testoOut = LibBio.setNoIncludeRiga(testoIn);
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

    protected int getNumPersone() {
        return numPersone;
    }// end of getter

    protected Object getOggetto() {
        return oggetto;
    }// end of getter method

    public boolean isRegistrata() {
        return registrata;
    }// end of getter method

}// fine della classe
