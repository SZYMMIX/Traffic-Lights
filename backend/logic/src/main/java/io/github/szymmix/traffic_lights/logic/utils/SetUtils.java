package io.github.szymmix.traffic_lights.logic.utils;

import io.github.szymmix.traffic_lights.logic.model.Lane;

import java.util.HashSet;
import java.util.Set;

public final class SetUtils {
    private SetUtils() {}

    public static Set<Lane> symmetricDifference(Set<Lane> a, Set<Lane> b) {
        Set<Lane> diff = new HashSet<>(a);
        diff.addAll(b);

        Set<Lane> intersectionSet = new HashSet<>(a);
        intersectionSet.retainAll(b);

        diff.removeAll(intersectionSet);
        return diff;
    }
}
