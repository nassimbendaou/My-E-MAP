package com.example.demo.Controller;

import com.example.demo.DAO.ClassroomS;
import com.example.demo.Metier.EstablishmentM;
import com.example.demo.Modele.Establishment;
import com.example.demo.DAO.EstablishmentS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("Establishments")
public class EstablishmentC {
    private EstablishmentM E_Service=new EstablishmentM();
    private ClassroomS C_Service=new ClassroomS();

    //public EstablishmentC(EstablishmentS e_Service) {
    //    E_Service = e_Service;
    //}

    @GetMapping("all")
    public List<Establishment> getEstablishment() throws ParseException {

        // request.setAttribute("test",E_Service.getAll());
        System.out.println("ok");
        return E_Service.getAll();
    }

    // Opening the add new est form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEstablishment(Model model) {
        model.addAttribute("EstablishedAttr", new Establishment());
        return "form";
    }
    @PostMapping("save")
    public Establishment save(@RequestBody Establishment E) {
        System.out.println("contreller phase");
        E_Service.addEstablishment(E);

        return E;
    }
    //@ModelAttribute("EstablishedAttr")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit( @RequestParam(value="id") int id,@RequestParam(value = "name") String name,
                            @RequestParam(value = "Date") String date,@RequestParam(value = "latitude") Float lat,
                            @RequestParam(value = "longitude") Float lon) {
        E_Service.updateEstablishement(E_Service.findEstablishment(id),name,date,lat,lon);
        //E_Service.updateEstablishement(E.getId());
        return "redirect:list";
    }

    // Opening the edit est form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public Establishment editEstablishment(@RequestParam(value="id", required=true) String id, Model model)  {
        return E_Service.findEstablishment(Integer.parseInt(id));
    }
    // Deleting the specified Est.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id) {
        E_Service.deleteEstablishement(E_Service.findEstablishment(Integer.parseInt(id)));
        return "done";
        //redirect:list
    }
//, Model model
}


