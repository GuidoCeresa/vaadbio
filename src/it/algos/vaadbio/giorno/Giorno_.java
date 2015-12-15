package it.algos.vaadbio.giorno;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Giorno.class)
public class Giorno_ extends BaseEntity_ {
    public static volatile SingularAttribute<Giorno, String> mese;
    public static volatile SingularAttribute<Giorno, String> nome;
    public static volatile SingularAttribute<Giorno, String> titolo;
    public static volatile SingularAttribute<Giorno, Integer> normale;
    public static volatile SingularAttribute<Giorno, Integer> bisestile;
}// end of entity class
