package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String listSubjects(Model model){
        model.addAttribute("subjects", subjectRepository.findAll());
        return "subjects_view";
    }

    @GetMapping("/new")
    public String createSubject(Model model) {
        model.addAttribute("subject", new Subject());
        return "create_subject_view";
    }

    @PostMapping
    public String create(Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/edit")
    public String editSubjectForm(@PathVariable Long id, Model model) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        model.addAttribute("subject", subject);
        return "edit_subject_view";
    }

    @PutMapping("/{id}")
    public String updateSubject(@PathVariable Long id,
                                Subject subject) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        existingSubject.setName(subject.getName());
        subjectRepository.save(existingSubject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return "redirect:/subjects";
    }
}
