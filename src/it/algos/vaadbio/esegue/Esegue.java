package it.algos.vaadbio.esegue;

import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.statistiche.StatAnni;
import it.algos.vaadbio.statistiche.StatGiorni;
import it.algos.vaadbio.statistiche.StatSintesi;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.vaadbio.upload.UploadGiorni;

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
        cicloUpload();
    } // fine del metodo

    /**
     * Ciclo normale giornaliero notturno di download
     */
    public static void cicloDownload() {
        new CicloDownload();
    } // fine del metodo

    /**
     * Ciclo normale di upload
     */
    public static void cicloUpload() {
        uploadGiorni();
        uploadAnni();
        statisticheAll();
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
        new UploadAnni();
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


}// end of abstract static class
