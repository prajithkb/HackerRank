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
import lombok.Setter;
import lombok.ToString;

public class DisjointSet<Item> {

    public DisjointSet(int size){
        elements = new Element[size+1];
        parentIndexes = new Integer[size +1];
    }

    private final Element[] elements;
    private final Integer[] parentIndexes;

    @Builder
    @Getter
    @Setter
    @ToString
    public static class Element<Item> {

        private Item value;

        private int index;

        private int size;
    }

    public void union(Element<Item> from, Element<Item> to) {
        final Integer toIndex = findParentIndexOrCreate(to);
        final Integer fromIndex = findParentIndexOrCreate(from);
        parentIndexes[fromIndex] = toIndex;
        elements[toIndex].setSize(elements[toIndex].getSize() + elements[fromIndex].getSize());
    }

    public int sizeOfConnectedComponents(Element<Item> from){
        return find(from.getIndex())
                .map(i -> elements[i].getSize())
                .orElse(from.getSize());
    }

    private Integer findParentIndexOrCreate(Element<Item> item){
        Optional<Integer> itemRep = find(item.getIndex());
        if (!itemRep.isPresent()){
            parentIndexes[item.getIndex()] = item.getIndex();
            elements[item.getIndex()] = item;
        }
        return itemRep.orElse(item.getIndex());
    }

    public List<Set<Integer>> getConnectedComponents(){
        final Map<Integer, Set<Integer>> connectedComponents = new HashMap<>();
        Arrays
                .stream(elements)
                .filter(e -> e != null)
                .collect(Collectors.toMap(e -> e.getIndex(), e -> find(e.getIndex())))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().isPresent())
                .forEach(e -> {
                    final Set<Integer> parts = connectedComponents.getOrDefault(e.getValue().get(), new HashSet<>());
                    parts.add(e.getKey());
                    connectedComponents.put(e.getValue().get(), parts);
                });
        return connectedComponents
                .entrySet()
                .stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    public String toString() {
        return getConnectedComponents().toString();
    }

    public Optional<Integer> find(final Integer index) {
        if(index == null || parentIndexes.length <= index || parentIndexes[index] == null){
            return Optional.empty();
        }
        if (parentIndexes[index] == index) {
            return Optional.of(index);
        } else {
            Optional<Integer> result = find(parentIndexes[index]);
            result.ifPresent(r ->  {
                parentIndexes[index] = r;
            });
            return result;
        }
    }

    public static <Item> Element<Item> createElement(Item item, int index) {
        return Element.<Item>builder()
                .value(item)
                .index(index)
                .size(1)
                .build();
    }
}
