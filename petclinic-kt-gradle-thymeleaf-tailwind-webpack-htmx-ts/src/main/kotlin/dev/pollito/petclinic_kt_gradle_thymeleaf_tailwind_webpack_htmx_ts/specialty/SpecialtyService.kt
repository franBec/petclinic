package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

interface SpecialtyService {

    fun findAll(): List<Specialty>

    fun `get`(id: Int): Specialty

    fun create(specialty: Specialty): Int

    fun update(id: Int, specialty: Specialty)

    fun delete(id: Int)

    fun getSpecialtyValues(): Map<Int, Int>
}
