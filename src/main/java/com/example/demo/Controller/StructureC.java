package com.example.demo.Controller;

import com.example.demo.Modele.Establishment;
import com.example.demo.Modele.Structure;
import com.example.demo.DAO.StructureS;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("Structure")
public class StructureC {

    private StructureS S_Service;

    public StructureC(StructureS s_service) {
        S_Service = s_service;
    }

    @GetMapping()
    public List<Structure> getS(){
        System.out.println("p1");
        //  model.addAttribute("Establishments", E_list);
        return S_Service.getAll_S();
    }

    // Opening the add new est form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEstablishment(Model model) {
        model.addAttribute("EstablishedAttr", new Establishment());
        return "form";
    }
    @PostMapping("save")
    public Structure save(@RequestBody Structure S,@RequestParam(value="id", required=true) int id) {
        System.out.println("contreller phase");
        S_Service.add(S,id);

        return S;
    }
    //@ModelAttribute("EstablishedAttr")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit( @RequestBody Structure S) {
        S_Service.edit(S);
        return "redirect:list";
    }

    // Opening the edit est form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Structure editEstablishment(@RequestParam(value="id", required=true) String id, Model model)  {
        //string type return
        //model.addAttribute("EstablishedAttr", E_Service.findEstablishment(Integer.parseInt(id)));
        //return "form"
        return S_Service.findStructure((Integer.parseInt(id)));
    }
    // Deleting the specified Est.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id) {
        S_Service.delete(Integer.parseInt(id));
        return "done";
        //redirect:list
    }
}
