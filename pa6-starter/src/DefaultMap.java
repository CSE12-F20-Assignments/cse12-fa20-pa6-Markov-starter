import java.util.List;

/**
 * 
 * @author Juan Dominguez
 * @author Qiyue Wang
 *
 * @param <K> The type of the keys in this DefaultMap
 * @param <V> The type of the values in this DefaultMap
 */
public interface DefaultMap<K, V> {
	
	/**
	 * Internal Representation of every (key, value) pair in this DefaultMap
	 */
	interface Entry<K, V> {
		K getKey();
		V getValue();
		
		void setValue(V value);
	}

	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean put(K key, V value) throws IllegalArgumentException;
	
	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean replace(K key, V newValue) throws IllegalArgumentException;
	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean remove(K key) throws IllegalArgumentException;
	
	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgument exception if the key is null
	 */
	void set(K key, V value) throws IllegalArgumentException; 
	
	/**
	 * @return the value corresponding to the specified key, null if key doesn't 
	 * exist in hash map
	 * @throws IllegalArgument exception if the key is null
	 */
	V get(K key) throws IllegalArgumentException;
	
	/**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	int size();
	
	/**
	 * 
	 * @return true iff this.size() == 0 is true
	 */
	boolean isEmpty();
	
	/**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean containsKey(K key) throws IllegalArgumentException;
	
	/**
	 * 
	 * @return an array containing the keys of this DefaultMap. If this DefaultMap is 
	 * empty, returns array of length zero. 
	 */
	
	List<K> keys();
}
