package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet


interface PetService {

    fun findAll(): List<PetDTO>

    fun `get`(id: Int): PetDTO

    fun create(petDTO: PetDTO): Int

    fun update(id: Int, petDTO: PetDTO)

    fun delete(id: Int)

    fun getPetValues(): Map<Int, Int>

}
