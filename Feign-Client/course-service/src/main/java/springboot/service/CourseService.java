package springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import springboot.dto.Course;
import springboot.dto.Rating;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseService {

    private List<Course> COURSES = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = new ClassPathResource("courses.json").getInputStream()) {
            Course[] courses = mapper.readValue(is, Course[].class);
            COURSES = new ArrayList<>(Arrays.asList(courses));
        } catch (IOException e) {
            log.error("Error reading courses.json", e);
            throw e;
        }
    }

    public Optional<Course> getCourseById(int id) {
        return COURSES.stream()
                .filter(course -> course.getId() == id)
                .findFirst();
    }

    public List<Course> getAllCourses() {
        return COURSES;
    }

    public boolean addRating(int courseId, Rating rating) {
        Course course = getCourseById(courseId)
                .orElse(null);
        if (course != null) {
            course.getRatings().add(rating);
            return true;
        }
        return false;
    }
}
