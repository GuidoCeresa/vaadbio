package it.algos.vaadbio.lib;


import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Item;
import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaad.wiki.Page;
import it.algos.vaad.wiki.WikiLogin;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.wrapperbio.WrapBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.lib.LibText;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

//import it.algos.vaadbio.biolista.BioLista;
//import it.algos.vaadbio.biooriginale.BioOriginale;
//import it.algos.vaadbio.biovalida.BioValida;
//import it.algos.vaadbio.biowiki.BioWiki;

/**
 * Created by gac on 20 ago 2015.
 * .
 */
public abstract class LibBio {


    /**
     * tag per la singola quadra di apertura
     */
    public static final String QUADRA_INI = "\\[";

    /**
     * tag per le doppie quadre di apertura
     */
    public static final String QUADRE_INI = QUADRA_INI + QUADRA_INI;

    /**
     * tag per la singola quadra di chiusura
     */
    public static final String QUADRA_END = "\\]";

    /**
     * tag per le doppie quadre di chiusura
     */
    public static final String QUADRE_END = QUADRA_END + QUADRA_END;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_INI = "\\{";

    /**
     * tag per le doppie graffe di apertura
     */
    public static final String GRAFFE_INI = GRAFFA_INI + GRAFFA_INI;

    /**
     * tag per la singola graffa di apertura
     */
    public static final String GRAFFA_END = "\\}";

    /**
     * tag per le doppie graffe di chiusura
     */
    public static final String GRAFFE_END = GRAFFA_END + GRAFFA_END;

    /**
     * tag di apertura delle note
     */
    public static final String REF_INI = "<ref>";

    /**
     * tag di chiusura delle note
     */
    public static final String REF_END = "</ref>";

    /**
     * tag di chiusura delle note
     */
    public static final String REF_END_DUE = "<ref/>";

    /**
     * tag pipe
     */
    public static final String PIPE = "|";


    /**
     * Estrae una mappa chiave valore dal testo di un template
     * Presuppone che le righe siano separate da pipe e return
     * Controllo della parità delle graffe interne (nel metodo estraeTemplate)
     * Gestisce l'eccezione delle graffe interne (nel metodo getMappaReali)
     * Elimina un eventuale pipe iniziale in tutte le chiavi della mappa
     *
     * @param testoTemplate del template
     * @return mappa di TUTTI i parametri esistenti nel testo
     */
    public static LinkedHashMap getMappaReali(String testoTemplate) {
        return getMappaReali(testoTemplate, "");
    }// end of static method


    /**
     * Estrae una mappa chiave valore dal testo di un template
     * Presuppone che le righe siano separate da pipe e return
     * Controllo della parità delle graffe interne (nel metodo estraeTemplate)
     * Gestisce l'eccezione delle graffe interne (nel metodo getMappaReali)
     * Elimina un eventuale pipe iniziale in tutte le chiavi della mappa
     *
     * @param testoTemplate del template
     * @param titoloVoce    sulla wiki
     * @return mappa di TUTTI i parametri esistenti nel testo
     */
    public static LinkedHashMap getMappaReali(String testoTemplate, String titoloVoce) {
        LinkedHashMap mappa = null;

        if (!testoTemplate.equals("")) {
            mappa = LibBio.estraeTmpMappa(testoTemplate);
//            mappa = WikiLib.regolaMappaPipe(mappa);
//            mappa = WikiLib.regolaMappaGraffe(mappa);
        }// fine del blocco if

        //--avviso di errore
        if (mappa == null && !titoloVoce.equals("")) {
//            log.warn "getMappaRealiBio - La pagina ${titoloVoce}, non contiene ritorni a capo"
        }// fine del blocco if

        return mappa;
    }// end of static method

    /**
     * Estrae una mappa chiave valore per un fix di parametri, da una biografia
     * <p>
     * E impossibile sperare in uno schema fisso
     * Occorre considerare le {{ graffe annidate, i | (pipe) annidati
     * i mancati ritorni a capo, ecc., ecc.
     * <p>
     * Uso la lista dei parametri che può riconoscere
     * (è meno flessibile, ma più sicuro)
     * Cerco il primo parametro nel testo e poi spazzolo il testo per cercare
     * il primo parametro noto e così via
     *
     * @param bio istanza della Entity
     * @return mappa dei parametri esistenti nella enumeration e presenti nel testo
     */
    public static LinkedHashMap<String, String> getMappaBio(Bio bio) {
        return getMappaBio(bio.getTmplBioServer());
    }// end of static method

    /**
     * Estrae una mappa chiave valore per un fix di parametri, dal testo di una biografia
     * <p>
     * E impossibile sperare in uno schema fisso
     * Occorre considerare le {{ graffe annidate, i | (pipe) annidati
     * i mancati ritorni a capo, ecc., ecc.
     * <p>
     * Uso la lista dei parametri che può riconoscere
     * (è meno flessibile, ma più sicuro)
     * Cerco il primo parametro nel testo e poi spazzolo il testo per cercare
     * il primo parametro noto e così via
     *
     * @param testoTemplate del template Bio
     * @return mappa dei parametri esistenti nella enumeration e presenti nel testo
     */
    public static LinkedHashMap<String, String> getMappaBio(String testoTemplate) {
        LinkedHashMap<String, String> mappa = null;
        LinkedHashMap<Integer, String> mappaTmp = new LinkedHashMap<Integer, String>();
//        Collection lista = null;
        String chiave;
        String sep = PIPE;
        String sep2 = PIPE + " ";
        String spazio = " ";
        String uguale = "=";
        String tab = "\t";
        String valore = "";
        int pos = 0;
        int posUgu;
        ArrayList listaTag;
        int posEnd;

        if (testoTemplate != null && !testoTemplate.equals("")) {
            mappa = new LinkedHashMap();
            for (ParBio par : ParBio.values()) {
                valore = par.getTag();
                listaTag = new ArrayList();
                listaTag.add(sep + valore + spazio);
                listaTag.add(sep + valore + uguale);
                listaTag.add(sep + valore + tab);
                listaTag.add(sep + valore + spazio + uguale);
                listaTag.add(sep2 + valore + spazio);
                listaTag.add(sep2 + valore + uguale);
                listaTag.add(sep2 + valore + tab);
                listaTag.add(sep2 + valore + spazio + uguale);

                try { // prova ad eseguire il codice
                    pos = LibText.getPos(testoTemplate, listaTag);
                } catch (Exception unErrore) { // intercetta l'errore
//                    log.error testoTemplate
                }// fine del blocco try-catch
                if (pos > 0) {
                    mappaTmp.put(pos, valore);
                }// fine del blocco if
            } // fine del ciclo for-each

            Object[] matrice = mappaTmp.keySet().toArray();
            ArrayList lista = (ArrayList) LibArray.fromObj(matrice);
            lista = LibArray.sort(lista);
            for (int k = 1; k <= lista.size(); k++) {
                chiave = mappaTmp.get(lista.get(k - 1));

                try { // prova ad eseguire il codice
                    if (k < lista.size()) {
                        posEnd = (Integer) lista.get(k);
                    } else {
                        posEnd = testoTemplate.length();
                    }// fine del blocco if-else
                    valore = testoTemplate.substring((Integer) lista.get(k - 1), posEnd);
                } catch (Exception unErrore) { // intercetta l'errore
                    int c = 76;
                }// fine del blocco try-catch
                if (!valore.equals("")) {
                    valore = valore.trim();
                    posUgu = valore.indexOf(uguale);
                    if (posUgu != -1) {
                        posUgu += uguale.length();
                        valore = valore.substring(posUgu).trim();
                    }// fine del blocco if
                    valore = regValore(valore);
                    if (!LibBio.isPariTag(valore, "{{", "}}")) {
                        valore = regGraffe(valore);
                    }// end of if cycle
                    valore = regACapo(valore);
                    valore = regBreakSpace(valore);
                    valore = valore.trim();
                    mappa.put(chiave, valore);
                }// fine del blocco if
            } // fine del ciclo for


//            for (int chiave : mappaTmp.keySet()) {
////                chiave = (Integer)mappaTmp.get(posizione - 1);
//                valore = testoTemplate.substring(chiave, chiave + mappaTmp.get(chiave).length());
//                if (!valore.equals("")) {
//                    valore = valore.trim();
//                    posUgu = valore.indexOf(uguale);
//                    if (posUgu != -1) {
//                        posUgu += uguale.length();
//                        valore = valore.substring(posUgu).trim();
//                    }// fine del blocco if
//                    valore = regValore(valore);
//                    valore = regACapo(valore);
//                    valore = regBreakSpace(valore);
//                    valore = valore.trim();
//                    mappa.put(chiave, valore);
//                }// fine del blocco if
//
//            } // fine del ciclo for-each


//            HashMap.Entry lis = mappaTmp.keySet();
//            if (lista != null) {
//                lista.add(testoTemplate.length());
//
//                for (int k = 1; k < lista.size(); k++) {
//                    chiave = mappaTmp.get(lista.get(k - 1));
//                    valore = testoTemplate.substring((int) lista.get(k - 1), (int) lista.get(k));
//                    if (!valore.equals("")) {
//                        valore = valore.trim();
//                        posUgu = valore.indexOf(uguale);
//                        if (posUgu != -1) {
//                            posUgu += uguale.length();
//                            valore = valore.substring(posUgu).trim();
//                        }// fine del blocco if
//                        valore = regValore(valore);
//                        valore = regACapo(valore);
//                        valore = regBreakSpace(valore);
//                        valore = valore.trim();
//                        mappa.put(chiave, valore);
//                    }// fine del blocco if
//
//                } // fine del ciclo for
//            }// fine del blocco if
        }// fine del blocco if

//        mappa = WikiLib.regolaMappaPipe(mappa);
//        mappa = WikiLib.regolaMappaGraffe(mappa);

        return mappa;
    } // fine del metodo


    /**
     * Estrae la mappa chiave/valore di un template dal testo completo di una voce
     * Gli estremi sono ESCLUSI
     * <p>
     * Recupera il tag iniziale con o senza ''Template''
     * Recupera il tag finale con o senza ritorno a capo precedente
     * Controlla che non esistano doppie graffe dispari all'interno del template
     *
     * @param testoEssenziale testo essenziale del template, estremi esclusi
     * @return mappa chiave/valore
     */
    public static LinkedHashMap estraeTmpMappa(String testoEssenziale) {
        LinkedHashMap mappa;
        String tagIni = "{{";

        if (testoEssenziale.startsWith(tagIni)) {
            testoEssenziale = estraeTmpRaw(testoEssenziale);
        }// fine del blocco if
        mappa = estraeMappaReali(testoEssenziale);

        return mappa;
    }// end of static method

    /**
     * Estrae il testo essenziale di un template dal testo completo di una voce
     * Gli estremi sono ESCLUSI
     * <p>
     * Il template DOVREBBE iniziare con {{Bio aCapo |
     * Il template DOVREBBE terminare con }} aCapo (eventuale)
     * Elimina doppie graffe iniziali e nome del template in modo che il raw parta col pipe
     * Elimina doppie graffe finali e aCapo (eventuale) finale
     *
     * @param testoTemplate testo completo del template
     * @return testoEssenziale testo essenziale del template, estremi esclusi
     */
    public static String estraeTmpRaw(String testoTemplate) {
        String testoEssenziale = "";
        String testoDopoBio;
        String tagBio = "Bio";
        String tagEnd = "}}";
        int posBioEnd;
        int pos;

        posBioEnd = testoTemplate.indexOf(tagBio) + tagBio.length();
        testoDopoBio = testoTemplate.substring(posBioEnd);
        pos = testoDopoBio.indexOf(PIPE);
        if (pos != -1) {
            testoEssenziale = testoDopoBio.substring(pos);
            testoEssenziale = LibText.levaCoda(testoEssenziale, tagEnd);
        }// fine del blocco if-else

        return testoEssenziale;
    }// end of static method


    /**
     * Estrae una mappa chiave valore dal testo del template
     * Presuppone che le righe siano separate da pipe e return
     * Controlla che non ci siano doppie graffe annidate nel valore dei parametri
     *
     * @param testoTemplate testo completo del template
     * @return mappa chiave/valore
     */
    public static LinkedHashMap estraeMappaReali(String testoTemplate) {
        LinkedHashMap mappa = null;
        LinkedHashMap mappaGraffe = null;
        boolean continua = false;
        String sep = PIPE;
        String sepRE = "\n\\|";
        String uguale = "=";
        String[] righe = null;
        String chiave;
        String valore;
        int pos;

        if (!testoTemplate.equals("")) {
            continua = true;
        }// fine del blocco if

        if (continua) {
            mappaGraffe = checkGraffe(testoTemplate);
            if (mappaGraffe.containsKey("isGraffe")) {
                testoTemplate = (String) mappaGraffe.get("testo");
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            if (testoTemplate.startsWith(sep)) {
                testoTemplate = testoTemplate.substring(1).trim();
            }// fine del blocco if

            righe = testoTemplate.split(sepRE);
            if (righe.length == 1) {
                mappa = getMappaRigaUnica(testoTemplate);
                continua = false;
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            if (righe != null) {
                mappa = new LinkedHashMap();

                for (String stringa : righe) {
                    pos = stringa.indexOf(uguale);
                    if (pos != -1) {
                        chiave = stringa.substring(0, pos).trim();
                        valore = stringa.substring(pos + 1).trim();
                        if (!chiave.equals("")) {
                            mappa.put(chiave, valore);
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each

            }// fine del blocco if
        }// fine del blocco if

        // reinserisce il contenuto del parametro che eventualmente avesse avuto le doppie graffe
        if (continua) {
            if (mappaGraffe.containsKey("isGraffe")) {
                if ((Integer) mappaGraffe.get("numGraffe") == 1) {
                    chiave = (String) mappaGraffe.get("nomeParGraffe");
                    valore = (String) mappaGraffe.get("valParGraffe");
                    mappa.put(chiave, valore);
                } else {
                    for (int k = 0; k < (Integer) mappaGraffe.get("numGraffe"); k++) {
//                        chiave = mappaGraffe.nomeParGraffe[k];
//                        valore = mappaGraffe.valParGraffe[k];
//                        mappa.put(chiave, valore);
                    } // fine del ciclo for
                }// fine del blocco if-else
            }// fine del blocco if
        }// fine del blocco if

        return mappa;
    }// end of static method


    /**
     * Controlla le graffe interne al testo
     * <p>
     * Casi da controllare (all'interno delle graffe principali, già eliminate):
     * 1-...{{..}}...               (singola)
     * 2-...{{}}...                 (vuota)
     * 3-...{{..}}                  (terminale)
     * 4-...{{..{{...}}...}}...     (interna)
     * 5-...{{..}}...{{...}}...     (doppie)
     * 6-..{{..}}..{{..}}..{{...}}..(tre o più)
     * 7-..{{..}}..|..{{..}}        (due in punti diversi)
     * 8-..{{...|...}}              (pipe interni)
     * <p>
     * Se la graffe esistono, restituisce:
     * testo = testo depurate delle graffe
     * valGraffe = valore del contenuto delle graffe                (stringa o arry di stringhe)
     * nomeParGraffe = nome del parametro interessato               (stringa o arry di stringhe)
     * valParGraffe = valore completo del parametro che le contiene (stringa o arry di stringhe)
     * isGraffe = boolean          //se esistono
     * numGraffe = quante ce ne sono
     */
    public static LinkedHashMap checkGraffe(String testoTemplate) {
        LinkedHashMap mappa = null;
        boolean continua = false;
        String tagIni = "{{";
        String tagEnd = "}}";

        mappa = new LinkedHashMap();
        mappa.put("isGraffe", false);
        mappa.put("testo", testoTemplate);
        mappa.put("numGraffe", 0);
        mappa.put("valGraffe", "");
        mappa.put("nomeParGraffe", "");
        mappa.put("valParGraffe", "");

        if (!testoTemplate.equals("")) {
            continua = true;
        }// fine del blocco if

        // controllo di esistenza delle graffe
        if (continua) {
            if (testoTemplate.contains(tagIni) && testoTemplate.contains(tagEnd)) {
                mappa.put("isGraffe", true);
            } else {
                continua = false;
            }// fine del blocco if-else
        }// fine del blocco if

        // spazzola il testo per ogni coppia di graffe
        if (continua) {
            while (testoTemplate.contains(tagIni) && testoTemplate.contains(tagEnd)) {
                testoTemplate = LibBio.levaGraffa(mappa, testoTemplate);
            } //fine del ciclo while
        }// fine del blocco if

        return mappa;
    }// fine del metodo


    /**
     * Elabora ed elimina le prime graffe del testo
     * Regola la mappa
     * Restituisce il testo depurato delle prime graffe per ulteriore elaborazione
     *
     * @param testoTemplate testo completo del template
     */
    public static String levaGraffa(HashMap mappa, String testoTemplate) {
        String testoElaborato = "";
        boolean continua = false;
        String tagIni = "{{";
        String tagEnd = "}}";
        int posIni;
        int posEnd;
        String testoGraffa = "";

        if (mappa != null && !testoTemplate.equals("")) {
            testoElaborato = testoTemplate;
            continua = true;
        }// fine del blocco if

        // controllo di esistenza delle graffe
        if (continua) {
            if (testoTemplate.contains(tagIni) && testoTemplate.contains(tagEnd)) {
            } else {
                continua = false;
            }// fine del blocco if-else
        }// fine del blocco if

        // controllo (non si sa mai) che le graffe siano nell'ordine giusto
        if (continua) {
            posIni = testoTemplate.indexOf(tagIni);
            posEnd = testoTemplate.indexOf(tagEnd);
            if (posIni > posEnd) {
                continua = false;
            }// fine del blocco if
        }// fine del blocco if

        //spazzola il testo fino a pareggiare le graffe
        if (continua) {
            posIni = testoTemplate.indexOf(tagIni);
            posEnd = testoTemplate.indexOf(tagEnd, posIni);
            testoGraffa = testoTemplate.substring(posIni, posEnd + tagEnd.length());
            while (!isPariTag(testoGraffa, tagIni, tagEnd)) {
                posEnd = testoTemplate.indexOf(tagEnd, posEnd + tagEnd.length());
                if (posEnd != -1) {
                    testoGraffa = testoTemplate.substring(posIni, posEnd + tagEnd.length());
                } else {
                    mappa.put("isGraffe", false);
                    break;
                }// fine del blocco if-else
            } //fine del ciclo while
        }// fine del blocco if

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            testoElaborato = regolaMappa(mappa, testoTemplate, testoGraffa);
        }// fine del blocco if

        return testoElaborato;
    }// fine del metodo

    /**
     * Elabora il testo della singola graffa
     * Regola la mappa
     */
    public static String regolaMappa(HashMap mappa, String testoTemplate, String testoGraffa) {
        String testoElaborato = "";
        boolean continua = false;
        ArrayList arrayValGraffe;
        ArrayList arrayNomeParGraffe;
        ArrayList arrayvValParGraffe;
        String valGraffe;
        String testoOut;
        String valParGraffe = "";
        String nomeParGraffe = "";
        String valRiga;
        String tagIni = "{{";
        String tagEnd = "}}";
        int posIni = 0;
        int posEnd = 0;
        String sep2 = "\n|";
        String txt = "";
        String sepParti = "=";
        String[] parti;
        int lenTemplate = 0;
        int numGraffe;
        String testo;

        // controllo di congruità
        if (mappa != null && !testoTemplate.equals("") && !testoGraffa.equals("")) {
            testoElaborato = LibText.sostituisce(testoTemplate, testoGraffa, "");
            continua = true;
        }// fine del blocco if

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            posIni = testoTemplate.indexOf(testoGraffa);
            posIni = testoTemplate.lastIndexOf(sep2, posIni);
            posIni += sep2.length();
            posEnd = testoTemplate.indexOf(sep2, posIni + testoGraffa.length());
            if (posIni == -1) {
                continua = false;
            }// fine del blocco if
            if (posEnd == -1) {
                posEnd = testoTemplate.length();
            }// fine del blocco if
        }// fine del blocco if

        //estrae i dati rilevanti per la mappa
        //inserisce i dati nella mappa
        if (continua) {
            valRiga = testoTemplate.substring(posIni, posEnd);
            posIni = valRiga.indexOf(sepParti);
            //nomeParGraffe = valRiga.substring(0, posIni).trim()
            //valParGraffe = valRiga.substring(posIni + sepParti.length()).trim()
            if (posIni != -1) {
                nomeParGraffe = valRiga.substring(0, posIni).trim();
                valParGraffe = valRiga.substring(posIni + sepParti.length()).trim();
            } else {
                continua = false;
            }// fine del blocco if-else
        }// fine del blocco if

        numGraffe = (Integer) mappa.get("numGraffe");
        numGraffe++;
        switch (numGraffe) {
            case 1:
                mappa.put("valGraffe", testoGraffa);
                mappa.put("nomeParGraffe", nomeParGraffe);
                mappa.put("valParGraffe", valParGraffe);
                break;
            case 2:
                arrayValGraffe = new ArrayList();
                String oldValGraffe;
                oldValGraffe = (String) mappa.get("valGraffe");
                arrayValGraffe.add(oldValGraffe);
                arrayValGraffe.add(testoGraffa);
                mappa.put("valGraffe", arrayValGraffe);

                arrayNomeParGraffe = new ArrayList();
                String oldNomeParGraffe;
                oldNomeParGraffe = (String) mappa.get("nomeParGraffe");
                arrayNomeParGraffe.add(oldNomeParGraffe);
                arrayNomeParGraffe.add(nomeParGraffe);
                mappa.put("nomeParGraffe", arrayNomeParGraffe);

                arrayvValParGraffe = new ArrayList();
                String oldValParGraffe;
                oldValParGraffe = (String) mappa.get("valParGraffe");
                arrayvValParGraffe.add(oldValParGraffe);
                arrayvValParGraffe.add(valParGraffe);
                mappa.put("valParGraffe", arrayvValParGraffe);
                break;
            default: // caso non definito
                arrayValGraffe = (ArrayList) mappa.get("valGraffe");
                arrayValGraffe.add(testoGraffa);
                mappa.put("valGraffe", arrayValGraffe);

                arrayNomeParGraffe = (ArrayList) mappa.get("nomeParGraffe");
                arrayNomeParGraffe.add(nomeParGraffe);
                mappa.put("nomeParGraffe", arrayNomeParGraffe);

                arrayvValParGraffe = (ArrayList) mappa.get("valParGraffe");
                arrayvValParGraffe.add(valParGraffe);
                mappa.put("valParGraffe", arrayvValParGraffe);
                break;
        } // fine del blocco switch

        mappa.put("numGraffe", numGraffe);
        mappa.put("testo", testoElaborato);

        return testoElaborato;
    }// fine della closure

    /**
     * Estrae una mappa chiave/valore dal testo contenuto tutto in una riga
     * Presuppone che la riga sia unica ed i parametri siano separati da pipe
     *
     * @param testo
     * @return mappa chiave/valore
     */
    public static LinkedHashMap getMappaRigaUnica(String testo) {
        LinkedHashMap mappa = null;
        boolean continua = false;
        String sepRE = "\\|";
        String uguale = "=";
        String[] righe;
        String chiave;
        String valore;
        int pos;

        if (!testo.equals("")) {
            continua = true;
        }// fine del blocco if

        if (continua) {
            righe = testo.split(sepRE);
            if (righe != null && righe.length > 0) {
                mappa = new LinkedHashMap();

                for (String stringa : righe) {
                    pos = stringa.indexOf(uguale);
                    if (pos != -1) {
                        chiave = stringa.substring(0, pos).trim();
                        valore = stringa.substring(pos + 1).trim();
                        if (!chiave.equals("")) {
                            mappa.put(chiave, valore);
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each

            }// fine del blocco if
        }// fine del blocco if

        return mappa;
    }// fine della closure


    /**
     * Controlla che le occorrenze dei due tag si pareggino all'interno del testo.
     *
     * @param testo  di riferimento
     * @param tagIni di apertura
     * @param tagEnd di chiusura
     * @return vero se il numero di tagIni è uguale al numero di tagEnd
     */
    public static boolean isPariTag(String testo, String tagIni, String tagEnd) {
        boolean pari = false;
        int numIni;
        int numEnd;

        if (!testo.equals("") && !tagIni.equals("") && !tagEnd.equals("")) {
            numIni = getNumTag(testo, tagIni);
            numEnd = getNumTag(testo, tagEnd);
            pari = (numIni == numEnd);
        }// fine del blocco if

        return pari;
    } // fine del metodo

    /**
     * Controlla se nel testo ci sono occorrenze pari delle graffe.
     * Le graffe devono anche essere nel giusto ordine
     *
     * @param testo in ingresso
     * @return vero se le occorrenze di apertura e chiusura sono uguali
     */
    public static boolean isPariGraffe(String testo) {
        return isPariTag(testo, GRAFFE_INI, GRAFFE_END);
    } // fine del metodo


    /**
     * Controlla se nel testo ci sono occorrenze pari delle quadre.
     * Le graffe devono anche essere nel giusto ordine
     *
     * @param testo in ingresso
     * @return vero se le occorrenze di apertura e chiusura sono uguali
     */
    public static boolean isPariQuadre(String testo) {
        return isPariTag(testo, QUADRE_INI, QUADRE_END);
    } // fine del metodo


    /**
     * Restituisce il valore di occorrenze del tag nel testo.
     *
     * @param testo da analizzare
     * @param tag   da cercare
     * @return numero di occorrenze
     * zero se non ce ne sono
     */
    public static int getNumTag(String testo, String tag) {
        int numTag = 0;
        int pos;

        if (!testo.equals("") && !tag.equals("")) {
            if (testo.contains(tag)) {
                pos = testo.indexOf(tag);
                while (pos != -1) {
                    pos = testo.indexOf(tag, pos + tag.length());
                    numTag++;
                }// fine di while
            } else {
                numTag = 0;
            }// fine del blocco if-else
        }// fine del blocco if

        return numTag;
    } // fine del metodo


    /**
     * Regola una mappa chiave
     * Elimina un eventuale pipe iniziale in tutte le chiavi
     *
     * @param mappaIn dei parametri in entrata
     * @return mappa dei parametri in uscita
     */
    public static LinkedHashMap regolaMappaPipe(Map mappaIn) {
        LinkedHashMap mappaOut = null;
        String chiave;
        String valore;
        String pipe = "|";
        int pos;

        if (mappaIn != null && mappaIn.size() > 0) {
            mappaOut = new LinkedHashMap();

            for (Object key : mappaIn.keySet()) {
                chiave = (String) key;
                valore = (String) mappaIn.get(key);
                if (chiave.startsWith(pipe)) {
                    pos = chiave.lastIndexOf(pipe);
                    pos++;
                    chiave = chiave.substring(pos);
                }// fine del blocco if
                mappaOut.put(chiave, valore);
            } // fine del ciclo for-each

        }// fine del blocco if

        return mappaOut;
    } // fine del metodo

    /**
     * Elimina il testo se inizia con un pipe
     */
    public static String regValore(String valoreIn) {
        String valoreOut = valoreIn;
        String pipe = "|";

        if (valoreIn.startsWith(pipe)) {
            valoreOut = "";
        }// fine del blocco if

        return valoreOut.trim();
    } // fine del metodo

    /**
     * Elimina le graffe finali
     */
    public static String regGraffe(String valoreIn) {
        String valoreOut = valoreIn;
        String graffe = "}}";

        if (valoreIn.endsWith(graffe)) {
            valoreOut = LibText.levaCoda(valoreIn, graffe);
        }// fine del blocco if

        return valoreOut.trim();
    } // fine del metodo


    /**
     * Controlla il primo aCapo che trova:
     * - se è all'interno di doppie graffe, non leva niente
     * - se non ci sono dopppie graffe, leva dopo l' aCapo
     */
    public static String regACapo(String valoreIn) {
        String valoreOut = valoreIn;
        String aCapo = "\n";
        String doppioACapo = aCapo + aCapo;
        String pipeACapo = aCapo + "|";
        int pos;
        HashMap mappaGraffe;

        if (!valoreIn.equals("") && valoreIn.contains(doppioACapo)) {
            valoreOut = valoreOut.replace(doppioACapo, aCapo);
        }// fine del blocco if

        if (!valoreIn.equals("") && valoreIn.contains(pipeACapo)) {
            mappaGraffe = LibBio.checkGraffe(valoreIn);

            if (mappaGraffe.containsKey("isGraffe")) {
            } else {
                pos = valoreIn.indexOf(pipeACapo);
                valoreOut = valoreIn.substring(0, pos);
            }// fine del blocco if-else
        }// fine del blocco if

        return valoreOut.trim();
    } // fine del metodo

    /**
     * Elimina un valore strano trovato (ed invisibile)
     * ATTENZIONE: non è uno spazio vuoto !
     * Trattasi del carattere: C2 A0 ovvero U+00A0 ovvero NO-BREAK SPACE
     * Non viene intercettato dal comando Java TRIM()
     */
    public static String regBreakSpace(String valoreIn) {
        String valoreOut = valoreIn;
        String strano = " ";   //NON cancellare: sembra uno spazio, ma è un carattere invisibile

        if (valoreIn.startsWith(strano)) {
            valoreOut = valoreIn.substring(1);
        }// fine del blocco if

        if (valoreIn.endsWith(strano)) {
            valoreOut = valoreIn.substring(0, valoreIn.length() - 1);
        }// fine del blocco if

        return valoreOut.trim();
    } // fine del metodo

    /**
     * Cancella un record
     * <p>
     * Cancella il record di BioWiki
     * Cancella (se esiste) anche il corrispondente record (medesimo pageid) di BioOriginale
     * Cancella (se esiste) anche il corrispondente record (medesimo pageid) di BioValida
     * Cancella (se esiste) anche il corrispondente record (medesimo pageid) di BioLista
     *
     * @param pageid della voce
     * @deprecated
     */
    public static boolean delete(long pageid) {
        boolean status = false;
//        BioWiki bioWiki;
//        BioOriginale bioOriginale;
//        BioValida bioValida;
//        BioLista bioLista;
//
//        bioWiki = BioWiki.findPageid(pageid);
//        if (bioWiki != null) {
//            bioWiki.delete();
//        }// fine del blocco if
//
//        bioOriginale = BioOriginale.findPageid(pageid);
//        if (bioOriginale != null) {
//            bioOriginale.delete();
//        }// fine del blocco if
//
//        bioValida = BioValida.findPageid(pageid);
//        if (bioValida != null) {
//            bioValida.delete();
//        }// fine del blocco if
//
//        bioLista = BioLista.findPageid(pageid);
//        if (bioLista != null) {
//            bioLista.delete();
//        }// fine del blocco if

        return status;
    } // fine del metodo

    /**
     * Restituisce il login registrato nella sessione
     */
    public static WikiLogin getLogin() {
        WikiLogin wikiLogin = null;
        Object obj;

        obj = LibSession.getAttribute(WikiLogin.WIKI_LOGIN_KEY_IN_SESSION);
        if (obj != null && obj instanceof WikiLogin) {
            wikiLogin = (WikiLogin) obj;
        }// end of if cycle

        return wikiLogin;
    } // fine del metodo

    /**
     * Controllla che il login esista e sia loggato come bot
     */
    public static boolean isLoggatoBot() {
        boolean status = false;
        WikiLogin wikiLogin = getLogin();

        if (wikiLogin != null && wikiLogin.isValido() && wikiLogin.isBot()) {
            status = true;
        }// end of if cycle

        return status;
    } // fine del metodo

    /**
     * Il ciclo necessita del login come bot per il funzionamento normale
     * <p>
     * Se è loggato come bot, ritorna true
     * Se non nè loggato o non è loggato come bot, controlla il flag USA_CICLI_ANCHE_SENZA_BOT
     * se è true, usa il limite normale (non bot) di mediawiki di 50 pagine
     * se è false, ritorna true e NON esegue il ciclo
     */
    public static boolean checkLoggin() {
        if (LibBio.isLoggatoBot()) {
            return true;
        } else {
            if (Pref.getBool(CostBio.USA_CICLI_ANCHE_SENZA_BOT, true)) {
                return true;
            } else {
                return false;
            }// end of if/else cycle
        }// end of if/else cycle
    }// end of static method

    /**
     * Regola la lunghezza del campo
     * <p>
     * Elimina il teasto successivo al ref
     * Elimina il testo successivo alle note
     * Elimina il testo successivo alle graffe
     * Elimina il testo successivo alla virgola
     * Elimina il testo successivo al punto interrogativo
     * Elimina eventuali quadre
     * Tronca comunque il testo a 255 caratteri
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     */
    public static String fixCampoLuogo(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = LibText.levaDopoRef(testoOut);
            testoOut = LibText.levaDopoNote(testoOut);
            testoOut = LibText.levaDopoGraffe(testoOut);
            testoOut = LibText.levaDopoVirgola(testoOut);
            testoOut = LibText.levaDopoInterrogativo(testoOut);
            testoOut = LibBio.setNoQuadre(testoOut);
            testoOut = testoOut.trim();
        }// fine del blocco if

        if (testoOut != null && testoOut.length() > 253) {
            testoOut = testoOut.substring(0, 252);
        }// fine del blocco if

        return testoOut;
    }// end of static method

    /**
     * Regola la lunghezza del campo
     * <p>
     * Elimina il teasto successivo al ref
     * Elimina il testo successivo alle note
     * Elimina il testo successivo alle graffe
     * Elimina il testo successivo alla virgola
     * Elimina il testo successivo al punto interrogativo
     * Elimina eventuali quadre
     * Tronca comunque il testo a 255 caratteri
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     */
    public static String fixCampoLuogoLink(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = LibText.levaDopoRef(testoOut);
            testoOut = LibText.levaDopoNote(testoOut);
            testoOut = LibText.levaDopoGraffe(testoOut);
            testoOut = LibText.levaDopoInterrogativo(testoOut);
            testoOut = LibBio.setNoQuadre(testoOut);
            testoOut = testoOut.trim();
        }// fine del blocco if

        if (testoOut != null && testoOut.length() > 253) {
            testoOut = testoOut.substring(0, 252);
        }// fine del blocco if

        return testoOut;
    }// end of static method

    /**
     * Regola la lunghezza del campo
     * <p>
     * Elimina il teasto successivo al ref
     * Elimina il testo successivo alle note
     * Elimina il testo successivo alle graffe
     * Elimina il testo successivo alla virgola
     * Elimina il testo successivo alla parentesi
     * Elimina eventuali quadre
     * Tronca comunque il testo a 255 caratteri
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     */
    public static String fixCampo(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = LibText.levaDopoParentesi(testoOut);
            testoOut = fixCampoLuogo(testoOut);
        }// fine del blocco if

        return testoOut;
    }// end of static method

    /**
     * Elimina il testo successivo alla virgola
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     */
    public static String fixCampoSesso(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = fixCampo(testoOut);
            testoOut = testoOut.trim();
        }// fine del blocco if

        if (testoOut != null && !testoOut.equals("M") && !testoOut.equals("F")) {
            testoOut = "";
        }// end of if cycle

        return testoOut;
    }// end of static method

    /**
     * Regola questo campo
     * <p>
     * Elimina il teasto successivo al ref
     * Elimina il testo successivo alle note
     * Elimina il testo successivo alle graffe
     * Elimina il testo successivo alla virgola
     * Elimina il testo successivo al punto interrogativo
     * Elimina eventuali quadre
     * Tronca comunque il testo a 255 caratteri
     *
     * @param testoGrezzo in entrata da elaborare
     * @return testoValido regolato in uscita
     */
    private static String fixCampoBase(String testoGrezzo) {
        String testoValido;

        if (testoGrezzo == null || testoGrezzo.equals(CostBio.VUOTO)) {
            return CostBio.VUOTO;
        }// end of if cycle

        testoValido = testoGrezzo.trim();
        testoValido = LibText.levaDopoRef(testoValido);
        testoValido = LibText.levaDopoNote(testoValido);
        testoValido = LibText.levaDopoGraffe(testoValido);
        testoValido = LibText.levaDopoVirgola(testoValido);
        testoValido = LibText.levaDopoInterrogativo(testoValido);
        testoValido = LibBio.setNoQuadre(testoValido);
        testoValido = testoValido.trim();

        if (testoValido.length() > 253) {
            testoValido = testoValido.substring(0, 252);
        }// fine del blocco if

        return testoValido;
    }// end of static method

    /**
     * Regola questo campo
     * <p>
     * Elimina il testo successivo a varii tag
     * Elimina il testo se NON contiene una spazio vuoto (tipico della data giorno-mese)
     * Elabora una patch specifica del Giorno
     *
     * @param testoGrezzo in entrata da elaborare
     * @return testoValido regolato in uscita
     */
    public static String fixGiornoValido(String testoGrezzo) {
        String testoValido = "";

        if (testoGrezzo == null || testoGrezzo.equals(CostBio.VUOTO) || !testoGrezzo.contains(CostBio.SPAZIO)) {
            return CostBio.VUOTO;
        }// end of if cycle

        testoValido = LibBio.fixCampoBase(testoGrezzo);
        testoValido = Giorno.fix(testoValido);

        return testoValido.trim();
    } // fine del metodo

    /**
     * Regola questo campo
     * <p>
     * Elimina il testo successivo a varii tag
     *
     * @param testoGrezzo in entrata da elaborare
     * @return testoValido regolato in uscita
     */
    public static String fixAnnoValido(String testoGrezzo) {
        String testoValido = "";
        String tagCirca = "circa";

        if (testoGrezzo == null || testoGrezzo.equals(CostBio.VUOTO)) {
            return CostBio.VUOTO;
        }// end of if cycle

        if (testoGrezzo.contains(tagCirca)) {
            return CostBio.VUOTO;
        }// end of if cycle

        testoValido = LibBio.fixCampoBase(testoGrezzo);

        return testoValido.trim();
    } // fine del metodo

    /**
     * Elimina il testo successivo alla virgola
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     * @deprecated
     */
    public static String fixCampoGiorno(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = LibText.levaDopoInterrogativo(testoOut);
            testoOut = testoOut.trim();
        }// fine del blocco if

        return testoOut;
    }// end of static method

    /**
     * Elimina il testo successivo alla virgola
     *
     * @param testoIn entrata da elaborare
     * @return testoOut regolato in uscita
     */
    public static String fixCampoAnno(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            testoOut = testoOut.trim();
            testoOut = LibText.levaDopoInterrogativo(testoOut);
            testoOut = testoOut.trim();
        }// fine del blocco if

        return testoOut;
    }// end of static method

    /**
     * Elimina (eventuali) doppie quadre in testa e coda della stringa.
     * <p>
     * Funziona solo se le quadre sono esattamente in TESTA ed in CODA alla stringa
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn entrata da elaborare
     * @return testoIn regolato in uscita con doppie quadre eliminate
     */
    public static String setNoQuadre(String testoIn) {
        String testoOut = testoIn;

        if (testoOut != null) {
            if (testoIn.length() > 0) {
                testoOut = testoIn.trim();
                testoOut = LibText.levaTesta(testoOut, QUADRA_INI);
                testoOut = LibText.levaTesta(testoOut, QUADRA_INI);
                testoOut = LibText.levaCoda(testoOut, QUADRA_END);
                testoOut = LibText.levaCoda(testoOut, QUADRA_END);
                testoOut = testoOut.trim();
            }// fine del blocco if
        }// fine del blocco if

        return testoOut;
    }// end of static method

    public static long getPageid(Item item) {
        if (item != null && item instanceof JPAContainerItem) {
            return getPageid((JPAContainerItem) item);
        }// end of if cycle

        return 0;
    }// end of static method

    public static long getPageid(JPAContainerItem item) {
        long pageid = 0;
        Object entity = null;
        Bio bio = null;

        if (item != null) {
            entity = item.getEntity();
        }// end of if cycle

        if (entity != null) {
            if (entity instanceof Bio) {
                bio = (Bio) entity;
                pageid = bio.getPageid();
            }// end of if cycle
        }// end of if cycle

        return pageid;
    }// end of static method

    public static String getTitle(Item item) {
        if (item != null && item instanceof JPAContainerItem) {
            return getTitle((JPAContainerItem) item);
        }// end of if cycle

        return "";
    }// end of static method

    public static String getTitle(JPAContainerItem item) {
        String title = "";
        Object entity = null;
        Bio bio = null;

        if (item != null) {
            entity = item.getEntity();
        }// end of if cycle

        if (entity != null) {
            if (entity instanceof Bio) {
                bio = (Bio) entity;
                title = bio.getTitle();
            }// end of if cycle
        }// end of if cycle

        return title;
    }// end of static method

    /**
     * Confronta il template risultante
     * <p>
     * Costruisce un template 'corretto' e lo confronta con quello attuale
     * A seconda del flag, considera il template presente sul server, oppure quello riformulato in BioOriginale
     * La differenza sono gli spazi e l'ordine dei parametri, mentre i valori sono gli stessi in entrambi i casi
     */
    public static boolean isTemplateDiversi(long pageid) {
        boolean status = true;
//        BioWiki bioWiki;
//        BioOriginale bioOrig;
        String tmplBioWiki = "";
        String tmplBioOriginario = "";
        String tmplBioNew = "";

//        bioWiki = BioWiki.findPageid(pageid);
//        if (bioWiki != null) {
//            tmplBioWiki = bioWiki.getTmplBio();
//        }// end of if cycle
//
//        bioOrig = BioOriginale.findPageid(pageid);
//        if (bioOrig != null) {
//            tmplBioOriginario = bioOrig.getTmplBio();
//            tmplBioNew = bioOrig.creaNewTmpl();
//        }// end of if cycle

        if (tmplBioNew.equals(tmplBioWiki)) {
            status = false;
        }// end of if cycle

        return status;
    }// end of static method


    /**
     * Recupera i pageids di tutti i records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> queryFind(String queryTxt) {
        return queryFind(queryTxt, 0);
    }// end of static method

    /**
     * Recupera la property (text) di tutti i records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @return lista di una property (String)
     */
    public synchronized static ArrayList<String> queryFindTxt(String queryTxt) {
        return queryFindTxt(queryTxt, 0);
    }// end of static method

    /**
     * Recupera i pageids dei primi (limit) records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @param limit    di ricerca per la query
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> queryFind(String queryTxt, int limit) {
        return queryFind(queryTxt, limit, 0);
    }// end of static method

    /**
     * Recupera la property (text) di tutti i records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @param limit    di ricerca per la query
     * @return lista di una property (String)
     */
    public synchronized static ArrayList<String> queryFindTxt(String queryTxt, int limit) {
        return queryFindTxt(queryTxt, limit, 0);
    }// end of static method

    /**
     * Recupera i pageids dei primi (limit) records, partendo da offSet, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @param limit    di ricerca per la query
     * @param offSet   di inizio per la query
     * @return lista di pageids (Long)
     */
    public synchronized static ArrayList<Long> queryFind(String queryTxt, int limit, int offSet) {
        ArrayList<Long> lista = null;
        List vettore;
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);

        if (limit > 0) {
            query.setMaxResults(limit);
        }// fine del blocco if

        if (offSet > 0) {
            query.setFirstResult(offSet);
        }// fine del blocco if

        vettore = query.getResultList();
        if (vettore != null) {
            lista = new ArrayList<Long>(vettore);
        }// end of if cycle
        manager.close();

        return lista;
    }// end of static method

    /**
     * Recupera la property (text) di tutti i records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @param limit    di ricerca per la query
     * @param offSet   di inizio per la query
     * @return lista di una property (String)
     */
    public synchronized static ArrayList<String> queryFindTxt(String queryTxt, int limit, int offSet) {
        ArrayList<String> lista = null;
        List vettore;
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);

        if (limit > 0) {
            query.setMaxResults(limit);
        }// fine del blocco if

        if (offSet > 0) {
            query.setFirstResult(offSet);
        }// fine del blocco if

        vettore = query.getResultList();
        if (vettore != null) {
            lista = new ArrayList<String>(vettore);
        }// end of if cycle
        manager.close();

        return lista;
    }// end of static method

    /**
     * Recupera il conteggio dei records, selezionati secondo la query ricevuta
     *
     * @param queryTxt per la selezione
     * @return numero di records
     */
    public synchronized static int queryCount(String queryTxt) {
        int numero = 0;
        List vettore;
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);

        vettore = query.getResultList();
        if (vettore != null) {
            numero = vettore.size();
        }// end of if cycle
        manager.close();

        return numero;
    }// end of static method


    /**
     * Recupera il conteggio dei records, selezionati secondo la query ricevuta
     *
     * @param entity   dataBase di riferimento
     * @param property unica da selezionare per il conteggio
     * @return numero di records
     */
    public synchronized static int queryCountDistinct(String entity, String property) {
        int numero = 0;
        long lungo;
        List vettore;
        EntityManager manager = EM.createEntityManager();
        Query query;
        String queryTxt = CostBio.VUOTO;
        String iniTag = "select COUNT(DISTINCT ";
        String punto = ".";
        String par = ")";
        String from = "from";

        if (entity != null && !entity.equals(CostBio.VUOTO) && property != null && !property.equals(CostBio.VUOTO)) {
            queryTxt += iniTag;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity.toLowerCase();
            queryTxt += punto;
            queryTxt += property;
            queryTxt += par;
            queryTxt += CostBio.SPAZIO;
            queryTxt += from;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity.toLowerCase();
        }// end of if cycle

        query = manager.createQuery(queryTxt);

        vettore = query.getResultList();
        if (vettore != null && vettore.size() == 1 && vettore.get(0) instanceof Long) {
            lungo = (long) vettore.get(0);
            numero = (int) lungo;
        }// end of if cycle
        manager.close();

        return numero;
    }// end of static method


    /**
     * Recupera una lista (valori unici) della property selezionata per tutti i records
     *
     * @param entity   dataBase di riferimento
     * @param property da selezionare
     * @return lista di valori della property
     */
    public synchronized static ArrayList<String> queryFindDistinctStx(String entity, String property) {
        ArrayList<String> listaUnici = null;
        long lungo;
        List entities;
        EntityManager manager = EM.createEntityManager();
        Query query;
        String queryTxt = CostBio.VUOTO;
        String iniTag = "select DISTINCT";
        String punto = ".";
        String from = "from";
        String order = "order by";

        if (entity != null && !entity.equals(CostBio.VUOTO) && property != null && !property.equals(CostBio.VUOTO)) {
            queryTxt += iniTag;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity.toLowerCase();
            queryTxt += punto;
            queryTxt += property;
            queryTxt += CostBio.SPAZIO;
            queryTxt += from;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity.toLowerCase();
            queryTxt += CostBio.SPAZIO;
            queryTxt += order;
            queryTxt += CostBio.SPAZIO;
            queryTxt += entity.toLowerCase();
            queryTxt += punto;
            queryTxt += property;
        }// end of if cycle

        query = manager.createQuery(queryTxt);

        entities = query.getResultList();
        if (entities != null && entities.size() > 0) {
            listaUnici = new ArrayList<String>(entities);
        }// end of if cycle
        manager.close();

        return listaUnici;
    }// end of static method


//    //--regola la lunghezza del campo
//    //--elimina il teasto successivo al ref
//    //--elimina il testo successivo alle note
//    //--elimina il testo successivo alle graffe
//    //--tronca comunque il testo a 255 caratteri
//    public String fixCampo( String testoIn) {
//        String testoOut = testoIn;
//
//        if (testoOut != null && !testoOut.equals("")) {
//            testoOut = testoOut.trim();
//            testoOut = LibText.levaDopoRef(testoOut);
//            testoOut = LibText.levaDopoNote(testoOut);
//            testoOut = LibText.levaDopoGraffe(testoOut);
//            testoOut = LibWiki.setNoQuadre(testoOut);
//            testoOut = testoOut.trim();
//        }// fine del blocco if
//
//        if (testoOut != null && testoOut.length() > 253) {
//            testoOut = testoOut.substring(0, 252);
//        }// fine del blocco if
//
//        return testoOut;
//    } // fine del metodo


    /**
     * Aggiunge tag ref in testa e coda alla stringa.
     * Aggiunge SOLO se gia non esiste
     * Se arriva un oggetto non stringa, restituisce nullo
     * Se arriva una stringa vuota, restituisce una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     * Elimina eventuali ref già presenti, per evitare di metterli doppi
     *
     * @param stringaIn in ingresso
     * @return stringa con tag ref aggiunti
     */
    public static String setRef(String stringaIn) {
        String stringaOut = stringaIn;

        stringaOut = REF_INI + stringaIn + REF_END;
        stringaOut = stringaOut.trim();

        return stringaOut;
    } // fine del metodo

    /**
     * Aggiunge tag 'NoInclude' in testa e coda alla stringa.
     * <p>
     * Aggiunge SOLO se già non esiste TODO Non ancora
     * Se arriva una stringa vuota, restituisce una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     * Elimina eventuali 'NoInclude' già presenti, per evitare di metterli doppi TODO Non ancora
     *
     * @param stringaIn in ingresso
     * @return stringa coi tag aggiunti
     */
    public static String setNoInclude(String stringaIn) {
        String stringaOut = stringaIn.trim();
        String tagIni = "<noinclude>";
        String tagEnd = "</noinclude>";

        if (!stringaIn.equals("")) {
            stringaOut = tagIni + stringaOut + tagEnd;
            stringaOut = stringaOut.trim();
        }// fine del blocco if

        return stringaOut;
    } // fine del metodo


//    /**
//     * Controlla che esistano modifiche sostanziali (non solo la data)
//     * <p>
//     * Se il flag è false, registra sempre
//     * Se il flag è vero, controlla la differenza del testo
//     *
//     * @param titoloVoce eventualmente da modificare
//     * @param testoNew   della modifica
//     * @return la modifica va effettuata
//     * @deprecated
//     */
//    public static boolean checkModificaSostanzialeCrono(String titoloVoce, String testoNew, String tag) {
//        boolean status = false;
//        boolean registraSoloModificheSostanziali = Pref.getBool(CostBio.USA_BODY_RIGHE_MULTIPLE_CRONO, true);
//        String testoOldSignificativo = CostBio.VUOTO;
//        String testoNewSignificativo = CostBio.VUOTO;
//        String tagEnd = "}}";
//        String testoOld = Api.leggeVoce(titoloVoce);
//        int pos1 = 0;
//        int pos2 = 0;
//
//        if (registraSoloModificheSostanziali) {
//            if (!testoOld.equals(CostBio.VUOTO) && !testoNew.equals(CostBio.VUOTO)) {
//                pos1 = testoOld.indexOf(tag);
//                pos2 = testoOld.indexOf(tagEnd, pos1);
//                testoOldSignificativo = testoOld.substring(pos2);
//
//                pos1 = testoNew.indexOf(tag);
//                pos2 = testoNew.indexOf(tagEnd, pos1);
//                testoNewSignificativo = testoNew.substring(pos2);
//            }// fine del blocco if
//            if (!testoOldSignificativo.equals(CostBio.VUOTO) && !testoNewSignificativo.equals(CostBio.VUOTO)) {
//                if (!testoNewSignificativo.equals(testoOldSignificativo)) {
//                    status = true;
//                }// fine del blocco if
//            }// fine del blocco if
//        } else {
//            status = true;
//        }// end of if/else cycle
//
//        return status;
//    } // fine del metodo

    /**
     * Controlla che esistano modifiche sostanziali (non solo la data)
     *
     * @param titoloVoce eventualmente da modificare
     * @param testoNew   della modifica
     * @param tagIni     inizio del testo iniziale (incipit) da considerare NON sostanziale
     * @param tagEnd     fine del testo iniziale (incipit) da considerare NON sostanziale
     * @return la modifica va effettuata
     */
    public static boolean checkModificaSostanziale(String titoloVoce, String testoNew, String tagIni, String tagEnd) {
        boolean status = false;
        String testoOldSignificativo = CostBio.VUOTO;
        String testoNewSignificativo = CostBio.VUOTO;
        String testoOld = Api.leggeVoce(titoloVoce);
        int pos1 = 0;
        int pos2 = 0;

        if (testoOld.equals(CostBio.VUOTO)) {
            return true;
        }// fine del blocco if

        if (!testoOld.equals(CostBio.VUOTO) && !testoNew.equals(CostBio.VUOTO)) {
            pos1 = testoOld.indexOf(tagIni);
            pos2 = testoOld.indexOf(tagEnd, pos1);
            testoOldSignificativo = testoOld.substring(pos2);

            pos1 = testoNew.indexOf(tagIni);
            pos2 = testoNew.indexOf(tagEnd, pos1);
            testoNewSignificativo = testoNew.substring(pos2);
        }// fine del blocco if

        if (!testoOldSignificativo.equals(CostBio.VUOTO) && !testoNewSignificativo.equals(CostBio.VUOTO)) {
            if (!testoNewSignificativo.equals(testoOldSignificativo)) {
                status = true;
            }// fine del blocco if
        }// fine del blocco if


        return status;
    } // fine del metodo

    /**
     * Estrae una subLista parziale dalla lista.
     *
     * @param lista  da cui estrarre la subList
     * @param posIni iniziale
     * @param posEnd finale
     * @return subLista
     */
    public static ArrayList subList(ArrayList<Long> lista, int posIni, int posEnd) {
        ArrayList subLista = null;
        List listaTmp = null;

        if (lista != null && lista.size() > 0) {
            if (posEnd > posIni) {
                if (posEnd < lista.size()) {
                    listaTmp = lista.subList(posIni, posEnd);
                }// end of if cycle
            }// end of if cycle
        }// fine del blocco if

        if (listaTmp != null && listaTmp instanceof List) {
            subLista = new ArrayList(listaTmp);
        }// end of if cycle

        return subLista;
    } // fine del metodo

    /**
     * Recupera un oggetto Bio leggendolo dal server wiki.
     *
     * @param wikiTitle della pagina da cui estrarre il template Bio
     * @return istanza di Bio
     */
    public static Bio leggeBio(String wikiTitle) {
        Bio bio = null;
        Page pagina = null;
        WrapBio wrap = null;

        if (!wikiTitle.equals(CostBio.VUOTO)) {
            pagina = Api.leggePage(wikiTitle);
        }// end of if cycle

        if (pagina != null) {
            wrap = new WrapBio(pagina);
        }// end of if cycle

        if (wrap != null) {
            bio = wrap.getBio();
        }// end of if cycle

        return bio;
    } // fine del metodo


    /**
     * Estrae la parte visibile di un link
     */
    public static String estraeLink(String paginaCompleta) {
        String link;

        link = paginaCompleta.substring(paginaCompleta.indexOf("|") + 1);
        link = LibWiki.setNoQuadre(link);

        return link;
    }// fine del metodo

    /**
     * Semplifica un link, se la parte visibile è uguale al link effettivo
     */
    public static String fixLink(String paginaCompleta) {
        String link = LibWiki.setNoQuadre(paginaCompleta);
        String[] parti = null;

        if (!link.equals(CostBio.VUOTO)) {
            parti = link.split("\\|");
        }// end of if cycle

        if (parti != null && parti.length == 2) {
            if (parti[0].equals(parti[1])) {
                link = parti[0];
            }// end of if cycle
        }// end of if cycle

        return LibWiki.setQuadre(link);
    }// fine del metodo

}// end of abstract static class
