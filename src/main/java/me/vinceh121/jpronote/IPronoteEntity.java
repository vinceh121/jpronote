package me.vinceh121.jpronote;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IPronoteEntity {
	/**
	 * The type of an entity is determined by the G or _T JSON attributes
	 * <b>These are subject to change</b>
	 * 
	 * 
	 * <b>-1</b> Undetermined type
	 * <b>0</b> class or event
	 * <b>2</b> group
	 * <b>3</b> teacher
	 * <b>4</b> student
	 * <b>7</b> date
	 * <b>10</b> grading (grade, average, max...)
	 * <b>12</b> graded subject
	 * <b>16</b> subject
	 * <b>17</b> classroom
	 * <b>20</b>
	 * <b>24</b> list
	 * <b>25</b> base64 image
	 * <b>60</b> test
	 * 
	 * @return The type of the implementing entity
	 */
	@JsonProperty("G")
	public int getType();

	@JsonProperty("_T")
	public void setType(int type);

	public String getId();

	public void setId(String id);

}
