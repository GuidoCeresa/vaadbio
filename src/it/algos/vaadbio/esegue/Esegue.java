package it.algos.vaadbio.esegue;

import it.algos.vaadbio.attivita.AttivitaService;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.ciclo.CicloElabora;
import it.algos.vaadbio.ciclo.CicloUpdate;
import it.algos.vaadbio.cognome.CognomeService;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.NazionalitaService;
import it.algos.vaadbio.nome.NomeService;
import it.algos.vaadbio.professione.ProfessioneService;
import it.algos.vaadbio.statistiche.*;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.vaadbio.upload.UploadCognomi;
import it.algos.vaadbio.upload.UploadGiorni;
import it.algos.vaadbio.upload.UploadNomi;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

/**
 * Created by gac on 25 gen 2016.
 * .
 */
public abstract class Esegue {

    /**
     * Ciclo completo
     */
    public static void cicloCompleto() {
        cicloDownload();
        cicloUpdate();
        cicloElabora();
        cicloUpload();
    } // fine del metodo

    /**
     * Ciclo normale giornaliero notturno di download
     */
    public static void cicloDownload() {
        AttivitaService.download();
        NazionalitaService.download();
        ProfessioneService.download();
        new CicloDownload();
    } // fine del metodo

    /**
     * Aggiorna tutti i records esistenti
     */
    public static void cicloUpdate() {
        new CicloUpdate();
    } // fine del metodo


    /**
     * Ciclo di elaborazione per tutte (dipende dal flag) pagine
     */
    public static void cicloElabora() {
        new CicloElabora();
    } // fine del metodo


    /**
     * todo da fare
     */
    public static void elabora() {
    } // fine del metodo


    /**
     * Ciclo normale di upload
     */
    public static void cicloUpload() {
        uploadGiorni();
        statisticaSintesi();
        cicloNomi();
        cicloGiornalieroCognomi();
        uploadAnni();
    } // fine del metodo

    /**
     * Upload giorni
     */
    public static void uploadGiorni() {
        new UploadGiorni();
    } // fine del metodo

    /**
     * Upload anni
     */
    public static void uploadAnni() {
        new UploadAnni(false);
    } // fine del metodo


    /**
     * Ciclo normale di aggiornamento ed upload dei nomi
     * <p>
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    public static void cicloNomi() {
        long inizio = System.currentTimeMillis();

        if (Pref.getBool(CostBio.USA_DAEMONS_NOMI, false)) {
            aggiornaNomi();
            elaboraNomi();
            uploadNomi();
            statisticheNomi();

            Log.debug("nomi", "Nomi doppi, aggiunti ed elaborati i nomi, upload di tutti e statistiche in " + LibTime.difText(inizio));
        }// end of if cycle
    } // fine del metodo


    /**
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    public static void downloadNomiDoppi() {
        NomeService.listaNomiDoppi();
    }// end of method


    /**
     * Esegue l'aggiornamento e la creazione dei nuovi records
     */
    public static void aggiornaNomi() {
        NomeService.aggiorna();
    }// end of method

    /**
     * Esegue l'elaborazione dei records esistenti
     */
    public static void elaboraNomi() {
        NomeService.elabora();
    }// end of method


    /**
     * Upload nomi
     */
    public static void uploadNomi() {
        new UploadNomi();
    } // fine del metodo

    /**
     * Crea la pagina riepilogativa dei nomi
     * Crea la pagina di riepilogo di tutti i nomi
     * Crea la pagina di controllo didascalie
     */
    public static void statisticheNomi() {
        new StatNomiPagine();
        new StatNomiListe();

//        creaPaginaDidascalie();
    }// fine del metodo


    /**
     * Crea tutte le pagine statistiche previste
     */
    public static void statisticheAll() {
        statisticaSintesi();
        statisticaGiorni();
        statisticaAnni();
    } // fine del metodo


    /**
     * Crea una pagina statistica di sintesi.
     */
    public static void statisticaSintesi() {
        new StatSintesi();
    } // fine del metodo


    /**
     * Crea una pagina statistica sui giorni.
     */
    public static void statisticaGiorni() {
        new StatGiorni();
    } // fine del metodo


    /**
     * Crea una pagina statistica sugli anni.
     */
    public static void statisticaAnni() {
        new StatAnni();
    } // fine del metodo


    /**
     * Crea una tabella esemplificativa di didascalie cronografiche (giorni ed anni).
     */
    public static void statisticaDidascalieCrono() {
        new StatDidascalieCrono();
    } // fine del metodo


    //------------------------------------------------------------------------------------------------------------------------
    // Cognomi
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Ciclo normale settimanale di controllo e manutenzione dei cognomi
     */
    public static void cicloSettimanaleCognomi() {
        long inizio = System.currentTimeMillis();

        if (Pref.getBool(CostBio.USA_DAEMONS_COGNOMI, false)) {
            Esegue.creaCognomi();
            Esegue.elaboraCognomi();
            Esegue.contaCognomi();
            Log.debug("cognomi", "Aggiunti ed elaborati i records dei cognopmi in " + LibTime.difText(inizio));
        }// end of if cycle
    } // fine del metodo

    /**
     * Ciclo normale giornaliero di upload dei cognomi
     */
    public static void cicloGiornalieroCognomi() {
        int vociUplodate = 0;
        long inizio = System.currentTimeMillis();

        if (Pref.getBool(CostBio.USA_DAEMONS_COGNOMI, false)) {
            vociUplodate = Esegue.uploadCognomi();
            Log.debug("cognomi", "Caricate sul server le " + LibNum.format(vociUplodate) + " pagine di liste di cognomi in " + LibTime.difText(inizio));
            Esegue.statisticheCognomi(); //todo se è troppo lungo, spostare in cicloSettimanaleCognomi
        }// end of if cycle
    } // fine del metodo

    /**
     * Esegue l'aggiornamento e la creazione dei nuovi records
     */
    public static void creaCognomi() {
        int recordsCreati;
        long inizio = System.currentTimeMillis();

        recordsCreati = CognomeService.crea();
        Log.debug("cognomi", "Creati " + LibNum.format(recordsCreati) + " records di cognomi in " + LibTime.difText(inizio));
    }// end of method

    /**
     * Esegue l'elaborazione dei records esistenti
     * todo da fare
     */
    public static void elaboraCognomi() {
//        CognomeService.elabora();
    }// end of method

    /**
     * todo da fare
     */
    public static void contaCognomi() {
    } // fine del metodo

    /**
     * Upload nomi
     */
    public static int uploadCognomi() {
        return new UploadCognomi().getVociUplodate();
    } // fine del metodo

    /**
     * Crea la pagina riepilogativa dei cognomi
     * Crea la pagina di controllo didascalie
     */
    public static void statisticheCognomi() {
//        new StatCognomiPagine();
//        new StatCognomiListe();

//        creaPaginaDidascalie();
    }// fine del metodo


    //------------------------------------------------------------------------------------------------------------------------
    // Test
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Crea una pagina di prova
     */
    public static void testIncipitNomi() {
        NomeService.testIncipitNomi();
    } // fine del metodo

}// end of abstract static class
