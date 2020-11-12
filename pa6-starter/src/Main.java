import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
	
	public static final int DEFAULT_MARKOV_DEGREE = 2;
	public static final int DEFAULT_SENTENCE_LENGTH = 100; 
	
	// Use the training seed to make your pseudo-random output deterministic
	public static final int DEFAULT_TRAINING_SEED = 20;
	public static final String DEFAULT_TRAINING_FILENAME = "Odyssey.txt";
	
	//Use this to configure how many sentences must be generated
	public static final int NUM_TRIALS = 1;

	public static void main(String[] args) {		

		String trainingData = getTrainingDataFromFile(DEFAULT_TRAINING_FILENAME);
		performMarkov(trainingData);
	}

	private static String getTrainingDataFromFile(String filename) {
		Path trainingFilePath = Paths.get(filename);
		BufferedReader reader = null;
		try { 
			reader = Files.newBufferedReader(trainingFilePath);
		} catch (IOException e) {
		    System.err.println(e);
		    System.exit(-1);
		}
		
		StringBuilder fileContents = readFile(reader);
		try {
			reader.close();
		} catch (IOException e) {
			System.err.println("An error occurred while closing the file");
			System.exit(-1);
		}
		
		if (fileContents == null) {
			System.exit(-1);
		}
		
		fileContents = sanitizeFileContents(fileContents);
		fileContents = wrapFileContents(fileContents);
		return fileContents.toString().trim();
	}
	
	private static StringBuilder readFile(BufferedReader fileReader) {
		StringBuilder fileContents = new StringBuilder();
		try {
			String line = null;
			while ((line = fileReader.readLine()) != null) {
				fileContents.append(line.trim() + " ");
			}
		} catch (IOException e) {
			System.err.println(e);
			System.err.println("An error occurred while reading from the file");
			return null;
		}
		return fileContents;
	}
	
	private static StringBuilder sanitizeFileContents(StringBuilder fileContents) {
		String[] words = fileContents.toString().split("\\s");
		StringBuilder sanitizedFileContents = new StringBuilder(fileContents.length());
		
		for (String word : words) {
			if (word.isEmpty() || word.trim().isEmpty()) {
				continue;
			}
			sanitizedFileContents.append(word + " ");
		}
		
		return sanitizedFileContents;
	}
	
	private static StringBuilder wrapFileContents(StringBuilder fileContents) {
		String[] trainingWords = fileContents.toString().split("\\s");
		
		int wordIdx = 0;
		for (int i = 0; i < DEFAULT_MARKOV_DEGREE; ++i) {
			fileContents.append(trainingWords[wordIdx] + " ");
			
			wordIdx = (wordIdx == trainingWords.length - 1) ? 0 : wordIdx + 1;
		}
		
 		return fileContents;
	}
	
	private static void performMarkov(String fileContents) {
		Random rng = new Random(DEFAULT_TRAINING_SEED);
		MarkovModel markov = new MarkovModel(rng, DEFAULT_MARKOV_DEGREE);
		System.out.format("Training Markov Model from file %s with degree %d.\n", DEFAULT_TRAINING_FILENAME, DEFAULT_MARKOV_DEGREE);
		int wordsTrainedWith = markov.train(fileContents);
		System.out.format("Number of words used for training: %d\n", wordsTrainedWith);
		System.out.format("Generating %d sentence(s) with length: %d\n", NUM_TRIALS, DEFAULT_SENTENCE_LENGTH);
		for (int i = 0; i < NUM_TRIALS; ++i) {
			String actualSentence = markov.generateSentence(DEFAULT_SENTENCE_LENGTH);
			System.out.print("Actual Length of Sentence: " + actualSentence.split("\\s").length);
			System.out.print(" Sentence: ");
			System.out.println(actualSentence);
		}
	}

}
