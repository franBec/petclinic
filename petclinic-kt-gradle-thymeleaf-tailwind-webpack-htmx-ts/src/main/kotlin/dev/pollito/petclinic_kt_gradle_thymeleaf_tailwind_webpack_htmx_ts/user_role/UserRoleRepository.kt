package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.user_role

import org.springframework.data.jpa.repository.JpaRepository


interface UserRoleRepository : JpaRepository<UserRole, Int>
