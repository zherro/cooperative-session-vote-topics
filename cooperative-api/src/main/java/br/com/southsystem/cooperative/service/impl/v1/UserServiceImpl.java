package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.repository.UserRepository;
import br.com.southsystem.cooperative.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_DOCUMENT_IN_USE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USERNAME_IN_USE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USER_DATA_INVALID;

@Service
@Qualifier("userServiceV1")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserServiceImpl(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @Override
    public SpecRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    public RuntimeException notFoundException(String key) {
        return new ResourceNotFoundException(messageSource, User.class.getSimpleName() , key);
    }

    @Override
    public User create(User data) {
        validateUser(data);
        validateUserConsistence(true, data);

        if(data.getPerson() != null) {
            data.getPerson().setActive(true);
            data.getPerson().setUser(data);
        }
        return UserService.super.create(data);
    }

    /**
     * Valida se o username e o documento nao esta em uso por outro usuário
     *
     * @param isNew -> e uma validacao para um novo registro
     * @param #{{@link br.com.southsystem.cooperative.model.User}
     */
    private void validateUserConsistence(final boolean isNew, final User data) {
        userRepository.getByUsername(data.getUsername())
                .ifPresent(u -> {
                    if(isNew || !u.getId().equals(data.getId()))
                        MessageService.createBusinessException(messageSource, MSG_EXCEPTION_USERNAME_IN_USE);
                });

        userRepository.getByPersonDoc(data.getPerson().getDoc())
                .ifPresent(u -> {
                    if(isNew || !u.getId().equals(data.getId()))
                        MessageService.createBusinessException(messageSource, MSG_EXCEPTION_DOCUMENT_IN_USE);
                });
    }

    @Override
    public User update(User data) {
        validateUser(data);
        validateUserConsistence(false, data);
        return UserService.super.update(data);
    }

    /**
     * Realiza a validacao de informacos obrigatorias do usuario:
     *  - username: deve possuir no minimo 3 caracteres
     *  - password: deve possuir no minimo 4 caracteres
     *  - nome: deve possuir no minimo 3 caracteres
     *  - documento: deve possuir no minimo 11 caracteres
     * @param #{{@link br.com.southsystem.cooperative.model.User}
     */
    protected void validateUser(User data) {
        if(data.getUsername() == null || data.getUsername().length() < 3
                || data.getPassword() == null || data.getPassword().length() < 4
                || data.getPerson() == null
                    || data.getPerson().getName() == null || data.getPerson().getName().length() < 3
                    || data.getPerson().getDoc() == null || data.getPerson().getDoc().length() < 11
        ) {
            MessageService.createBusinessException(messageSource, MSG_EXCEPTION_USER_DATA_INVALID);
        }
    }
}
