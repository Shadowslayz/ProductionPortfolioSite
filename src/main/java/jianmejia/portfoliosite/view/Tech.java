package jianmejia.portfoliosite.view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Tech {
    private Tech() {}
    public static List<String> list(String csv) {
        if (csv == null || csv.isBlank()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
