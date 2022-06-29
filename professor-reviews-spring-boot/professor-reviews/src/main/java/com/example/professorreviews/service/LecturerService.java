package com.example.professorreviews.service;

import com.example.professorreviews.dao.LecturerDAO;
import com.example.professorreviews.model.Lecturer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {

  private final LecturerDAO lecturerDAO;

  @Autowired
  public LecturerService(@Qualifier("LecturerDAO") LecturerDAO lecturerDAO) {
    this.lecturerDAO = lecturerDAO;
  }

  public int insertLecturer(Lecturer lecturer) {
    return lecturerDAO.insertLecturer(lecturer);
  }

  public List<Lecturer> getAllLecturers() {
    return lecturerDAO.getAllLecturers();
  }

  public Lecturer selectLecturerById(Long id) {
    return lecturerDAO.selectLecturerById(id);
  }

  public Lecturer selectLecturerWithReviewsById(Long id) {
    return lecturerDAO.selectLecturerWithReviewsById(id);
  }

  public List<Lecturer> searchLecturer(String keyword) {
    return lecturerDAO.searchLecturer(keyword);
  }

  public List<Lecturer> getTop10Lecturers() {
    return lecturerDAO.getTop10Lecturers();
  }

  public Lecturer updateLecturerById(Long id, Lecturer lecturer) {
    return lecturerDAO.updateLecturerById(id, lecturer);
  }

  public int deleteLecturerById(Long id) {
    return lecturerDAO.deleteLecturerById(id);
  }
}
