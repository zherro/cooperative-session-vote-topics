package br.com.southsystem.cooperative.dto.pageable;

import lombok.Setter;

@Setter
public class RequestFilter {

    private int page;
    private int size;

    public int getPage() {
        return page <= 0 ? 1 : page;
    }

    public int getSize() {
        return size < 5 ? 10 : size;
    }
}
