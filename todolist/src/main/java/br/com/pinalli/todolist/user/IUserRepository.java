package br.com.pinalli.todolist.user;

/**
 * @author pinalli
 * @version 1.0
 */
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, UUID> {

         UserModel findByUsername(String username);
}