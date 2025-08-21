package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.dto.Course;
import springboot.dto.Rating;
import springboot.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/search/{courseId}")
    public Course search(@PathVariable int courseId) {
        return studentService.searchCourse(courseId);
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return studentService.getAllCourses();
    }

    @PostMapping("/courses/{courseId}/ratings")
    public String submitRating(@PathVariable int courseId, Rating rating) {
        return studentService.submitRating(courseId, rating);
    }
}
