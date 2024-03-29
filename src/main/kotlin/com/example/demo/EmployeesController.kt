package com.example.demo

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/employees")
class EmployeesController (var employeesRepository: EmployeesRepository,var taskRepository: TaskRepository){
@GetMapping
    fun getAllEmployees(): Flux<Employees>{
    return employeesRepository.findAll()
}
    @GetMapping("/{id}")
    fun getById(@PathVariable id:String):Mono<Employees>{
        return employeesRepository.findById(id)
    }
    @GetMapping("/add/task/{employeesId}/{taskId}")
    fun addTaskToEmployees(@PathVariable employeesId:String, @PathVariable taskId:String):Mono<Employees>{
        var task=taskRepository.findById(taskId)
        var employees= employeesRepository.findById(employeesId)
      var e =  employees.zipWith(task).flatMap {
          tuple -> tuple.t1.addTask(tuple.t2)


employeesRepository.save(tuple.t1)
    }
        return e
    }
    @PostMapping
    fun saveEmployees(@RequestBody employees:Employees):Mono<Employees>{
        return employeesRepository.save(employees)
    }
    @DeleteMapping("/{id}")
    fun deletedById(@PathVariable id:String): Mono<String>{
        println(id)
        return Mono.just("delete")
    }
}