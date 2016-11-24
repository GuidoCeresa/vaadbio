package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gac on 31 ott 2016.
 * <p>
 * Crea la lista antroponima
 * <p>
 * Crea la lista dei nomi e la carica sul server wiki
 * Crea la lista dei cognomi e la carica sul server wiki
 */
public abstract class ListaAntroponimo extends ListaBio {

    /**
     * Costruttore senza parametri
     */
    protected ListaAntroponimo() {
    }// fine del costruttore

    /**
     * Costruttore completo
     */
    public ListaAntroponimo(Object oggetto) {
        super(oggetto);
    }// fine del costruttore


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();

        // head
        usaHeadTocIndice = true;
        usaHeadIncipit = true;
        tagHeadTemplateProgetto = "antroponimi";
        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = true;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 100);

        // footer
        usaFooterPortale = Pref.getBool(CostBio.USA_FOOTER_PORTALE_NOMI, true);
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            usaFooterCategorie = false;
        } else {
            usaFooterCategorie = Pref.getBool(CostBio.USA_FOOTER_CATEGORIE_NOMI, true);
        }// end of if/else cycle
    }// fine del metodo


    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    @Override
    protected String getChiaveParagrafo(Bio bio) {
        return LibBio.getChiavePerAttivita(bio, tagParagrafoNullo);
    }// fine del metodo

//    /**
//     * Costruisce una singola mappa
//     * Sovrascritto
//     */
//    @Override
//    protected void elaboraMappa(Bio bio) {
//        String didascalia;
//        ArrayList<String> lista;
//        String chiaveParagrafo;
//        HashMap<String, Object> mappa;
//
//        chiaveParagrafo = getTitoloParagrafo(bio);
//        didascalia = bio.getDidascaliaListe();
//
//        if (mappaBio.containsKey(chiaveParagrafo)) {
//            lista = (ArrayList<String>) mappaBio.get(chiaveParagrafo).get(KEY_MAP_LISTA);
//            lista.add(didascalia);
//        } else {
//            mappa = new HashMap<String, Object>();
//            lista = new ArrayList<>();
//            lista.add(didascalia);
//            mappa.put(KEY_MAP_LINK, getPaginaLinkata(bio));
//            mappa.put(KEY_MAP_TITOLO, getTitoloPar(bio));
//            mappa.put(KEY_MAP_LISTA, lista);
//            mappa.put(KEY_MAP_SESSO, bio.getSesso());
//            mappaBio.put(chiaveParagrafo, mappa);
//        }// end of if/else cycle
//    }// fine del metodo


    /**
     * Costruisce il titolo
     * Controlla se il titolo visibile (link) non esiste già
     * Se esiste, sostituisce la pagina (prima parte del titolo) con quella già esistente
     */
    protected String costruisceTitolo2(String paginaWiki, String linkVisibile) {
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
     * Costruisce il paragrafo
     * Sovrascrivibile
     */
    protected String righeParagrafo2() {
        String text = CostBio.VUOTO;
        int numVociParagrafo;
        String key;
        HashMap<String, Object> mappa;
        String titoloParagrafo;
        String titoloSottopagina;
        String paginaLinkata;
        String titoloVisibile;
        String sesso;
        List<Bio> lista;

        for (Map.Entry<String, HashMap> mappaTmp : mappaBio.entrySet()) {
            text += CostBio.A_CAPO;

            key = mappaTmp.getKey();
            mappa = (HashMap) mappaTmp.getValue();

            paginaLinkata = (String) mappa.get(KEY_MAP_LINK);
            titoloVisibile = (String) mappa.get(KEY_MAP_TITOLO);
            lista = (List<Bio>) mappa.get(KEY_MAP_LISTA);
            numVociParagrafo = lista.size();

            titoloParagrafo = costruisceTitolo(paginaLinkata, titoloVisibile);
            text += LibWiki.setParagrafo(titoloParagrafo);
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
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
    }// fine del metodo


    /**
     * Controlla che la modifica sia sostanziale
     * Se il flag è false, registra sempre
     * Se il flag è vero, controlla la differenza del testo
     * Sovrascritto
     */
    @Override
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA, false)) {
            return true;
        } else {
            return LibBio.checkModificaSostanziale(titolo, testo, tagHeadTemplateAvviso, "}}");
        }// end of if/else cycle
    }// fine del metodo


}// fine della classe
