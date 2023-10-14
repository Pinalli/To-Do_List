package br.com.pinalli.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pinalli.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The start date must be greater than the date actual");
        }
        // if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //             .body("The start date must be minor than the end date");
        // }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
    //http://localhost:8080/tasks/984847474-chdjdjdhs-5339393jdjdjd
    @PutMapping("/{id}")
    public void update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
       
        var task = this.taskRepository.findById(id).orElseThrow(null);

        Utils.copyNonNullProperties(taskModel, task);
    
        this.taskRepository.save(task);
    }
}
