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
import it.algos.vaadbio.upload.*;
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
        cicloCognomi();
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
     * Upload attività
     */
    public static void uploadAttivita() {
        new UploadAttivita();
    } // fine del metodo


    /**
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    @Deprecated
    public static void downloadNomiDoppi() {
        NomeService.listaNomiDoppi();
    }// end of method


    /**
     * Esegue l'aggiornamento e la creazione dei nuovi records
     */
    @Deprecated
    public static void aggiornaNomi() {
        NomeService.aggiorna();
    }// end of method

    /**
     * Esegue l'elaborazione dei records esistenti
     */
    @Deprecated
    public static void elaboraNomi() {
        NomeService.elabora();
    }// end of method



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
    // Nomi
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Ciclo normale giornaliero di creazione dei records di nomi
     * Ciclo normale giornaliero di upload delle liste di nomi
     */
    public static void cicloNomi() {
        if (Pref.getBool(CostBio.USA_DAEMONS_NOMI, false)) {
            NomeService.crea();
            Esegue.uploadNomi();
            Esegue.statisticheNomi();
        }// end of if cycle
    } // fine del metodo

    /**
     * Upload nomi
     */
    public static int uploadNomi() {
        return new UploadNomi().getVociUplodate();
    } // fine del metodo


    /**
     * Crea la pagina riepilogativa dei nomi
     * Crea la pagina di riepilogo di tutti i nomi
     */
    public static void statisticheNomi() {
        new StatNomiPagine();
        new StatNomiListe();
    }// fine del metodo
//------------------------------------------------------------------------------------------------------------------------
    // Cognomi
    //------------------------------------------------------------------------------------------------------------------------

    /**
     * Ciclo normale giornaliero di creazione dei records di cognomi
     * Ciclo normale giornaliero di upload delle liste di cognomi
     */
    public static void cicloCognomi() {
        if (Pref.getBool(CostBio.USA_DAEMONS_COGNOMI, false)) {
            CognomeService.crea();
            Esegue.uploadCognomi();
            Esegue.statisticheCognomi();
        }// end of if cycle
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
        new StatCognomiPagine();
        new StatCognomiListe();
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
