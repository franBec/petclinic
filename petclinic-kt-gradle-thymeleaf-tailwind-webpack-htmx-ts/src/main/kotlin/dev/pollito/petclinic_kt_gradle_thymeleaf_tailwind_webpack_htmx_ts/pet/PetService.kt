package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

interface PetService {

    fun findAll(): List<Pet>

    fun `get`(id: Int): Pet

    fun create(pet: Pet): Int

    fun update(id: Int, pet: Pet)

    fun delete(id: Int)

    fun getPetValues(): Map<Int, Int>
}
