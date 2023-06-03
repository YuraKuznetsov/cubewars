package org.geekhub.yurii.dto.news;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewsUpdateDTO {

    @NotNull
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
