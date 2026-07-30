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
    private val typeMapper: TypeMapper,
) : TypeService {

    override fun findAll(): List<TypeDTO> {
        val types = typeRepository.findAll(Sort.by("id"))
        return types.map { type -> typeMapper.updateTypeDTO(type, TypeDTO()) }
    }

    override fun `get`(id: Int): TypeDTO = typeRepository.findById(id)
        .map { type -> typeMapper.updateTypeDTO(type, TypeDTO()) }
        .orElseThrow { NotFoundException() }

    override fun create(typeDTO: TypeDTO): Int {
        val type = Type()
        typeMapper.updateType(typeDTO, type)
        return typeRepository.save(type).id!!
    }

    override fun update(id: Int, typeDTO: TypeDTO) {
        val type = typeRepository.findById(id)
            .orElseThrow { NotFoundException() }
        typeMapper.updateType(typeDTO, type)
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
