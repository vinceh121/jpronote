package me.vinceh121.jpronote;

public abstract class AbstractPronoteEntity implements IPronoteEntity {
	private String id;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;
	}
}
