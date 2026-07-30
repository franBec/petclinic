package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.events.BeforeDeleteType
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.CustomCollectors
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class TypeServiceImpl(
    private val typeRepository: TypeRepository,
    private val publisher: ApplicationEventPublisher,
) : TypeService {

    override fun findAll(): List<Type> = typeRepository.findAll(Sort.by("id"))

    override fun `get`(id: Int): Type = typeRepository.findById(id)
        .orElseThrow { NotFoundException() }

    override fun create(type: Type): Int = typeRepository.save(type).id!!

    override fun update(id: Int, type: Type) {
        type.id = id
        typeRepository.findById(id).orElseThrow { NotFoundException() }
        typeRepository.save(type)
    }

    override fun delete(id: Int) {
        val type = typeRepository.findById(id)
            .orElseThrow { NotFoundException() }
        publisher.publishEvent(BeforeDeleteType(id))
        typeRepository.delete(type)
    }

    override fun getTypeValues(): Map<Int, Int> = typeRepository.findAll(Sort.by("id"))
        .stream()
        .collect(CustomCollectors.toSortedMap(Type::id, Type::id))
}
