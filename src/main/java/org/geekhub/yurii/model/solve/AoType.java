package org.geekhub.yurii.model.solve;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AoType {

    AO_5(5, 1, 1),
    AO_12(12, 1, 1),
    AO_50(50, 3, 3),
    AO_100(100, 5, 5);

    private final int solvesCount;
    private final int ignoredBetters;
    private final int ignoredWorsts;

    public int getMiddleCount() {
        return solvesCount - ignoredWorsts - ignoredBetters;
    }
}
