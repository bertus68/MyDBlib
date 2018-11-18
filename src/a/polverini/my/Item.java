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
	
	/**
	 * @return the item properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param key
	 * @return the corresponding property
	 */
	public Object get(Object key) {
		return properties.get(key);
	}

	/**
	 * @param key
	 * @param val
	 */
	public void set(Object key, Object val) {
		properties.put(key, val);
	}
	
	/**
	 * the children list
	 */
	private List<Item> children = new ArrayList<>();

	/**
	 * @return true if the item has children
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	/**
	 * @return the item children
	 */
	public List<Item> getChildren() {
		return this.children;
	}

	/**
	 * @param child
	 */
	public void add(Item child) {
		children.add(child);
	}

}