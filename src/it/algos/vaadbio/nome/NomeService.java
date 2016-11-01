package it.algos.vaadbio.nome;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.LibText;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestione dei nomi (antroponimi)
 * <p>
 * 1° fase da fare una tantum o ogni 6-12 mesi
 * Costruisce
 * Annullamento del link tra BioGrails e gli Antroponimi
 * Creazione dei records di antroponimi leggendo i records BioGrails
 * Controllo della pagina Progetto:Antroponimi/Nomi doppi
 * Ricalcolo delle voci per ricostruire in ogni record di BioGrails il link verso il corretto record di Antroponimo
 * <p>
 * 2° fase da fare una tantum
 * Aggiunge
 * Aggiunta dei records di antroponimi leggendo i records BioGrails
 * Controllo della pagina Progetto:Antroponimi/Nomi doppi
 * Ricalcolo delle voci per ricostruire in ogni record di BioGrails il link verso il corretto record di Antroponimo
 * <p>
 * 3° fase da fare ogni settimana
 * Ricalcola
 * Upload
 * Controllo della pagina Progetto:Antroponimi/Nomi doppi
 * Spazzolamento di tutti i records di Antroponimi per aggiornare il numero di voci linkate
 * Creazione della pagina/lista per ogni record di Antroponimi che supera la soglia
 * <p>
 * Note:
 * A- Se USA_ACCENTI_NORMALIZZATI è true, devo vedere sempre il nome Aaròn. Se è false lo vedo solo se supera SOGLIA_ANTROPONIMI
 * B- Crea records di Antroponimo al di sopra di SOGLIA_ANTROPONIMI
 * C- Se USA_SOLO_PRIMO_NOME_ANTROPONIMI è true, considera solo il primo nome che trova per creare un antroponimo
 * e trovo solo Aaron. Se è false, trovo anche Aaron Michael (se supera la SOGLIA_ANTROPONIMI)
 * D- Se USA_LISTA_NOMI_DOPPI è true, aggiunge i records letti da Progetto:Antroponimi/Nomi doppi
 * <p>
 * nomi distinti: 93.219
 * 2382 antroponimi
 * Jean-Jacques deve rimanere, Jean Baptiste no
 */
public abstract class NomeService {

    private static String TITOLO_LISTA_NOMI_DOPPI = "Progetto:Antroponimi/Nomi doppi";

    private static String[] TAG_INI_NUMERI = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private static String[] TAG_INI_CHAR = {"*", "&", "!", "&nbsp", ".", "(", ",", "?", "{", "[", "<", "-"};
    private static String[] TAG_INI_APICI = {"‘", "‛", "\"", "''"};
    private static String[] TAG_INI_NOMI = {"A.", "DJ", "J."};
    private static String[] TAG_ALL_ARABI = {"Abd", "'Abd", "ʿAbd", "Abu", "'Abu", "Abū", "'Abū", "Ibn", "'Ibn", "ʿAbd"};
    private static String[] TAG_ALL_TITOLI = {"Lady", "Sir", "Maestro", "De", "Van", "Della", "dos"};
    private static String[] TAG_ALL_NOMI = {"Gian"};
    private static String[] TAG_ALL_NOMI_CINESI = {"Zhang"};
    private static String[] TAG_ALL_COGNOMI = {"d'Asburgo", "d'Asburgo-Lorena", "d'Este", "da Silva", "di Borbone", "O'Brien", "Knight"};

    /**
     * costruisce i records
     */
    public static void costruisce() {
//        cancellaTutto()
//        aggiunge();
//
//        log.info 'Fine costruzione antroponimi'
    }// fine del metodo



    /**
     * Aggiunta nuovi records e modifica di quelli esistenti
     * Vengono creati nuovi records per i nomi presenti nelle voci (bioGrails) che superano la soglia minima
     */
    public static void aggiorna() {
        ArrayList<String> listaNomiCompleta;
        ArrayList<String> listaNomiUnici;

        listaNomiDoppi();

        //--recupera una lista 'grezza' di tutti i nomi
        listaNomiCompleta = creaListaNomiCompleta();

        //--elimina tutto ciò che compare oltre al nome
        listaNomiUnici = elaboraAllNomiUnici(listaNomiCompleta);

        //--(ri)costruisce i records
        spazzolaAllNomiUnici(listaNomiUnici);

        //--aggiunge i riferimenti alla voce principale di ogni record
//        elaboraVocePrincipale();
    }// fine del metodo


    /**
     * Controllo della pagina Progetto:Antroponimi/Nomi doppi
     * Vengono creati i records mancanti nel database per tutti i nomi doppi
     * Questo perché spazzolando il parametro nome delle biografie, di norma identifica SOLO il primo nome
     * Occorre aggiungere quindi i nomi doppi esplicitamente previsti nella lista su wiki
     * Vengono eliminati im precedenti nomi doppi non più presenti nella pagina del progetto
     */
    public static void listaNomiDoppi() {
        String titolo = TITOLO_LISTA_NOMI_DOPPI;
        String tagInizio = "*";
        String tagRiga = "\\*";
        String tagFine = "\n\n";
        String[] righe = null;
        String testoPagina = Api.leggeVoce(titolo);
        ArrayList<String> listaServer = new ArrayList<>();
        ArrayList<String> listaDB;
        String tagRigaNomiMultipli = ",";
        String[] arrayNomiDoppi = null;

        if (!testoPagina.equals(CostBio.VUOTO)) {
            testoPagina = testoPagina.substring(testoPagina.indexOf(tagInizio), testoPagina.indexOf(tagFine));
            righe = testoPagina.split(tagRiga);
        }// fine del blocco if

        if (righe != null && righe.length > 0) {
            for (String stringa : righe) {
                if (!stringa.equals(CostBio.VUOTO)) {
                    if (stringa.contains(tagRigaNomiMultipli)) {
                        arrayNomiDoppi = stringa.split(tagRigaNomiMultipli);
                        if (arrayNomiDoppi.length > 1) {
                            for (String nome : arrayNomiDoppi) {
                                listaServer.add(nome.trim());
                            }// end of for cycle
                        }// end of if/else cycle
                    } else {
                        listaServer.add(stringa.trim());
                    }// end of if/else cycle
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        listaDB = LibBio.queryFindTxt("select nome.nome from Nome nome where nome.nomeDoppio=1 order by nome.nome asc");
        aggiungeMancanti(LibArray.differenza(listaServer, listaDB));
        cancellaEccedenti(LibArray.differenza(listaDB, listaServer));
    }// fine del metodo


    /**
     * Crea i records che mancano
     */
    private static void aggiungeMancanti(ArrayList<String> listaMancanti) {
        for (String nome : listaMancanti) {
            Nome.crea(nome);
        }// end of for cycle
    }// fine del metodo

    /**
     * Cancella i records che avanzano
     */
    private static void cancellaEccedenti(ArrayList<String> listaEccedenti) {
        Nome nome;

        for (String nomeTxt : listaEccedenti) {
            nome = Nome.findByNome(nomeTxt);
            if (nome != null) {
                nome.delete();
            }// end of if cycle
        }// end of for cycle
    }// fine del metodo


    /**
     * Elabora i records
     */
    public static void elabora() {
        ArrayList<Nome> listaNomiDoppi;
        ArrayList<Nome> listaNomiCompleta;
        String nomeValido;

        //--recupera una lista di tutti i nomi NON doppi
        listaNomiDoppi = Nome.findAllDoppi();

        //--recupera una lista di tutti i nomi NON doppi
        listaNomiCompleta = Nome.findAllNotDoppi();

        for (Nome nome : listaNomiCompleta) {
            nomeValido = check(nome.getNome());
            if (!nomeValido.equals(nome.getNome())) {
                if (nomeValido.equals(CostBio.VUOTO)) {
                    nome.delete();
                } else {
                    nome.setNome(nomeValido);
                    nome.save();
                }// end of if/else cycle
            }// fine del blocco if
        }// end of for cycle

    }// fine del metodo

    /**
     * Crea (controllando che non esista già) un record principale di Nome
     *
     * @param nomeTxt nome della persona
     */
    private static Nome elaboraSingolo(String nomeTxt, boolean nomeDoppio) {
        Nome nome = Nome.findByNome(nomeTxt);

        if (nome == null) {
            nome = new Nome(nomeTxt);
            nome.setPrincipale(true);
            nome.setNomeDoppio(nomeDoppio);
            nome.setRiferimento(nome);
            nome.save();
        }// end of if cycle

        return nome;
    }// fine del metodo


    /**
     * Crea (controllando che non esista già) un record secondario di Nome
     *
     * @param nomeTxt     nome della persona
     * @param riferimento record principale di riferimento nel DB Nome
     */
    private static Nome elaboraSingolo(String nomeTxt, Nome riferimento) {
        Nome nome = null;

        if (!nomeTxt.equals(CostBio.VUOTO)) {
            nomeTxt = nomeTxt.trim();
            nome = Nome.findByNome(nomeTxt);

            if (nome == null) {
                nome = new Nome(nomeTxt, false, true, riferimento);
                nome.save();
            }// end of if cycle
        }// end of if cycle

        return nome;
    }// fine del metodo

    /**
     * Recupera una lista 'grezza' di tutti i nomi
     */
    private static ArrayList<String> creaListaNomiCompleta() {
        return LibBio.queryFindDistinctStx("Bio", "nome");
    }// fine del metodo

    /**
     * Elabora tutti i nomi
     * Costruisce una lista di nomi ''validi' e 'unici'
     * Devo ricontrollare l'unicità, perché prendendo solo il primo nome
     * ''Marco Filiberto'' e ''Marco Giovanni'' risultano sempre come ''Marco''
     */
    public static ArrayList<String> elaboraAllNomiUnici(ArrayList<String> listaNomiCompleta) {
        ArrayList<String> listaNomiUnici = new ArrayList<String>();
        String nomeValido;

        //--costruisce una lista di nomi 'unici'
        for (String nomeDaControllare : listaNomiCompleta) {
            nomeValido = check(nomeDaControllare);
            if (!nomeValido.equals(CostBio.VUOTO)) {
                if (!listaNomiUnici.contains(nomeValido)) {
                    listaNomiUnici.add(nomeValido);
                }// fine del blocco if
            }// fine del blocco if
        }// end of for cycle

        return listaNomiUnici;
    }// fine del metodo


    /**
     * Elabora il singolo nome
     * <p>
     * Prima regola: considera solo i nomi più lunghi di 2 caratteri
     * Seconda regola: usa (secondo preferenze) i nomi singoli; Maria e Maria Cristina sono uguali
     * Terza regola: controlla una lista di nomi doppi accettati come autonomi per la loro rilevanza
     * Quarta regola: elimina parti iniziali con caratteri/prefissi non accettati -> LibBio.checkNome()
     * Quinta regola: elimina <ref>< finali e testo successivo
     * <p>
     * Elimina caratteri 'anomali' dal nome
     * Gian, Lady, Sir, Maestro, Abd, 'Abd, Abu, Abū, Ibn, DJ, e J.
     */
    public static String check(String nomeIn) {
        String nomeOut = CostBio.VUOTO;
        int pos;
        boolean usaNomeSingolo = Pref.getBool(CostBio.USA_NOME_SINGOLO, true);
        Nome nomeEsistente = null;

        //--prima regola
        if (nomeIn.length() < 3) {
            return nomeOut;
        } else {
            nomeOut = nomeIn.trim();
        }// end of if/else cycle

        if (nomeOut.contains(CostBio.SPAZIO)) {
            //--seconda regola
            if (usaNomeSingolo) {
                pos = nomeOut.indexOf(CostBio.SPAZIO);
                nomeOut = nomeOut.substring(0, pos);
                nomeOut = nomeOut.trim();
            }// end of if cycle

            //--terza regola
            nomeEsistente = Nome.findByNome(nomeIn.trim());
            if (nomeEsistente != null) {
                if (nomeEsistente.isNomeDoppio()) {
                    nomeOut = nomeEsistente.getNome();
                }// end of if cycle
            } else {
            }// end of if/else cycle
        }// fine del blocco if

        // --quarta regola
        if (!checkNome(nomeOut)) {
            nomeOut = CostBio.VUOTO;
        }// fine del blocco if

        //--quinta regola
        nomeOut = LibBio.fixCampo(nomeOut);

        //--prima regola ricontrollata dopo l'uso del nome singolo
        if (nomeOut.length() < 3) {
            nomeOut = CostBio.VUOTO;
        }// fine del blocco if

        //--per sicurezza in caso di nomi strani
        nomeOut = LibText.primaMaiuscola(nomeOut);

        return nomeOut;
    }// fine del metodo


    /**
     * Spazzola la lista di nomi
     */
    public static void spazzolaAllNomiUnici(ArrayList<String> listaAllNomiUnici) {

        for (String nome : listaAllNomiUnici) {
            spazzolaNome(nome);
        }// end of for cycle

    }// fine del metodo


    /**
     * Controlla il singolo nome
     * Crea un record per ogni nome non ancora esistente
     * Registra anche i nomi accentati ma col riferimento al record del nome normalizzato (senza accenti)
     */
    private static void spazzolaNome(String nomeConEventualeAccento) {

        if (!nomeConEventualeAccento.equals(CostBio.VUOTO)) {
            if (nomeSenzaAccento(nomeConEventualeAccento)) {
                elaboraSingolo(nomeConEventualeAccento, false);
            }// fine del blocco if-else
        }// fine del blocco if
    }// fine del metodo


    /**
     * Controlla eventuali accenti del nome
     */
    private static boolean nomeSenzaAccento(String nomeConEventualeAccento) {
        boolean senzaAccento = false;
        String nomeNormalizzato;

        nomeNormalizzato = normalizza(nomeConEventualeAccento);
        if (nomeNormalizzato.equals(nomeConEventualeAccento)) {
            senzaAccento = true;
        }// fine del blocco if

        return senzaAccento;
    }// fine del metodo

    /**
     * Elimina eventuali accenti dal nome
     */
    private static String normalizza(String nomeConEventualeAccento) {
        String nomeNormalizzato = nomeConEventualeAccento;

        if (!nomeConEventualeAccento.equals(CostBio.VUOTO)) {
            if (false) {
                nomeNormalizzato = Normalizer.normalize(nomeConEventualeAccento, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            }// fine del blocco if
        }// fine del blocco if

        return nomeNormalizzato;
    }// fine del metodo


    /**
     * Controllo di validità di un nome <br>
     * Elimina parti iniziali con caratteri/prefissi non accettati <br>
     * Elimina sempre il nome esattamente uguale al tag-iniziale o al tag-all <br>
     * Elimina sempre il nome che inizia col tag-iniziale <br>
     * Elimina il nome che inizia col tag-all se il tag è seguito da spazio <br>
     */
    public static boolean checkNome(String nome) {
        boolean accettato = true;
        ArrayList<String> tagIniziale = LibBio.sumTag(TAG_INI_NUMERI, TAG_INI_CHAR, TAG_INI_APICI, TAG_INI_NOMI);
        ArrayList<String> tagAll = LibBio.sumTag(TAG_ALL_ARABI, TAG_ALL_TITOLI, TAG_ALL_NOMI, TAG_ALL_NOMI_CINESI, TAG_ALL_COGNOMI);
        ArrayList<String> tagSomma = LibArray.somma(tagIniziale, tagAll);
        String spazio = CostBio.SPAZIO;

        for (String tag : tagSomma) {
            if (nome.equals(tag)) {
                accettato = false;
            }// fine del blocco if
        }// end of for cycle

        if (accettato) {
            for (String tag : tagIniziale) {
                if (nome.startsWith(tag)) {
                    accettato = false;
                }// fine del blocco if
            }// end of for cycle
        }// fine del blocco if

        if (accettato) {
            for (String tag : tagAll) {
                if (nome.startsWith(tag + spazio)) {
                    accettato = false;
                }// fine del blocco if
            }// end of for cycle
        }// fine del blocco if

        return accettato;
    }// fine del metodo


    /**
     * Crea una pagina di prova
     */
    public static void testIncipitNomi() {
        String titolo = "Utente:Gac/Sandbox4279";
        String template;
        String testo;

        template = "template:Incipit lista nomi";
//        template = "Utente:Dr Zimbu/Sandbox3";
        testo = "Test per controllare il funzionamento del template " + LibWiki.setBold(LibWiki.setLink(template)) + CostBio.A_CAPO;

        ArrayList<String> lista = Nome.findListaTaglioPagina();
        for (String nome : lista) {
            testo += "# Nella pagina [[persone di nome " + nome + "]] l'incipit sarà: {{" + template + "|nome=" + nome + "}}<br>\n";
        }// end of for cycle

        Api.scriveVoce(titolo, testo);
    } // fine del metodo

}//end of class
