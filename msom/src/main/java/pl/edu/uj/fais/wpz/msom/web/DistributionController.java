/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;

/**
 * Provides CRUD for disrtibution controller
 * @author paweldylag
 */
@Controller
public class DistributionController {
    
    @Autowired
    private DistributionService distributionService;
    
    /**
     *
     * @param model to use in method
     * @return path to view with distributions list
     */
    @RequestMapping(value = "/distributions", method = RequestMethod.GET)
    public String showAllDistributions(Model model) {
        List<Distribution> distributionList = distributionService.findAll();
        model.addAttribute("distributionList", distributionList);

        return "distributions/list";
    }
    
    /**
    * Creates new form for distribution
    * @param model to use in method
    * @return path to view with new distributions
    */
    @RequestMapping(value = "/distributions/new", method = RequestMethod.GET)
    public String createDistribution(Model model) {
        model.addAttribute("distribution", new Distribution());
        model.addAttribute("distributionTypes", DistributionType.values());
        return "distributions/new";
    }
    
    /**
    * Saves new distribution to database
    * @param distribution to add
    * @param redirectAttributes is used for response messages about method action
    * @return redirect to view with distributions
    */
    @RequestMapping(value = "/distributions/new", method = RequestMethod.POST)
    public String addDistribution(@ModelAttribute(value = "distribution") Distribution distribution, RedirectAttributes redirectAttributes) {
        if (distributionService.add(distribution)) {
            redirectAttributes.addFlashAttribute("msg", "The Distribution was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Distribution");
        }
        return "redirect:/distributions";
    }
    
    /**
     * Returns distribution with specified ID
     *
     * @param id distribution ID
     * @param model Model to put distribution to
     * @return path to view with distributions view
     */
    @RequestMapping(value = "/distributions/{id}", method = RequestMethod.GET)
    public String getDistribution(@PathVariable("id") long id, Model model) {
        Distribution distribution = distributionService.find(id);
        if (distribution == null) throw new NullPointerException();
        model.addAttribute("distribution", distribution);
        model.addAttribute("distributionTypes", DistributionType.values());
        return "distributions/view";
    }
    
    /**
     * Deletes distribution with specified ID
     *
     * @param id distribution's ID
     * @param redirectAttributes is used for response messages about method action
     * @return redirect to view with distribution
     */
    @RequestMapping(value = "/distributions/remove/{id}", method = RequestMethod.POST)
    public String deleteDistribution(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Distribution distribution = distributionService.find(id);

        if ((distribution != null) && distributionService.remove(distribution)) {
            redirectAttributes.addFlashAttribute("msg", "The Distribution was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Distribution");
        }

        return "redirect:/distributions";
    }
    
    /**
     *
     * @param distribution to update
     * @param redirectAttributes is used for response messages about method action
     * @return redirect to view with distributions
     */
    @RequestMapping(value = "/distributions/update", method = RequestMethod.POST)
    public String updateDistribution(@ModelAttribute(value = "distribution") Distribution distribution, RedirectAttributes redirectAttributes) {
        if (distributionService.update(distribution)) {
            redirectAttributes.addFlashAttribute("msg", "The Distribution was updated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Distribution");
        }
        return "redirect:/distributions";
    }
}
