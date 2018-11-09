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
	
	private final String tag;
	
	public String getTag() {
		return tag;
	}

	public Item() {
		this.parent = null;
		this.tag = null;
	}

	public Item(Item parent, String tag) {
		this.tag = tag;
		this.parent = parent;
		if(parent!=null) {
			this.parent.add(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return tag;
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