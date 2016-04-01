package it.algos.vaadbio.esegue;

import it.algos.vaadbio.attivita.AttivitaService;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.ciclo.CicloElabora;
import it.algos.vaadbio.ciclo.CicloUpdate;
import it.algos.vaadbio.nazionalita.NazionalitaService;
import it.algos.vaadbio.nome.NomeService;
import it.algos.vaadbio.professione.ProfessioneService;
import it.algos.vaadbio.statistiche.StatAnni;
import it.algos.vaadbio.statistiche.StatDidascalieCrono;
import it.algos.vaadbio.statistiche.StatGiorni;
import it.algos.vaadbio.statistiche.StatSintesi;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.vaadbio.upload.UploadGiorni;
import it.algos.vaadbio.upload.UploadNomi;

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
     * Ciclo normale di upload
     */
    public static void cicloUpload() {
        uploadGiorni();
        statisticaSintesi();
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
     * Upload nomi
     */
    public static void uploadNomi() {
        new UploadNomi();
    } // fine del metodo

    /**
     * Esegue l'aggiornamento della lista dei nomi doppi
     */
    public static void esegueDownloadDoppi() {
        NomeService.listaNomiDoppi();
    } // fine del metodo


    /**
     * Aggiunta nomi.
     */
    public static void aggiungeNomi() {
        NomeService.listaNomiDoppi();
        NomeService.aggiunge();
    } // fine del metodo


    /**
     * Elaborazione nomi.
     */
    public static void elaboraNomi() {
        NomeService.elabora();
    } // fine del metodo


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


}// end of abstract static class
