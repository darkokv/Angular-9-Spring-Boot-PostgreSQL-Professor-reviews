package com.example.professorreviews.dao;

import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.model.User;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

public interface LecturerDAO {

  @Transactional
  int insertLecturer(Long id, Lecturer lecturer);

  @Transactional
  default int insertLecturer(Lecturer lecturer) {
    Long id = new Random().nextLong();
    return insertLecturer(id, lecturer);
  }

  @Transactional
  List<Lecturer> getAllLecturers();

  @Transactional
  Lecturer selectLecturerById(Long id);

  @Transactional
  Lecturer selectLecturerWithReviewsById(Long id);

  @Transactional
  List<Lecturer> searchLecturer(String keyword);

  @Transactional
  List<Lecturer> getTop10Lecturers();

  @Transactional
  Lecturer updateLecturerById(Long id, Lecturer user);

  @Transactional
  int deleteLecturerById(Long id);

}
