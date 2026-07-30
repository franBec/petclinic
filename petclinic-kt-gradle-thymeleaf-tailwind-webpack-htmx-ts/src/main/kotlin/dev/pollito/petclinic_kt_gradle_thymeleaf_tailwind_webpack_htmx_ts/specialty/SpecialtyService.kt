package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

interface SpecialtyService {

    fun findAll(): List<SpecialtyDTO>

    fun `get`(id: Int): SpecialtyDTO

    fun create(specialtyDTO: SpecialtyDTO): Int

    fun update(id: Int, specialtyDTO: SpecialtyDTO)

    fun delete(id: Int)

    fun getSpecialtyValues(): Map<Int, Int>
}
