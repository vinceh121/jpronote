package me.vinceh121.jpronote;

public abstract class AbstractPronoteEntity implements IPronoteEntity {
	private int type = -1;
	private String id;

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
