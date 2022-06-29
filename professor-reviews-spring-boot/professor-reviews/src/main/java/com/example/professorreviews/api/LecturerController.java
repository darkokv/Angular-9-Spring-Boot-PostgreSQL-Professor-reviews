package com.example.professorreviews.api;

import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.service.LecturerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.8.100:4200"})
public class LecturerController {

  private final LecturerService lecturerService;

  @Autowired
  public LecturerController(LecturerService lecturerService) {
    this.lecturerService = lecturerService;
  }

  //Get all lecturers (GET)
  @GetMapping("/pr/getalllecturers")
  @ResponseBody
  public List<Lecturer> getAllLecturers() {
    return lecturerService.getAllLecturers();
  }

  //Select lecturer by id (GET)
  @GetMapping("/pr/lecturer/{id}")
  @ResponseBody
  public Lecturer selectLecturerById(@PathVariable("id") Long id) {
    return lecturerService.selectLecturerById(id);
  }

  //Select lecturer with reviews by id (GET)
  @GetMapping("/pr/lecturerwithreviews/{id}")
  @ResponseBody
  public Lecturer selectLecturerWithReviewsById(@PathVariable("id") Long id) {
    return lecturerService.selectLecturerWithReviewsById(id);
  }

  //Search lecturer by keyword (GET)
  @GetMapping("/pr/searchlecturer/{keyword}")
  @ResponseBody
  public List<Lecturer> searchLecturer(@PathVariable("keyword") String keyword) {
    return lecturerService.searchLecturer(keyword);
  }

  //Search top 10 lecturers by average rate (GET)
  @GetMapping("/pr/gettop10lecturers")
  @ResponseBody
  public List<Lecturer> getTop10Lecturers() {
    return lecturerService.getTop10Lecturers();
  }

  //Insert lecturer (POST)
  @PostMapping("/pr/insertlecturer")
  public int insertLecturer(@RequestBody Lecturer lecturer) {
    return lecturerService.insertLecturer(lecturer);
  }

  //Update lecturer by id (PUT)
  @PutMapping("/pr/updatelecturer/{id}")
  public Lecturer updateLecturerByid(@PathVariable Long id, @RequestBody Lecturer lecturer)  {
    return lecturerService.updateLecturerById(id, lecturer);
  }

  //Delete lecturer by id (DELETE)
  @DeleteMapping("/pr/deletelecturer/{id}")
  public int deleteLecturerById(@PathVariable Long id) {
    return lecturerService.deleteLecturerById(id);
  }
}
