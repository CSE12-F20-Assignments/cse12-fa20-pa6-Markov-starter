import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarkovModel {
	
	// Key = prefixes, Value = [ Key = word, Value = appearances after prefix]
	private MyHashMap<String, MyHashMap<String, Integer>> wordMap; 
	// Key = Prefixes, Value = Probability distribution of next word
	private MyHashMap<String, ArrayList<String>> wordMapCache;
	private int degree;
	private Random rng;
	
	public MarkovModel(Random rng, int degree) {
		wordMap = new MyHashMap<>();
		wordMapCache = new MyHashMap<>();
		this.rng = rng;
		this.degree = degree;
	}
	
	
	public int train(String trainingText) {
		// TODO
		return 0;
	}
	
	// Keeps track of how many times the given word has occurred when preceded prefix
	private void updateWordMap(String prefix, String word) {
		// TODO? 
	}
	
	public String generateSentence(int wordCount) {
		List<String> prefixes = wordMap.keys();
		int initialPrefixIdx = rng.nextInt(prefixes.size());
		String initialPrefix = prefixes.get(initialPrefixIdx);
		StringBuilder sentence = new StringBuilder(wordCount);
	
		sentence.append(initialPrefix);
		
		String currentPrefix = initialPrefix;
		for (int i = 0; i < wordCount - degree; ++i) {
			String generatedWord = generateWord(currentPrefix);
			
			// not enough training text to generate entire sentence
			if (generatedWord == null) {
				break;
			}
			sentence.append(" " + generatedWord);
			currentPrefix = getNextPrefix(currentPrefix, generatedWord);
		}
		
		return sentence.toString();
	}
	
	/**
	 * Uses the probability distribution of the possible next words to generate
	 * a random word
	 * @param prefix the prefix before the generated words
	 * @return the randomly generated word
	 */
	private String generateWord(String prefix) {
		var counts = wordMap.get(prefix);
		if (counts == null) {
			return null;
		}
		ArrayList<String> possiblePredictions = getPossibleWords(prefix, counts);
		String predictedWord = possiblePredictions.get(rng.nextInt(possiblePredictions.size()));
		possiblePredictions.remove(predictedWord);
		return predictedWord;
	}


	// generate probability distribution of next word for the prefix
	private ArrayList<String> getPossibleWords(String prefix, MyHashMap<String, Integer> counts) {
		// TODO: 
		return null;
	}
	
	private String getNextPrefix(String currentPrefix, String generatedWord) {
		return currentPrefix.substring(currentPrefix.indexOf(' ') + 1) + " " + generatedWord;
	}
	
}