package a.polverini.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Item {

	public String getKey() {
		return "";
	}
	
	protected final Item parent;
	
	public Item getParent() {
		return this.parent;
	}
	
	private final String name;
	
	public String getName() {
		return name;
	}

	public Item() {
		this.parent = null;
		this.name = null;
	}

	public Item(Item parent, String name) {
		this.name = name;
		this.parent = parent;
		if(parent!=null) {
			this.parent.add(this);
		}
	}

	public final String toString() {
		return name;
	}

	private Properties properties = new Properties();

	public Object get(Object key) {
		return properties.get(key);
	}

	public void set(Object key, Object val) {
		properties.put(key, val);
	}
	
	private List<Item> children = new ArrayList<>();

	public List<Item> getChildren() {
		return this.children;
	}
	
	public void add(Item child) {
		children.add(child);
	}

}