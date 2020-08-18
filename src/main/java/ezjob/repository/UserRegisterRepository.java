package ezjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ezjob.model.User;

@Repository
public interface UserRegisterRepository extends JpaRepository<User, Long>  {

	
	

}
