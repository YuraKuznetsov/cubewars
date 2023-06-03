package org.geekhub.yurii.service.export;

import org.springframework.core.io.Resource;

import java.util.List;

public abstract class DocumentResourceCreator<E> {

    protected final Class<E> clazz;
    protected List<E> data;

    public DocumentResourceCreator(Class<E> clazz) {
        this.clazz = clazz;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public abstract Resource createResource();
}
