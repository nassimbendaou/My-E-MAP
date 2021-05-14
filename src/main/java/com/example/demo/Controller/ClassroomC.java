package com.example.demo.Controller;

import com.example.demo.Modele.Classroom;
import com.example.demo.DAO.ClassroomS;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("Classrooms")

public class ClassroomC {
    private ClassroomS C_Service=new ClassroomS();
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Classroom> getBlock() {
        List<Classroom> C_list = C_Service.getAll();
        //model.addAttribute("Classrooms", C_list);
        return C_list;
    }
    @GetMapping("/listC")
    public  List<Classroom> getClassrooms( @RequestParam(value="id") int id) {
        System.out.println("called");
        System.out.println(id);

        List<Classroom> C_list = (List<Classroom>) C_Service.getAll_C(id);
        for(Classroom c : C_list){
            System.out.println(c.getName());
        }

        //model.addAttribute("Classrooms", C_list);


        return C_list;
    }
    @GetMapping("/size")
    public  int size_Classes( @RequestParam(value="q") int id) {
        return C_Service.getNumberOfClassesByBlock(id);
    }
    // Opening the add new est form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBlock(Model model) {
        model.addAttribute("ClassroomAttr", new Classroom());
        return "form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(@RequestBody Classroom C) {
        System.out.println("1");
        C_Service.edit(C);
        System.out.println("2");
        return "redirect:list";
    }
    // Opening the edit est form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editBlock(@RequestParam(value="id") String id, Model model){
        model.addAttribute("ClassroomAttr", C_Service.findClassroom(id));
        return "form";
    }
    // Deleting the specified Est.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id") String id) {
        C_Service.delete(id);
        return "redirect:list";
    }

}
