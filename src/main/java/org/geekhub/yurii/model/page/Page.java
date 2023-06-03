package org.geekhub.yurii.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<E> {

    private final List<E> elements;
    private final Boolean hasNext;
}
