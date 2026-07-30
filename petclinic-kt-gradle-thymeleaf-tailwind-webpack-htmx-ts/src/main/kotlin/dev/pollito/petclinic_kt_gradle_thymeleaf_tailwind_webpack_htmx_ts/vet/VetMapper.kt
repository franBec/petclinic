package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyRepository
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.NotFoundException
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy
import org.springframework.beans.factory.annotation.Autowired

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
abstract class VetMapper {

    @Autowired
    protected lateinit var specialtyRepository: SpecialtyRepository

    @Mapping(
        target = "vetSpecialtySpecialties",
        expression = "java(vet.getVetSpecialtySpecialties() == null ? java.util.List.of() : vet.getVetSpecialtySpecialties().stream().map(specialty -> specialty.getId()).toList())",
    )
    abstract fun map(vet: Vet): VetDTO

    @Mapping(
        target = "id",
        ignore = true,
    )
    @Mapping(
        target = "vetSpecialtySpecialties",
        ignore = true,
    )
    abstract fun map(vetDTO: VetDTO): Vet

    @AfterMapping
    protected fun afterMap(vetDTO: VetDTO, @MappingTarget vet: Vet) {
        val vetSpecialtySpecialties = specialtyRepository.findAllById(
            vetDTO.vetSpecialtySpecialties
                ?: emptyList(),
        )
        if (vetSpecialtySpecialties.size != (
                if (vetDTO.vetSpecialtySpecialties == null) {
                    0
                } else {
                    vetDTO.vetSpecialtySpecialties!!.size
                }
                )
        ) {
            throw NotFoundException("one of vetSpecialtySpecialties not found")
        }
        vet.vetSpecialtySpecialties = vetSpecialtySpecialties.toMutableSet()
    }
}
