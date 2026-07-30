package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

interface VisitService {

    fun findAll(): List<Visit>

    fun `get`(id: Int): Visit

    fun create(visit: Visit): Int

    fun update(id: Int, visit: Visit)

    fun delete(id: Int)
}
