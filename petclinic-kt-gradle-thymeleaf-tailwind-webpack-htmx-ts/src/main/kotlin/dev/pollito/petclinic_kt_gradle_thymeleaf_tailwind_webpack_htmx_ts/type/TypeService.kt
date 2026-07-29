package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type


interface TypeService {

    fun findAll(): List<TypeDTO>

    fun `get`(id: Int): TypeDTO

    fun create(typeDTO: TypeDTO): Int

    fun update(id: Int, typeDTO: TypeDTO)

    fun delete(id: Int)

    fun getTypeValues(): Map<Int, Int>

}
