package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.user;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(BeforeDeleteOwner.class)
    public void on(final BeforeDeleteOwner event) {
        final ReferencedException referencedException = new ReferencedException();
        final User ownerUser = userRepository.findFirstByOwnerId(event.getId());
        if (ownerUser != null) {
            referencedException.setKey("owner.user.owner.referenced");
            referencedException.addParam(ownerUser.getUsername());
            throw referencedException;
        }
    }

}
