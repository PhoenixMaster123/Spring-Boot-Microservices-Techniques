package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.client.CourseClient;
import springboot.dto.Course;
import springboot.dto.Rating;

import java.util.List;

@Service
public class StudentService {

    private final CourseClient courseClient;

    @Autowired
    public StudentService(CourseClient courseClient) {
        this.courseClient = courseClient;
    }

    public Course searchCourse(int courseId) {
        return courseClient.course(courseId);
    }

    public List<Course> getAllCourses() {
        return courseClient.courses();
    }

    public String submitRating(int courseId, Rating rating) {
        return courseClient.submitRating(courseId, rating);
    }
}
