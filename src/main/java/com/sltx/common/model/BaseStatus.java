package com.sltx.common.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * state base class
 *
 */
public abstract class BaseStatus {

	private Map<String, String> map = new LinkedHashMap<String, String>();

	/**
	 * add status
	 * @param key
	 * @param desc
	 */
	public void add(String key, String desc) {
		map.put(key, desc);
	}

	/**
	 * Return description according to key
	 * @param key
	 * @return
	 */
	public String desc(String key) {
		return map.get(key);
	}

	/**
	 * Return the key according to the description
	 * @param desc
	 * @return
	 */
	public String key(String desc) {
		for (String _key : map.keySet()) {
			if (desc.equals(map.get(_key))) {
				return _key;
			}
		}

		return null;
	}

	/**
	 * return all status
	 * @return
	 */
	public Map<String, String> all() {
		return map;
	}

}
