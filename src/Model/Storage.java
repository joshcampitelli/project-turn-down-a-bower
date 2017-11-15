package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Model.Features.*;
/**
* The Model.Storage Class keeps a log of all learned metrics and their
* respective problems.
*
* Code and comments by:
* 
* @author Josh Campitelli
*/
public class Storage {
	private HashMap<String, ArrayList<GenericFeature>> learned;
	
	public Storage() {
		learned = new HashMap<>();
	}

	/*For UnitTests*/
	public int getSize() {
		return learned.size();
	}

	/**
	 * The getLearned method returns the learned metrics for all known
	 * problems stored in the HashMap.
	 * @return reference to the HashMap<String, ArrayList<GenericMetric>>
	 * @author Josh Campitelli
	 */
	public HashMap<String, ArrayList<GenericFeature>> getLearned() {
		return learned;
	}

	/**
	 * The insert method simply puts data into the HashMap, where the problem
	 * parameter represents the problem, and ArrayList are the known features.
	 * @param key identifies the problem (HashMap Key)
	 * @param features array of metrics to be inserted into HashMap
	 * @author Josh Campitelli
	 */
	public void insert(String key, ArrayList<GenericFeature> features) {
		learned.put(key, features);
	}

	/*
	* The remove method removes an instance of learned information from the hashmap.
	* Parameter represents the string key used to represent the
	* Ex:
	* 	remove("h1")
	* @author Ethan Morrill
	*/
	public void remove(String key){

		learned.remove(key);
		//Error Handling for if failed available
	}

	public void update(String key, ArrayList<GenericFeature> updatedInfo){

		learned.replace(key, updatedInfo);
		//Error Handling for if failed available
	}

	/**
	 * Returns hashmap of keys and features for a given featureName
	 * @param featureName identifies the name f the feature of interest
	 * @return Hashmap of {key, GenercFeature} for the provided featureName
	 * @author Ethan Morrill
	 */
	public HashMap<String, GenericFeature> getFeature(String featureName){
		HashMap<String, GenericFeature> targetFeatures = new HashMap<>();
		Set<String> keys = learned.keySet();
		for(String key : keys){
			ArrayList<GenericFeature> values = learned.get(key);
			for (GenericFeature feature : values) {
				if(feature.getName().equals(featureName)){
					targetFeatures.put(key,feature);
				}
			}
		}
		return targetFeatures;
	}
}
