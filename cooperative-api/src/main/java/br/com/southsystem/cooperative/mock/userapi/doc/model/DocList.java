package br.com.southsystem.cooperative.mock.userapi.doc.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocList {
    private String type;
    private List<String> docs;
}
