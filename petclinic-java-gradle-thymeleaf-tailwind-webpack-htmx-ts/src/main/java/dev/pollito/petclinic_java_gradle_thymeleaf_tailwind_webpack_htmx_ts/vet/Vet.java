package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Vet {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(nullable = false, columnDefinition = "text")
    private String firstName;

    @Column(nullable = false, columnDefinition = "text")
    private String lastName;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Column
    private OffsetDateTime deletedAt;

    @ManyToMany
    @JoinTable(
            name = "VetSpecialty",
            joinColumns = @JoinColumn(name = "vetId"),
            inverseJoinColumns = @JoinColumn(name = "specialtyId")
    )
    private Set<Specialty> vetSpecialtySpecialties = new HashSet<>();

}
