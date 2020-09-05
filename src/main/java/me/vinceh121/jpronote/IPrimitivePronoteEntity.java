package me.vinceh121.jpronote;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IPrimitivePronoteEntity extends IPronoteEntity {
	/**
	 * The type of an entity is determined by the _T JSON attributes
	 * <b>These are subject to change</b>
	 *
	 *
	 * <b>-1</b> Undetermined type <br>
	 * <b>7</b> date <br>
	 * <b>8</b> time period <br>
	 * <b>10</b> Number <br>
	 * <b>11</b> time periods TODO: define format <br>
	 * <b>21</b> string <br>
	 * <b>24</b> list <br>
	 * <b>25</b> base64 image <br>
	 *
	 * @return The type of the implementing entity
	 */
	@JsonProperty("_T")
	int getType();

	@JsonProperty("_T")
	void setType(int type);

}
