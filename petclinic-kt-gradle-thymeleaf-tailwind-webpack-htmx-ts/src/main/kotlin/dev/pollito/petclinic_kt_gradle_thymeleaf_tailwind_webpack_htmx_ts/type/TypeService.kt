package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

interface TypeService {

    fun findAll(): List<Type>

    fun `get`(id: Int): Type

    fun create(type: Type): Int

    fun update(id: Int, type: Type)

    fun delete(id: Int)

    fun getTypeValues(): Map<Int, Int>
}
