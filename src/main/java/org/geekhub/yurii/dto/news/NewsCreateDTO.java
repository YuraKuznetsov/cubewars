package org.geekhub.yurii.dto.news;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewsCreateDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
