package com.myapp.patientMVC.web;

import com.myapp.patientMVC.entities.Patient;
import com.myapp.patientMVC.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword
                           ){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }



    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> lisPatients(){
        return patientRepository.findAll();
    }

    @GetMapping("/PatientForm")
    public String PatientForm(Model model)
    {
        model.addAttribute("patient", new Patient());
        return "PatientForm";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Patient patient,
                       BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String key
                      )
    {
        if(bindingResult.hasErrors())
            return "PatientForm" ;
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+key;
    }
    @GetMapping("/update")
    public String Update(Model model, Long id, int page, String key)
    {
        Patient p = patientRepository.findById(id).orElse(null);
        if(p == null)
            throw new RuntimeException("Patient introuvable !");
        model.addAttribute("patient", p);
        model.addAttribute("page", page);
        model.addAttribute("key", key);


        return "EditPatient";
    }

}
