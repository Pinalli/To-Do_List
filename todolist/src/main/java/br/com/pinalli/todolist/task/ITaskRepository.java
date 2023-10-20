package br.com.pinalli.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 * @author pinalli
 * @version 1.0
 */

public interface ITaskRepository  extends JpaRepository<TaskModel, UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
}
