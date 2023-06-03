package org.geekhub.yurii.model.page;

import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
public class Pageable {

    @NotNull
    private Integer pageSize;
    @NotNull
    private Integer pageNo;
    private Direction direction;

    public Pageable() {
        direction = Direction.DESC;
    }

    public int getLimit() {
        return pageSize;
    }

    public int getOffset() {
        return  (pageNo - 1) * pageSize;
    }

    public String getDirection() {
        return direction.toString();
    }
}
