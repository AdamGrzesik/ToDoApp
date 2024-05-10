package com.njp.services

import com.njp.models.Task
import com.njp.repositories.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Optional

@Service
class TaskService {
    @Autowired
    lateinit var taskRepository: TaskRepository

    fun getAllTasks(): Iterable<Task> = taskRepository.findAll()

    fun getTaskById(id: Long): Optional<Task> = taskRepository.findById(id)

    fun saveTask(task: Task): Task {
        if (task.id == null) {
            task.createdAt = Instant.now()
        }
        task.updatedAt = Instant.now()
        return taskRepository.save(task)
    }

    fun delete(task: Task) = taskRepository.delete(task)
}