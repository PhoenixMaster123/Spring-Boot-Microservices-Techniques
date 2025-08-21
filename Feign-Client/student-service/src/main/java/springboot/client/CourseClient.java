package springboot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springboot.dto.Course;
import springboot.dto.Rating;

import java.util.List;

@FeignClient(name = "course-client", url = "${application.services.course.url}")
public interface CourseClient {
    // @FeignClient -> create a proxy for the CourseClient
    // RestTemplate -> build the request
    // URL , GET , NO, List<COURSE>
    @GetMapping
    List<Course> courses();

    @GetMapping("/{id}")
    Course course(@PathVariable int id);

    @PostMapping("/{id}/ratings")
    String submitRating(@PathVariable int id, @RequestBody Rating rating);

}
