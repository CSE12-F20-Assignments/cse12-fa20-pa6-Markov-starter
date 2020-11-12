
---

# PA6: Markov Chains(open)
---

**This assignment is open to collaboration.**

This assignment will teach you how to use Hash Maps and apply them to a basic learning algorithm known as a Markov Chain. 



This PA is due on ** **Wednesday, November 18 at 11:59pm** **  
The Gradescope autograder will be released on ** **Saturday, November 14** **

Link to FAQ: [https://piazza.com/class/kffnb3soo432qi?cid=714](https://piazza.com/class/kffnb3soo432qi?cid=714)  
Link to PA Video: [https://canvas.ucsd.edu/courses/18854/external_tools/82](https://canvas.ucsd.edu/courses/18854/external_tools/82) 


## Getting the Code
The starter code is in the Github repository that you are currently looking at. If you are not familiar with Github, here are two easy ways to get your code.

1. Download as a ZIP folder 

    If you scroll to the top of Github repository, you should see a green button that says *Code*. Click on that button. Then click on *Download ZIP*. This should download all the files as a ZIP folder. You can then unzip/extract the zip bundle and move it to wherever you would like to work. The code that you will be changing is in the folder called *pa4-starter*.

2. Using git clone (requires terminal/command line)

    If you scroll to the top of the Github repository, you should see a green button that says *Code*. Click on that button. You should see something that says *Clone with HTTPS*. Copy the link that is in that section. In terminal/command line, navigate to whatever folder/directory you would like to work. Type the command `git clone _` where the `_` is replaced with the link you copied. This should clone the repository on your computer and you can then edit the files on whatever IDE you see fit.
    
If you are unsure or have questions about how to get the starter code, feel free to make a Piazza post or ask a tutor for help.

## Part 1: An Implementation of `DefaultMap` (17 points)
You’ll provide a fast implementation of an interface called `DefaultMap` in `MyHashMap.java`.  


You will implement all the methods defined in the `DefaultMap` interface. You must ensure that each has the specified big-O bound in the average case, and argue why based on the documentation of any libraries you use, or based on your implementation. Note that these are big-O bounds, so doing _better_ (like O(1) where O(log(n)) is required) is acceptable. In each, _n_ represents the number of entries in the map.

- `put`, required O(n)
- `replace`, required _O(n)_
- `remove`, required _O(n)_
- `set`, required _O(log(n))_
- `get`, required _O(log(n))_
- `size`, required _O(1)_
- `isEmpty`, required _O(1)_
- `containsKey`, required _O(log(n))_
- `keys`, required _O(n)_

In `MyHashMap` you will implement a single constructor that takes two arguments (the
initial capacity and the load factor). This is where you will initialize the instance variables. 
 
### `HashMapEntry<K, V>`
Within `myHashMap.java`, you are provided the private inner class `HashMapEntry<K, V>`. Use this class to represent your key value pairs in your HashMap. Note it implements the `DefaultMap.Entry<K, V>` interface. Each `HashMapEntry` object has two instance variables (`K key`, `V value`). Use the getters/setters from this class to get the key values and set the values. 

### Instance Variables
- `loadFactor`: the load factor for when to increase the capacity of the HashMap (default = 0.75)
- `capacity`: the overall capacity of the HashMap (initial default = 16)
- `size`: the number of elements in the HashMap
- `buckets` or `entries`: If you would like to using Separate chaining conflict resolution, use the List array `buckets`. If you would like to use Linear Probing for your conflict resoultion, use the array `entries`. For either case, `buckets` or `entries` is where your elements will be stored.

**Note:** `initialCapacity` must be *non-negative* and `loadFactor` must be *positive*. If either of these conditions are violated, you must throw an `IllegalArgumentException` in your constructor with the appropriate error message. 

The specifications for the other methods are defined in header comments in the `DefaultMap.java` file, which you should follow. You may use any methods in the Java collections library to implement them (including helpers like `Collections.sort`). If you don't know how to use a particular library method, interface, or class in the standard Java utils, ask! This is an opportunity to learn about the built-in libraries.
***Note:*** You are not allowed to use the java HashMap library!!!

You may find these useful:
- [`Set`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Set.html)
- [`Collection`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Collection.html)

The pages linked from those may also have useful information.  Your
implementation of `DefaultMap` will be graded automatically by tests that we
provide. We’ll provide a subset of the tests in the grader.

## Part 2: Implementing the Markov Chain (16 points)
You will be implementing a simple predictive model to generate sentences known as a Markov Chain. A Markov Chain is a probabilistic, generative model in which, given some initial event, the probability of future events is directly related to the previous event. That is, the *transition* from event A to event B is given by the probability that event B occurs after event A. A Markov Chain can be visualized with a state machine diagram such as the following:
![](https://i.imgur.com/vfQ7hvO.png)

You will implement a text generator Markov Chain, where we'll refer to the "events" or "states" of the model as **prefixes**. In order to **train** your model, you will need to build its vocabulary (wordMap) based on the Chain's **degree** (aka order). For example, if the degree of your model is 2, then all prefixes will be of length 2. Thus, your vocabulary will consist of the *mapping* from all prefixes in some training text to a list of all words that come after it in the same training text. A transition from one prefix to the next will be determined by randomly selecting a *next word* of the prefix given the probabilities of the words that follow that prefix.


(to learn more check out this [link](https://medium.com/swlh/machine-learning-algorithms-markov-chains-8e62290bfe12)). 

#### Instance Variables
##### `wordMap`
This is the vocabulary of your model. Each key represents one **prefix** in the model, and each value represents a HashMap of every word that follows that prefix, mapped to the number of times that word occurred after the prefix. 
##### `wordMapCache`
This is also the vocabulary of your model (with a catch down below). Each key represents one **prefix** in the model. However, each value is a `List` of words that follow that prefix. Each word in the list must occurr as many times as it occurred after the prefix. 
##### `degree`
This is the **degree** or order of your model. This determines the length of each **prefix** in the vocabulary of your model. 
##### `rng`
This is a **pseudo-random** number generator (prng or rng) which is initialized outside of the `MarkovModel` class. You will use this rng to randomly select an initial prefix (state 0 above). You will also use this rng to randomly select the next word in a generated sentence (the state transition above). 

Getting deterministic output: using the `Random` class in Java, you can create a **seeded** rng using `new Random(seed)` where seed is an integer. A seeded rng will always produce the *same* sequence between different runs of your program. 

For more on rngs and how to use the `Random` class, check out the [Javadocs](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Random.html#%3Cinit%3E(long)). 

### Required Methods
#### train

`public int train(String trainingText)`
`train` takes one argument, `trainingText`, and uses the words from the text to populate `wordMap`. It then returns the word count of the training text. 

For each prefix of length `degree` in `trainingText`, update its `wordMap` entry given the word immediately following the prefix. For example, assume the prefix is "current prefix" and the word is "nextword". The `wordMap` entry for prefix would like this: 

entry: "current prefix": {"nextword":1}

Now assume that later we get the same prefix and word. The entry will now like this:

entry: "current prefix": {"nextword":2}

Note: the degree of your model will affect how many words the model is trained with. For example, a Markov model with degree=3 will be trainied with 1 more word than a model with degree=2 trained on the same text.

#### generateSentence

`public String generateSentence(int wordCount)`
This method is provided in the starter code, however, the helper methods that are used in this method are not given. You have the option to either complete the helper methods (descriptions below) or implement this method from scratch. Either way **do not** modify the method header. 

`generateSentence` takes one argument, `wordCount`, and generates a sentence (`String`) of exact length `wordCount` **in words**. 

To generate a sentence, you must first use `rng` to randomly select one of the prefixes in `wordMap`. This is your initial prefix (state 0 in the state diagram above). Then, use `rng` to randomly generate the next word based on the current prefix of your sentence using `wordMapCache`. 

**Important** To generate a word, you must build and use the `wordMapCache` as follows: 

*to build*  
Given the current prefix, create a list of the words that follow that prefix, where each word is present in the list as many times as it followed the prefix. 

*to use*   
Use`rng` to randomly select a word from this list **and remove that word from the list**. 

If the list has been exhausted (all words have been removed) or it does not exist yet, repeat the build step first. 

### Example
`trainingText`: "Hello there, world. Hello there, everyone. Hello there, CSE students! Hello there, world."
`degree`: 2

`wordMap`: 
{
    "Hello there,": {"world." : 2, "everyone.": 1, "CSE": 1}, 
    "there, world.": {"Hello": 2},
    "world. Hello": {"there,": 2},
    "there, everyone.": {"Hello": 1},
    "everyone. Hello": {"there,": 1},
    "there, CSE": {"students!" : 1},
    "CSE students!": {"Hello":1},
    "students! Hello": {"there,":1}
}

Note that for the key "there, world", the value "Hello" has a count of 2. This is because we want every prefix in the sentence to have possible words that follow it, so we did a wrap-around here that makes "Hello" in the beginning follow "there, world" at the end. You don't need to worry about it in your implementation though. We already took care of it in main(). This is the same case with "there" having a count of 2 for "world. Hello".

Assume that we pick "Hello there" as our initial prefix. Then, its entry in `wordMapCache` would look like the following:

"Hello, there" : {"world", "world", "everyone", "CSE"}

Notice that "world" appeared twice in the list. Now assume we generate the word "world". Remember, once we use a word we remove the word from the list. The entry would now look like the following: 

"Hello, there" : {"world", "everyone", "CSE"} 

Note: For this PA, punctuation should not be ignored. That means that, for example, a word `"word"` is **not** the same as `"word!"`. 

Note: You may find the methods `trim()` and `split()` from the `String` class useful. Check out the [Javadocs](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html) for information on how to use these.


### Recommended Helper Methods
#### Why use helper methods? 
Helper methods allow for better modularity within your code. By creating a seperate method to handle a specific job, you can call that method over and over rather than having to write out the code again. Helper methods also help you see the steps of your code in a clear way, allowing you to understand the logic behind what you are writing.

*The following methods are **not** required, however, they will be helpful to you and make your code more readable.*

#### updateWordMap
`private void updateWordMap(String prefix, String word)`
Use this method to update the `wordMap` instance variable. Given `prefix`, increment the mapped words count for this word by 1. If the map for the given `prefix` does not exist, create it.

#### generateWord
`private String generateWord(String prefix)`
Randomly generate a word for the `wordMapCache` entry for the given prefix, then remove that word from the entry. 

#### getPossibleWords
`private ArrayList<String> getPossibleWords(String prefix, MyHashMap<String, Integer> counts)`
First, check if an entry exists for the given prefix in `wordMapCache`. If it does and **it is not empty** just return it. If it is empty or it doesn't exist, perform the *build* step mentioned above. 

#### getNextPrefix
`private String getNextPrefix(String currentPrefix, String generatedWord)`
Given the `currentPrefix` which was used to generate the word `generatedWord`, get the next prefix in the sentence. 

## Testing
### MyHashMap - MyHashMapTest.java
In the starter code, we provide you with MyHashMapTest which has an example of how to unit test your implementaion. **Note**: For this PA, your unit tests will be graded for completion only, however, we **strongly** encourage you to thoroughly test every public method in your class (helper methods you create should inherently be *private*). You are required to have at least one unit test written by yourself. 

### MarkovModel - Main.java
In the starter code we provide you with a Main class where you can run your implementation. This is a simple program that:
1. reads a file
2. creates an instance of your MarkovModel with a specified degree and seeded rng
3. trains your model with the read file
4. Uses your model to generate a specified amount of sentences of a specified length. 

You can freely modify this file as you see fit for testing purposes. The following constants will be helpful for you:	
`DEFAULT_TRAINING_FILENAME` To set the filename of the file to read for training. Must be a relative path (just the name and extension).
`DEFAULT_MARKOV_DEGREE` To set the degree of the MarkovModel
`DEFAULT_TRAINING_SEED` To seed the rng used by the MarkovModel
`DEFAULT_SENTENCE_LENGTH` To set the length of sentences that your MarkovModel will generate
`NUM_TRIALS` To set the number of sentences that your MarkovModel will be used to generate

**Note** we've provided some training files for you in the *starter/* directory. If you wish to use your own files for training, they must be placed in this folder as well.
## Part 3: Gradescope Assignment (11 points)

You will write answers to the following questions on Gradescope:

- For each method in `MyHashMap` (excluding `size` and `isEmpty`), argue why its performance meets the specified required bound.
- Which type of collision handling did you use? Why did you choose to use this one over the other?
- Discuss the runtime for the method `train`, answering the following questions: How much real time (nanoseconds) does it take to train on the sample data "Odyssey.txt"? What is its asymptotic complexity in terms of a tight big-O bound? In particular, how do you describe the input size?
- Discuss the runtime for the method `generateSentence`, answering the following questions: How much real time (nanoseconds) does it take to generate a sentence after training? How does the `wordCount` affect the runtime? What is the asymptotic complexity of **performing a single sentence generation** in terms of a tight big-O bound? In particular, how do you measure the size of the input?


### Style (5 points)

This PA has the same style guidelines as PA5.

- Lines **must not** be longer than 100 characters except for method headers
- Indentation must be consistent
- Test method names must have meaning related to the test
- Lines **must not** be indented more than 6 times. If you have a need to
  indent more than 6 levels, build a helper method or otherwise reorganize your code
- Helper method names should describe their purpose
- If you write a helper method with a body longer than 2 statements, **you
  must** add a header comment (a comment above the method) that summarizes what it does in English



## Submitting

#### Part 1 & 2
On the Gradescope assignment **Programming Assignment 6 - code** please submit the following file structure:

* MyHashMap.java
* MyHashMapTest.java
* MarkovModel.java

The easiest way to submit your files is to drag them individually into the submit box and upload that to Gradescope. You may submit as many times as you like till the deadline. 

#### Part 3
Please submit your answers to the questions from part 3 on the Gradescope assignment **Programming Assignment 6 - questions**. You may submit as many times as you like till the deadline.


## Scoring (50 points total)

- 17 points: implementation of `DefaultMap` [automatically graded]
- 16 points: Markov Chain
  - 8 points for `train` [automatically graded]
  - 8 points for `generateSentence` [automatically graded]
- 1 point: MyHashMapTest graded on completition [manually graded]
- 11 points: Gradescope Questions [manually graded]
- 5 points: Style [manually graded]

There is no bad implementation testing on this PA. However, we highly recommend you use the practices you know from testing to thoroughly check that `MyHashMap` and the Markov Chain helpers you wrote work as expected.


