package spring.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class EmpController {

    private List<Emp> list;

    public EmpController() {


        list = new ArrayList<>();

        list.add(new Emp(1, "Janek", 120000, "Radom"));
        list.add(new Emp(2, "Zosia", 9000, "Makowiec"));
        list.add(new Emp(3, "Marek", 10000, "Warszawa"));
        list.add(new Emp(4, "Krysytna", 13000, "Ryzowice"));
    }



    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    @RequestMapping(value = "/empform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {
        return new ModelAndView("emp/empform", "emp", new Emp());
    }

    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {

        if(emp.getId() == 0){
            emp.setId(Emp.counter++);
            list.add(emp);

        }

        else{
        list.set(emp.getId()-1,emp);

        }

        return new ModelAndView("redirect:/viewemp");



    }



    @RequestMapping("/viewemp")
    public ModelAndView viewemp(Model model) {


        return new ModelAndView("emp/viewemp", "list", list);
    }

   @RequestMapping("/test")
    public ModelAndView test (){

       System.out.println("Witaj");
       return new ModelAndView("redirect:/");
   }

    @RequestMapping("/delete")
    public ModelAndView methodDelete (@RequestParam(value = "emp_id") String id){

        int indexRemove = Integer.parseInt(id);

        Iterator iterator = list.iterator();

        while (iterator.hasNext()){

            Emp emp = (Emp) iterator.next();

            if (emp.getId() == indexRemove){
                iterator.remove();
                break;

            }
        }

        //sent.
        return new ModelAndView("redirect:/viewemp");


    }

    @RequestMapping(value = "/edit")
    public ModelAndView methodEdit (@ModelAttribute(value = "emp_id") String id){

        int indexFind = Integer.parseInt(id);
        Emp emp = list.stream().filter(f-> f.getId () == indexFind).findFirst().get();
        return new ModelAndView("emp/empform","emp",emp);





        // value = "/delete_emp", method = RequestMethod.POST
        //public ModelAndView delete(@RequestParam(value = "emp_id") String emp_id)

      //  return null;
    }


    private Emp getEmployeesById(@RequestParam int id) {
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }




//}
}