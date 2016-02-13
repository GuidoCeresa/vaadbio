package it.algos.vaadbio.delta;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Delta.class)
public class Delta_ extends BaseEntity_ {
	public static volatile SingularAttribute<Delta, String> sorgente;
	public static volatile SingularAttribute<Delta, String> valido;
	public static volatile SingularAttribute<Delta, String> differenza;
}// end of entity class
