package br.com.southsystem.usersapi.doc;

import br.com.southsystem.usersapi.doc.api.GeraCpfCnpj;
import br.com.southsystem.usersapi.doc.api.ValidatorCPFCNPJ;
import br.com.southsystem.usersapi.doc.model.DocList;
import br.com.southsystem.usersapi.doc.model.UserStatus;
import br.com.southsystem.cooperative.controller.doc.model.ValidatorResponse;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class DocApiController {
//
//    @Autowired
//    private UserRepositoryData userRepository;

    @GetMapping("/cpf/validate")
    public ResponseEntity validateCPF(final String cpf) {
        var docNumber = ValidatorCPFCNPJ.clearDoc(cpf);
        var isValid = ValidatorCPFCNPJ.isValidSsn(docNumber);
        if(!isValid) {
            var response = new ValidatorResponse(docNumber, UserStatus.of(isValid));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

//        var user = userRepository.findFirstUserByPersonDoc(docNumber);
//        if(user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if(!user.get().isActive()) {
//            isValid = false;
//        }

        var response = new ValidatorResponse(docNumber, UserStatus.of(isValid));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cnpj/validate")
    public ResponseEntity validateCNPJ(final String cnpj) {
        var docNumber = ValidatorCPFCNPJ.clearDoc(cnpj);
        var isValid = ValidatorCPFCNPJ.isValidTfn(docNumber);
        if(!isValid) {
            return ResponseEntity.notFound().build();
        }

//        var user = userRepository.findFirstUserByPersonDoc(docNumber);
//        if(user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if(!user.get().isActive()) {
//            isValid = false;
//        }
        var response = new ValidatorResponse(docNumber, UserStatus.of(isValid));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/generate/{type}")
    public ResponseEntity generateDocs(@PathVariable String type, int qtd) {
        var gerador = new GeraCpfCnpj();
        var docs = new ArrayList<String>();

        for (int i =0; i < qtd; i++) {
            if(type.equals("cpf")) {
                docs.add(gerador.cpf());
            } else if(type.equals("cnpj")) {
                docs.add(gerador.cnpj());
            }
        }

        return ResponseEntity.ok(new DocList(type, docs));
    }
}
