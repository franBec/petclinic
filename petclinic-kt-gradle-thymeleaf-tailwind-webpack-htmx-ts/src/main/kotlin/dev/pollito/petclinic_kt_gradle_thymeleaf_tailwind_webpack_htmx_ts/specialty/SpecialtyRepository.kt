package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

import org.springframework.data.jpa.repository.JpaRepository


interface SpecialtyRepository : JpaRepository<Specialty, Int>
