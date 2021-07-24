package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.AppMessages;
import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.repository.UserRepository;
import br.com.southsystem.cooperative.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_DOCUMENT_IN_USE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USERNAME_IN_USE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USER_DATA_INVALID;

@Service
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
        if(data.getUsername() == null || data.getUsername().length() < 3
                || data.getPassword() == null || data.getPassword().length() < 4
                || data.getPerson() == null
                    || data.getPerson().getName() == null || data.getPerson().getName().length() < 3
                    || data.getPerson().getDoc() == null || data.getPerson().getDoc().length() < 11
        ) {
            createBusinessException(MSG_EXCEPTION_USER_DATA_INVALID);
        }

        userRepository.getByUsername(data.getUsername())
                .ifPresent(u -> createBusinessException(MSG_EXCEPTION_USERNAME_IN_USE));

        userRepository.getByPersonDoc(data.getPerson().getDoc())
                .ifPresent(u -> createBusinessException(MSG_EXCEPTION_DOCUMENT_IN_USE));

        if(data.getPerson() != null) {
            data.getPerson().setActive(true);
        }
        return UserService.super.create(data);
    }

    private void createBusinessException(AppMessages msgExceptionUsernameInUse) {
        var msg = MessageService.getMessage(messageSource, msgExceptionUsernameInUse.getMsgKey());
        throw new BusinessException(msg);
    }
}
