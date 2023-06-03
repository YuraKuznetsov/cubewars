package org.geekhub.yurii.model.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileFormat {

    WORD(".docx"),
    PDF(".pdf"),
    EXCEL(".xlsx");

    private final String extension;
}
