package com.example.professorreviews.dao;

import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.InstituteCount;
import com.example.professorreviews.model.Lecturer;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

public interface InstituteDAO {

  @Transactional
  int insertInstitute(Long id, Institute institute);

  @Transactional
  default int insertInstitute(Institute institute) {
    Long id = new Random().nextLong();
    return insertInstitute(id, institute);
  }

  @Transactional
  List<Institute> getAllInstitutes();

  @Transactional
  List<InstituteCount> getInstitutesCount();

  @Transactional
  Institute selectInstituteById(Long id);

  @Transactional
  List<Institute> selectInstitutesByCityName(String city);

  @Transactional
  List<Institute> getTop10Institutes();

  @Transactional
  Institute updateInstituteById(Long id, Institute institute);

  @Transactional
  int deleteInstituteByid(Long id);

}
