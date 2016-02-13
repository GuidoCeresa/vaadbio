package it.algos.vaadbio.mese;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Mese.class)
public class Mese_ extends BaseEntity_ {
	public static volatile SingularAttribute<Mese, String> titoloBreve;
	public static volatile SingularAttribute<Mese, String> titoloLungo;
	public static volatile SingularAttribute<Mese, Integer> giorni;
	public static volatile SingularAttribute<Mese, Integer> giorniBisestili;
}// end of entity class
