package com.example.demo.Controller;
import com.example.demo.Modele.Block;
import com.example.demo.DAO.BlockS;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/Blocks")
public class BlockC {
    private BlockS B_Service=new BlockS();

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List getBlock() {
        //model.addAttribute("Blocks", B_list);
        return  B_Service.getAll();
    }
    // Opening the add new est form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBlock(Model model) {
        model.addAttribute("BlockAttr", new Block());
        return "form";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody Block B,@RequestParam(value="id") String id) {
        B_Service.add(B,Integer.parseInt(id));
        return "redirect:list";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(@RequestBody Block B) {
        B_Service.edit(B);
        return "redirect:list";
    }
    // Opening the edit est form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editBlock(@RequestParam(value="id") String id, Model model){
        model.addAttribute("BlockAttr", B_Service.findBlock(id));
        return "form";
    }
    // Deleting the specified Est.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id") String id) {
        B_Service.delete(id);
        return "redirect:list";
    }
    @PostMapping
    public void addBlock(HttpServletRequest req){
        String name=req.getParameter("name");
    }
}
