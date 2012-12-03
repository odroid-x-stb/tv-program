package fr.enseirb.odroidx.tv;

public class Channel {

	private String name;
	private int number;
	private int resourceId;
	
	public Channel(String name, int number, int ressourceId) {
		this.name = name;
		this.number = number;
		this.resourceId = ressourceId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
}
