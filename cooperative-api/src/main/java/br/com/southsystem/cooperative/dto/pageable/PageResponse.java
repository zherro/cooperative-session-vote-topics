package br.com.southsystem.cooperative.dto.pageable;


import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = -8859900022644419310L;

    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private int totalSize;
    private long totalItems;
    private Map<String, Object> filters;

    public PageResponse(Page page){
        buildResponse(page, page.getContent());
    }

    public PageResponse(Page page, List<T> c){
        buildResponse(page, c);
    }

    private void buildResponse(Page page, List<T> c) {
        setContent(c);
        setPage(page.getNumber() + 1);
        setSize(page.getSize());
        setTotalPages(page.getTotalPages());
        setTotalItems(page.getTotalElements());
        setTotalSize(page.getNumberOfElements());
    }
}
