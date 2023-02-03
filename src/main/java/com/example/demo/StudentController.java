package com.example.demo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String listStudents(Model model){
        model.addAttribute("students", studentRepository.findAll());
        return "students_view";
    }

    @GetMapping("/new")
    public String createStudent(Model model) {
        model.addAttribute("student", new Student());
        return "create_student_view";
    }

    @PostMapping
    public String createStudent(Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        model.addAttribute("student", student);
        return "edit_student_view";
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                                Student student) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setIndexNumber(student.getIndexNumber());
        studentRepository.save(existingStudent);
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}/subjects")
    public String listStudentSubjects(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        model.addAttribute("student", student);
        return "student_subjects_view";
    }

    @GetMapping("/{id}/subjects/new")
    public String addStudentSubjectForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        StudentSubjectForm studentSubjectForm = new StudentSubjectForm();
        studentSubjectForm.setStudent(student);
        model.addAttribute("studentSubject", studentSubjectForm);
        return "add_student_subject_view";
    }

    @PostMapping("/{id}/subjects")
    public String addStudentSubject(@PathVariable Long id, StudentSubjectForm studentSubjectForm, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Subject subject = subjectRepository.findByName(studentSubjectForm.getSubjectName())
                .orElseThrow(IllegalArgumentException::new);
        student.addSubject(subject, studentSubjectForm.getGrade());
        studentRepository.save(student);
        model.addAttribute("id", student.getId());
        return "redirect:/students/{id}/subjects";
    }

    @GetMapping("/{id}/subjects/{subjectId}")
    public String deleteStudent(@PathVariable Long id, @PathVariable Long subjectId, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        student.removeSubject(subjectId);
        studentRepository.save(student);
        model.addAttribute("id", student.getId());
        return "redirect:/students/{id}/subjects";
    }

}