package com.njp.controllers

import org.springframework.ui.Model
import com.njp.models.Task
import com.njp.services.TaskService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping


@Controller
class TaskFormController {
    @Autowired
    lateinit var taskService: TaskService

    @GetMapping("/add/task")
    fun addTaskForm(task: Task): String {
        return "new-task"
    }

    @PostMapping("/add/task")
    fun createTodoItem(task: @Valid Task, result: BindingResult, model: Model): String {
        val item: Task = Task(
            description = task.description,
            isComplete = task.isComplete
        )
        if (task.description?.isBlank() == true) {
            return "new-task"
        }
        taskService.saveTask(task)
        return "redirect:/"
    }

    @GetMapping("/delete/task/{id}")
    fun deleteTask(@PathVariable("id") id: Long, model: Model): String {
        val task = taskService
            .getTaskById(id)
            .orElseThrow { IllegalArgumentException("Invalid task id: $id") }
            .let { taskService.delete(it) }
        return "redirect:/"
    }

    @GetMapping("/edit/task/{id}")
    fun editTask(@PathVariable("id") id: Long, model: Model): String {
        val task = taskService
            .getTaskById(id)
            .orElseThrow { IllegalArgumentException("Invalid task id: $id") }
            .let { model.addAttribute("task", it) }
        return "edit-task"
    }

    @PostMapping("/edit/task/{id}/complete")
    fun updateTask(@PathVariable("id") id: Long, task: @Valid Task, result: BindingResult, model: Model): String {
        val item = taskService
            .getTaskById(id)
            .orElseThrow { IllegalArgumentException("Invalid task id: $id") }
            .let {
                it.description = task.description
                it.isComplete = task.isComplete
                taskService.saveTask(it)
            }
        return "redirect:/"
    }
}