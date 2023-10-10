package br.com.pinalli.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.Data;

/**
 * @author @pinalli
 * @version 1.0
 */
/**
 * Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields.
 * / @Data = @Getter @Setter 
 * /@EqualsAndHashCode @ToString
 */

 /**
  * JPA annotation to define the entity name.  
  */
@Data  
@Entity(name="tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
