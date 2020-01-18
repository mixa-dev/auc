package nl.mixa.auc.util;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectionUtils {

    public static <T> Stream<List<T>> batchList(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }


}
