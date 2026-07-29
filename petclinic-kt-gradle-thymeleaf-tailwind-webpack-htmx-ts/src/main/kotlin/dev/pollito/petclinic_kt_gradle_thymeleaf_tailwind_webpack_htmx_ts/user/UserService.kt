package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteOwner
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository
) {

    @EventListener(BeforeDeleteOwner::class)
    fun on(event: BeforeDeleteOwner) {
        val referencedException = ReferencedException()
        val ownerUser = userRepository.findFirstByOwnerId(event.id)
        if (ownerUser != null) {
            referencedException.key = "owner.user.owner.referenced"
            referencedException.addParam(ownerUser.username)
            throw referencedException
        }
    }

}
