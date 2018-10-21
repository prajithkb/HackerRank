package com.hackerrank.graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class DisjointSet<Item> {

    public DisjointSet(int size){
        parents = new Element[size+1];
    }

    private final Element[] parents;

    @Builder
    @Getter
    @ToString
    public static class Element<Item> {

        private Item value;

        private int index;
    }

    public void union(Element<Item> from, Element<Item> to) {
        parents[findOrCreate(from).getIndex()] =  findOrCreate(to);
    }

    private Element<Item> findOrCreate(Element<Item> item){
        Optional<Element<Item>> itemRep = find(item);
        if (!itemRep.isPresent()){
            parents[item.getIndex()] = item;
        }
        return itemRep.orElse(item);
    }

    public List<Set<Integer>> getConnectedComponents(){
        final Map<Integer, Set<Integer>> connectedComponents = new HashMap<>();
        for (int i = 1; i < parents.length; i++) {
            final int currentIndex = i;
            find(currentIndex)
                    .map(Element::getIndex)
                    .ifPresent(index -> {
                        final Set<Integer> parts = connectedComponents.getOrDefault(index, new HashSet<>());
                        parts.add(currentIndex);
                        connectedComponents.put(index, parts);
                    });
        }
        return connectedComponents
                .entrySet()
                .stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    public String toString() {
        return getConnectedComponents().toString();
    }

    public Optional<Element<Item>> find(Integer index) {
       return index < parents.length ? find(parents[index]) : Optional.empty();
    }

    public Optional<Element<Item>> find(Element<Item> element) {
        if(element == null || parents.length <= element.getIndex() || parents[element.getIndex()] == null){
            return Optional.empty();
        }
        if (parents[element.getIndex()]
                .equals(element)) {
            return Optional.of(element);
        } else {
            Optional<Element<Item>> result = find(parents[element.getIndex()]);
            parents[element.getIndex()] =  result.get();
            return result;
        }
    }

    public static <Item> Element<Item> toElement(Item item, int index) {
        return Element.<Item>builder()
                .value(item)
                .index(index)
                .build();
    }
}
