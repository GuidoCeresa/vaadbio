package it.algos.vaadbio.bootstrap;

import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.domain.pref.PrefType;
import it.algos.webbase.domain.pref.TypePref;

import java.util.List;

/**
 * Created by gac on 23 ott 2016.
 * .
 */
public class ConvertePreferenze {

    public ConvertePreferenze() {
        inizia();
    }// end of constructor

    private void inizia() {
        List<Pref> lista = Pref.getListAll();
        TypePref tipoVecchio;
        PrefType tipoNuovo;
        Object valueOld;

        for (Pref pref : lista) {
            tipoVecchio = pref.getType();
            tipoNuovo = pref.getTipo();

            if (tipoVecchio != null && tipoNuovo == null) {
                switch (tipoVecchio) {
                    case booleano:
                        valueOld = pref.getBoolean();
                        pref.setTipo(PrefType.bool);
                        pref.setValore(valueOld);
                        pref.setBool(false);
                        pref.setType(null);
                        pref.save();
                        break;

                    case intero:
                        valueOld = pref.getIntero();
                        pref.setTipo(PrefType.integer);
                        pref.setValore(valueOld);
                        pref.setIntero(0);
                        pref.setType(null);
                        pref.save();
                        break;

                    case data:
                        valueOld = pref.getDataOld();
                        pref.setTipo(PrefType.date);
                        pref.setValore(valueOld);
                        pref.setData(null);
                        pref.setType(null);
                        pref.save();
                        break;


                    case stringa:
                        valueOld = pref.getStringa();
                        pref.setTipo(PrefType.string);
                        pref.setValore(valueOld);
                        pref.setStringa("");
                        pref.setType(null);
                        pref.save();
                        break;

                    default: // caso non definito
                        break;
                } // fine del blocco switch
                pref.save();
            }// end of if cycle
        }// end of for cycle

    }// end of method

}// end of bootstrap class
