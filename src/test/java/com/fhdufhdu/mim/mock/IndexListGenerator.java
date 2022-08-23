package com.fhdufhdu.mim.mock;

import java.util.*;

public class IndexListGenerator {
    public static List<Integer> generate(boolean allowDuplicate) {
        Random random = new Random();
        Collection<Integer> indexList;

        if(allowDuplicate)
            indexList = new ArrayList<>();
        else
            indexList = new HashSet<>();

        while (indexList.size() != 10)
            indexList.add(random.nextInt(10));

        return new ArrayList<>(indexList);
    }
}