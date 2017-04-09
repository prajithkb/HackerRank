package com.hackerrank.solutions;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * <h> Aho Corasick</h>
 * <p>
 * This is an implementation of the https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm
 * Aho Carsick Pattern matching.
 * <p>
 * Informally, the algorithm constructs a finite state machine that resembles a trie with additional links between the various internal nodes.
 * These extra internal links allow fast transitions between failed string matches (e.g. a search for cat in a trie that does not contain cat,
 * mbut contains cart, and thus would fail at the node prefixed by ca), to other branches of the trie that share a common prefix
 * (e.g., in the previous case, a branch for attribute might be the best lateral transition).
 * This allows the automaton to transition between string matches without the need for backtracking.
 * <p>
 * The complexity of the algorithm is linear in the length of the strings plus the length of the searched text plus the number of output matches.
 * mNote that because all matches are found,there can be a quadratic number of matches if every substring matches (e.g. dictionary = a, aa, aaa, aaaa and input string is aaaa).
 *
 * @author kprajith
 */
public class AhoCarsickAlgorithmBasedSearchEngine {

    private static int MAX_NUMBER_OF_STATES = 12;
    private static int NUMBER_OF_ALPHABETS = 26;
    private int[][] trie = new int[MAX_NUMBER_OF_STATES][NUMBER_OF_ALPHABETS];
    private static int NIL_STATE = -1;
    private static int START_STATE = 0;
    private List<Set<Integer>> wordEndingStates = Lists.newArrayList();

    private int[] failedStates = new int[MAX_NUMBER_OF_STATES];
    private String[] dictionary;


    /**
     * package qualified constructor
     *
     * @param words
     */
    AhoCarsickAlgorithmBasedSearchEngine(String[] words) {
        Preconditions.checkNotNull(words);
        this.dictionary = words;
        initializeMembers();
        initializeMatchingMachine(convertToLowerCase(words));
        initializeFailedStates();
    }

    void initializeMembers() {
        for (int i = 0; i < MAX_NUMBER_OF_STATES; i++) {
            Arrays.setAll(trie[i], value -> -1);
        }
        Arrays.setAll(failedStates, value -> -1);
        for (int i = 0; i < MAX_NUMBER_OF_STATES; i++) {
            wordEndingStates.add(Sets.newHashSet());
        }
    }

    static String[] convertToLowerCase(String[] words) {
        return Arrays.stream(words).map(String::toLowerCase).toArray(String[]::new);
    }


    /**
     * This is an implementation of the https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm
     * Aho Carsick Pattern matching.
     * <p>
     * Informally, the algorithm constructs a finite state machine that resembles a trie with additional links between the various internal nodes.
     * These extra internal links allow fast transitions between failed string matches (e.g. a search for cat in a trie that does not contain cat,
     * but contains cart, and thus would fail at the node prefixed by ca), to other branches of the trie that share a common prefix
     * (e.g., in the previous case, a branch for attribute might be the best lateral transition).
     * This allows the automaton to transition between string matches without the need for backtracking.
     * <p>
     * The complexity of the algorithm is linear in the length of the strings plus the length of the searched text plus the number of output matches.
     * Note that because all matches are found,there can be a quadratic number of matches if every substring matches (e.g. dictionary = a, aa, aaa, aaaa and input string is aaaa).
     */
    public static AhoCarsickAlgorithmBasedSearchEngine createEngine(String[] words) {
        AhoCarsickAlgorithmBasedSearchEngine ahoCarsickAlgorithmBasedSearchEngine = new AhoCarsickAlgorithmBasedSearchEngine(words);
        return ahoCarsickAlgorithmBasedSearchEngine;
    }


    private void initializeMatchingMachine(String[] words) {
        int states = 1;
        for (int i = 0; i < words.length; i++) {
            char[] currentWord = words[i].toCharArray();
            int currentState = START_STATE;
            for (int j = 0; j < currentWord.length; j++) {
                int character = convertToInt(currentWord[j]);
                if (trie[currentState][character] == NIL_STATE) {
                    trie[currentState][character] = states++;
                }
                currentState = trie[currentState][character];
            }
            // Mark that the word has ended
            wordEndingStates.get(currentState).add(i);

        }
        for (int ch = 0; ch < NUMBER_OF_ALPHABETS; ++ch) {
            if (trie[0][ch] == NIL_STATE)
                trie[0][ch] = START_STATE;
        }

    }

    static String stringify(int[][] trie) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < trie.length; i++) {
            boolean isEmpty = true;
            for (int j = 0; j < trie[i].length; j++) {
                if (trie[i][j] > 0) {
                    isEmpty = false;
                    stringifyNode(sb, i);
                    sb.append("->");
                    sb.append((char) ('a' + j));
                    stringifyNode(sb, trie[i][j]);
                    sb.append(") ");
                }
            }
            if (isEmpty) {
                stringifyNode(sb, i);
                sb.append("-> {END}");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static void stringifyNode(StringBuilder sb, int i) {
        sb.append("(");
        sb.append(i);
        sb.append(")");
    }

    private void initializeFailedStates() {
        Queue<Integer> queue = Queues.newLinkedBlockingQueue();
        for (int i = 0; i < NUMBER_OF_ALPHABETS; i++) {
            if (trie[0][i] != 0) {
                failedStates[trie[0][i]] = 0;
                queue.add(trie[0][i]);
            }
        }
        while (!queue.isEmpty()) {
            int state = queue.poll();
            for (int i = 0; i < NUMBER_OF_ALPHABETS; i++) {
                if (trie[state][i] != NIL_STATE) {
                    int failure = failedStates[state];
                    while (trie[failure][i] == -1) {
                        failure = failedStates[failure];
                    }
                    failure = trie[failure][i];
                    failedStates[trie[state][i]] = failure;
                    wordEndingStates.get(trie[state][i]).addAll(wordEndingStates.get(failure));
                    queue.add(trie[state][i]);
                }
            }
        }


    }

    int findNextState(int currentState, char nextCharacter) {
        int result = currentState;
        int ch = convertToInt(nextCharacter);
        while (trie[result][ch] == -1) {
            result = failedStates[result];
        }
        return trie[result][ch];
    }

    public void printOccurrences(String input) {
        System.out.println("Printing all the occurrences in the input " + input);
        int currentState = 0;
        for (int i = 0; i < input.length(); i++) {
            currentState = findNextState(currentState, input.charAt(i));
            if (wordEndingStates.get(currentState).isEmpty()) {
                continue;
            }
            for (int j = 0; j < dictionary.length; j++) {
                if (wordEndingStates.get(currentState).contains(j)) {
                    System.out.println("Word `" + dictionary[j] + "` appears from " + (i - dictionary[j].length() +1 ) + " to " + i);
                }
            }
        }
    }

    private int convertToInt(char nextCharacter) {
        return nextCharacter - 'a';
    }

    public static void main(String[] args) {
        String[] dictionary = new String[]{"he", "she", "hers", "his"};
        AhoCarsickAlgorithmBasedSearchEngine searchEngine = AhoCarsickAlgorithmBasedSearchEngine.createEngine(dictionary);
        System.out.println(stringify(searchEngine.trie));
        System.out.println(searchEngine.wordEndingStates);
        System.out.println(Arrays.toString(searchEngine.failedStates));
        System.out.println("Created Search Engine");
        searchEngine.printOccurrences("ahishers");
    }

}
