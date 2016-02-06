package it.algos.vaadbio.nome;

import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;

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

    /**
     * costruisce i records
     */
    public static void costruisce() {
//        cancellaTutto()
        aggiunge();
//
//        log.info 'Fine costruzione antroponimi'
    }// fine del metodo

    /**
     * Aggiunta nuovi records
     * Vengono creati nuovi records per i nomi presenti nelle voci (bioGrails) che superano la soglia minima
     */
    private static void aggiunge() {
        ArrayList<String> listaNomiCompleta;
        ArrayList<String> listaNomiUnici;

        listaNomiDoppi();

        //--recupera una lista 'grezza' di tutti i nomi
//        listaNomiCompleta = creaListaNomiCompleta();

        //--elimina tutto ciò che compare oltre al nome
//        listaNomiUnici = elaboraNomiUnici(listaNomiCompleta);

        //--(ri)costruisce i records di antroponimi
//        spazzolaPacchetto(listaNomiUnici);

        //--aggiunge i riferimenti alla voce principale di ogni record
//        elaboraVocePrincipale();
    }// fine del metodo


    /**
     * Controllo della pagina Progetto:Antroponimi/Nomi doppi
     * Vengono creati i records mancanti nel database per tutti i nomi doppi
     * Questo perché spazzolando il parametro nome delle biografie, di norma identifica SOLO il primo nome
     * Occorre aggiungere quindi i nomi doppi esplicitamente previsti nella lista su wiki
     */
    public static void listaNomiDoppi() {
        String titolo = TITOLO_LISTA_NOMI_DOPPI;
        String tagInizio = "*";
        String tagRiga = "\\*";
        String tagFine = "\n\n";
        String[] righe = null;
        String testoPagina = Api.leggeVoce(titolo);

        if (!testoPagina.equals(CostBio.VUOTO)) {
            testoPagina = testoPagina.substring(testoPagina.indexOf(tagInizio), testoPagina.indexOf(tagFine));
            righe = testoPagina.split(tagRiga);
        }// fine del blocco if

        if (righe != null && righe.length > 0) {
            for (String stringa : righe) {
                if (!stringa.equals(CostBio.VUOTO)) {
                    elaboraRigaNomiDoppi(stringa.trim());
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle
    }// fine del metodo


    /**
     * Controllo della pagina Progetto:Antroponimi/Nomi doppi
     */
    private static void elaboraRigaNomiDoppi(String riga) {
        String tagNome = ",";
        String nomeTxt;
        Nome nome = null;
        String[] nomiDoppi = riga.split(tagNome);

        if (nomiDoppi.length > 0) {
            nomeTxt = nomiDoppi[0];
            nome = elaboraSingolo(nomeTxt);
            if (nomiDoppi.length > 1) {
                for (int k = 1; k < nomiDoppi.length; k++) {
                    elaboraSingolo(nomiDoppi[k], nome);
                }// end of for cycle
            }// end of if/else cycle
        }// fine del blocco if
    }// fine del metodo


    /**
     * Crea (controllando che non esista già) un record principale di Nome
     *
     * @param nomeTxt nome della persona
     */
    private static Nome elaboraSingolo(String nomeTxt) {
        Nome nome = Nome.findByNome(nomeTxt);

        if (nome == null) {
            nome = new Nome(nomeTxt);
            nome.setPrincipale(true);
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
        Nome nome = Nome.findByNome(nomeTxt);

        if (nome == null) {
            nome = new Nome(nomeTxt, false, riferimento);
            nome.save();
        }// end of if cycle

        return nome;
    }// fine del metodo

}//end of class
