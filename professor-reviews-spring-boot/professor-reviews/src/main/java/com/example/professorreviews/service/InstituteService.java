package com.example.professorreviews.service;

import com.example.professorreviews.dao.InstituteDAO;
import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.InstituteCount;
import com.example.professorreviews.model.Lecturer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InstituteService {

  private final InstituteDAO instituteDAO;

  @Autowired
  public InstituteService(@Qualifier("InstituteDAO") InstituteDAO instituteDAO) {
    this.instituteDAO = instituteDAO;
  }

  public int insertInstitute(Institute institute) {
    return instituteDAO.insertInstitute(institute);
  }

  public List<Institute> getAllInstitutes() {
    return instituteDAO.getAllInstitutes();
  }

  public List<InstituteCount> getAllInstitutesCount() {
    return instituteDAO.getInstitutesCount();
  }

  public Institute selectInstituteById(Long id) {
    return instituteDAO.selectInstituteById(id);
  }

  public List<Institute> selectInstitutesByCityName(String city) {
    return instituteDAO.selectInstitutesByCityName(city);
  }

  public List<Institute> getTop10Institutes() {
    return instituteDAO.getTop10Institutes();
  }

  public Institute updateInstituteById(Long id, Institute institute) {
    return instituteDAO.updateInstituteById(id, institute);
  }

  public int deleteInstituteById(Long id) {
    return instituteDAO.deleteInstituteByid(id);
  }
}
