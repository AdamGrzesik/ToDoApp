package com.njp.controllers

import com.njp.services.TaskService
import com.njp.utils.FunFactFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController {
    @Autowired
    lateinit var taskService: TaskService

    @Autowired
    lateinit var funFactFetcher: FunFactFetcher

    @GetMapping("/")
    fun index(): ModelAndView {
        val modelAndView = ModelAndView("index")
        val funFact = funFactFetcher.fetchRandomFunFact()
        modelAndView.addObject("Tasks", taskService.getAllTasks())
        modelAndView.addObject("FunFact", funFact)
        return modelAndView
    }
}