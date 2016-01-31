package it.algos.vaadbio.nome;

import it.algos.vaad.wiki.Api;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

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
public class NomeService {
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
     */
    private static void listaNomiDoppi() {
        String titolo = TITOLO_LISTA_NOMI_DOPPI;
        String tagInizio = "*";
        String tagRiga = "\\*";
        String[] righe = null;
        String testoPagina = Api.leggeVoce(titolo);
        int soglia = Pref.getInt(CostBio.SOGLIA_NOMI,100);


        if (!testoPagina.equals(CostBio.VUOTO)) {
            testoPagina = testoPagina.substring(testoPagina.indexOf(tagInizio));
            righe = testoPagina.split(tagRiga);
        }// fine del blocco if

        if (righe != null && righe.length > 0) {
            for (String stringa : righe) {
                elaboraRigaNomiDoppi(stringa, soglia);
            }// end of for cycle
        }// end of if cycle
    }// fine del metodo


    /**
     * Controllo della pagina Progetto:Antroponimi/Nomi doppi
     */
    private static void elaboraRigaNomiDoppi(String riga, int soglia) {
        String tagNome = ",";
        String[] nomiDoppi = null;
        Nome nome = null;

        if (!riga.equals(CostBio.VUOTO)) {
            nomiDoppi = riga.split(tagNome);
        }// fine del blocco if

        if (nomiDoppi != null && nomiDoppi.length > 0) {
            nome = elaboraNomeDoppio(nomiDoppi[0].trim(), soglia, true, null);

            if (nome!=null) {
//                antroponimo.voceRiferimento = antroponimo
//                antroponimo.save()
            }// fine del blocco if

            if (nomiDoppi.length > 1) {
                for (int k = 1; k < nomiDoppi.length; k++) {
                    elaboraNomeDoppio(nomiDoppi[k].trim(), soglia, false, nome);
                } // fine del ciclo for
            }// fine del blocco if-else
        }// fine del blocco if
    }// fine del metodo

    /**
     * Controllo della pagina Progetto:Antroponimi/Nomi doppi
     */
    public static Nome elaboraNomeDoppio(String nomeDoppio, int soglia, boolean vocePrincipale, Nome nome) {
//        int numVoci = numeroVociCheUsanoNome(nome);
//
//        if (vocePrincipale) {
//            if (numVoci > soglia) {
//                antroponimo = registraSingoloNome(nome.trim(), numVoci, vocePrincipale)
//            }// fine del blocco if
//        } else {
//            antroponimo = registraSingoloNome(nome.trim(), numVoci, vocePrincipale, antroponimo)
//        }// fine del blocco if-else
//
//        return antroponimo
        return null;
    }// fine del metodo

}//end of class
