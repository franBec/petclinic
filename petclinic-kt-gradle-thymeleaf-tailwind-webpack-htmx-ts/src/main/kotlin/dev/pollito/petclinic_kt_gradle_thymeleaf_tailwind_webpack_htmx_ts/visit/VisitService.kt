package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

interface VisitService {

    fun findAll(): List<VisitDTO>

    fun `get`(id: Int): VisitDTO

    fun create(visitDTO: VisitDTO): Int

    fun update(id: Int, visitDTO: VisitDTO)

    fun delete(id: Int)
}
