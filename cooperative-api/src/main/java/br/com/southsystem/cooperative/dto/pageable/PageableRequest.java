package br.com.southsystem.cooperative.dto.pageable;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableRequest {

    private int page = 1;
    private int size = 10;
    private String sortBy = "";

    private int maxPageSize = 20;


    public PageableRequest(int page, int size, String sortField) throws IllegalArgumentException{
        this.page = page;
        this.size = size;
        this.sortBy = sortField;
        this.validate();
    }


    public PageableRequest(int page, int size, String sortField, int maxPageSize) throws IllegalArgumentException{
        this.page = page;
        this.size = size;
        this.sortBy = sortField;
        this.maxPageSize = maxPageSize;
        this.validate();
    }

    public PageableRequest(int page, int size) throws IllegalArgumentException{
        this.page = page;
        this.size = size;
        this.validate();
    }


    private void validate(){
        if (page <= 0) {
            throw new IllegalArgumentException("O numero da página deve ser maior que 0!");
        }

        if (size < 1 || size > maxPageSize) {
            throw new IllegalArgumentException(String.format("o parâmtro size deve ser maior que 1 e menor que %d !", maxPageSize));
        }

        this.page = --page;
        this.size = size;
    }


    public Pageable build(){
        if(this.sortBy == null || this.sortBy.isEmpty()) {
            return PageRequest.of( page,size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        return PageRequest.of( page,size, Sort.by(Sort.Direction.DESC, this.sortBy));
    }
}
