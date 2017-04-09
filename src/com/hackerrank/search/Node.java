package com.hackerrank.search;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data
public class Node {

    @Getter
    @Setter
    private Node failure;

    @Getter
    @Setter
    private int state;

    Node() {
    }

    Node(int state) {
        this.state = state;
    }

    private Map<Character, Node> characters = Maps.newHashMap();

    @Getter
    @Setter
    private String word;


    boolean isLeaf() {
        return word != null;
    }

    void add(@NonNull Character character, @NonNull Node node) {
        characters.put(character, node);
    }

    Node get(@NonNull Character character) {
        return characters.get(character);
    }

    Set<Character> characters() {
        return characters.keySet();
    }

    boolean hasCharacter(Character character) {
        return characters().contains(character);
    }

    boolean hasFailure() {
        return failure != null;
    }

    Collection<Node> nextStates() {
        return characters.values();
    }

    void clearFailure() {
        setFailure(null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (word != null) {
            sb.append("(");
            sb.append(state).append(")");
            sb.append("->");
            sb.append(word);
            sb.append(" ");
        }

        characters.forEach((k, v) -> {
            if (v != null) {
                sb.append("(");
                sb.append(state).append(")");
                sb.append("->");
                sb.append(k);
                sb.append("->");
                sb.append("(").append(v.getState()).append(") ");
            }
        });

        if (hasFailure()) {
            sb.append(" * Failure * ").append("(").append(state).append(") -> ").append(failure.getState());
        }
        return sb.toString();
    }

}
