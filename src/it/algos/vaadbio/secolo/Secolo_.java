package it.algos.vaadbio.secolo;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Secolo.class)
public class Secolo_ extends BaseEntity_ {
	public static volatile SingularAttribute<Secolo, String> titolo;
	public static volatile SingularAttribute<Secolo, Integer> inizio;
	public static volatile SingularAttribute<Secolo, Integer> fine;
	public static volatile SingularAttribute<Secolo, Boolean> anteCristo;
}// end of entity class
