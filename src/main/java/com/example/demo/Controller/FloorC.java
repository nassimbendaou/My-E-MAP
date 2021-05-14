package com.example.demo.Controller;

import com.example.demo.Modele.Floor;
import com.example.demo.DAO.FloorS;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Floors")
public class FloorC {
    private FloorS F_Service=new FloorS();
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Floor> getFloor() {
        List F_list = F_Service.getAll();
        //model.addAttribute("Floors", F_list);
        return F_list;
    }
    // Opening the add new est form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addFloor(Model model) {
        model.addAttribute("FloorAttr", new Floor());
        return "form";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("FloorAttr") Floor F,@RequestParam(value="id") String id) {
        F_Service.add(F,Integer.parseInt(id));
        return "redirect:list";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(@RequestBody Floor F) {
        F_Service.edit(F);
        return "redirect:list";
    }
    // Opening the edit est form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editFloor(@RequestParam(value="id") String id, Model model){
        model.addAttribute("ClassroomAttr", F_Service.findFloor(id));
        return "form";
    }
    // Deleting the specified Est.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") String id) {
        F_Service.delete(id);
        return "redirect:list";
    }

}

