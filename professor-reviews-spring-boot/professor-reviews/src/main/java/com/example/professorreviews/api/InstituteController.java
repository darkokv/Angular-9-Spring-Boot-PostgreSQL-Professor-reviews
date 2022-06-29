package com.example.professorreviews.api;

import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.InstituteCount;
import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.service.InstituteService;
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
public class InstituteController {

  private final InstituteService instituteService;

  @Autowired
  public InstituteController(InstituteService instituteService) {
    this.instituteService = instituteService;
  }

  //Get all institutes (GET)
  @GetMapping("/pr/getallinstitutes")
  @ResponseBody
  public List<Institute> getAllInstitutes() {
    return instituteService.getAllInstitutes();
  }

  //Get all institutes count (GET)
  @GetMapping("/pr/getallinstitutescount")
  @ResponseBody
  public List<InstituteCount> getAllInstitutesCount() {
    return instituteService.getAllInstitutesCount();
  }

  //Select institute by id (GET)
  @GetMapping("/pr/institute/{id}")
  @ResponseBody
  public Institute selectInstituteById(@PathVariable("id") Long id) {
    return instituteService.selectInstituteById(id);
  }

  //Select institutes by city name (GET)
  @GetMapping("/pr/institutesbycity/{city}")
  @ResponseBody
  public List<Institute> selectInstitutesByCityName(@PathVariable("city") String city) {
    return instituteService.selectInstitutesByCityName(city);
  }

  //Select top 10 institutes by average rate (GET)
  @GetMapping("/pr/gettop10institutes")
  @ResponseBody
  public List<Institute> getTop10Institutes() {
    return instituteService.getTop10Institutes();
  }

  //Insert institute (POST)
  @PostMapping("/pr/insertinstitute")
  public int insertInstitute(@RequestBody Institute institute) {
    return instituteService.insertInstitute(institute);
  }

  //Update institute by id (PUT)
  @PutMapping("/pr/updateinstitute/{id}")
  public Institute updateInstituteById(@PathVariable Long id, @RequestBody Institute institute)  {
    return instituteService.updateInstituteById(id, institute);
  }

  //Delete institute by id (DELETE)
  @DeleteMapping("/pr/deleteinstitute/{id}")
  public int deleteInstituteById(@PathVariable Long id) {
    return instituteService.deleteInstituteById(id);
  }
}
