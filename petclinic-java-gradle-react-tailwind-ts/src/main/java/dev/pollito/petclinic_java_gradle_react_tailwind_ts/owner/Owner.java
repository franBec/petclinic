package dev.pollito.petclinic_java_gradle_react_tailwind_ts.owner;

import dev.pollito.petclinic_java_gradle_react_tailwind_ts.pet.Pet;
import dev.pollito.petclinic_java_gradle_react_tailwind_ts.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Owner {

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

    @Column(nullable = false, columnDefinition = "text")
    private String address;

    @Column(nullable = false, columnDefinition = "text")
    private String city;

    @Column(nullable = false, columnDefinition = "text")
    private String telephone;

    @Column(nullable = false, columnDefinition = "text")
    private String email;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Column
    private OffsetDateTime deletedAt;

    @OneToMany(mappedBy = "owner")
    private Set<Pet> ownerPets = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<User> ownerUsers = new HashSet<>();

}
