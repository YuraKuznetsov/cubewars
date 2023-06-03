package org.geekhub.yurii.dto.news.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentUpdateDTO {

    @NotNull
    private Integer id;
    @NotBlank
    private String content;
}
