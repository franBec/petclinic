package dev.pollito.petclinic_java_gradle_react_tailwind_ts.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    User findByUsernameIgnoreCase(String username);

    User findFirstByOwnerId(Integer id);

    boolean existsByUsernameIgnoreCase(String username);

}
