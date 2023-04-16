package me.vinceh121.jpronote.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractBasicPronoteEntity implements IIDEntity, INamedEntity {
	@JsonProperty("N")
	private String id;
	@JsonProperty("L")
	private String name;
	@JsonProperty("G")
	private int unknownG;
	@JsonProperty("P")
	private int unknownP;
	@JsonProperty("A")
	private boolean unknownA;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public int getUnknownG() {
		return unknownG;
	}

	public void setUnknownG(int unknownG) {
		this.unknownG = unknownG;
	}

	public int getUnknownP() {
		return unknownP;
	}

	public void setUnknownP(int unknownP) {
		this.unknownP = unknownP;
	}

	public boolean isUnknownA() {
		return unknownA;
	}

	public void setUnknownA(boolean unknownA) {
		this.unknownA = unknownA;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, unknownA, unknownG, unknownP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractBasicPronoteEntity other = (AbstractBasicPronoteEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && unknownA == other.unknownA
				&& unknownG == other.unknownG && unknownP == other.unknownP;
	}

	@Override
	public String toString() {
		return "AbstractBasicPronoteEntity [id="
				+ id
				+ ", name="
				+ name
				+ ", unknownG="
				+ unknownG
				+ ", unknownP="
				+ unknownP
				+ ", unknownA="
				+ unknownA
				+ "]";
	}
}
