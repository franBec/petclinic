package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import org.springframework.data.jpa.repository.JpaRepository


interface TypeRepository : JpaRepository<Type, Int>
