package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ezjob.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
