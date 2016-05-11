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
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;

/**
 *
 * @author paweldylag
 */
@Controller
public class DistributionController {
    
    @Autowired
    private DistributionService distributionService;
    
    @RequestMapping(value = "/distribution", method = RequestMethod.GET)
    public String showAllDistributions(Model model) {
        List<Distribution> distributionList = distributionService.findAll();
        model.addAttribute("distributionList", distributionList);

        return "distribution/list";
    }
    
    /**
    * Creates new form for distribution
    * @param model
    * @return distribution/new
    */
    @RequestMapping(value = "/distribution/new", method = RequestMethod.GET)
    public String createDistribution(Model model) {
        model.addAttribute("distribution", new Distribution());
        return "distribution/new";
    }
    
    /**
    * Saves new distribution to database
    * @param distribution
    * @return redirects to list
    */
    @RequestMapping(value = "/distribution/new", method = RequestMethod.POST)
    public String addDistribution(@ModelAttribute(value = "distribution") Distribution distribution) {
        distributionService.add(distribution);
        return "redirect:/distribution";
    }
    
    /**
     * Returns distribution with specified ID
     *
     * @param id distribution ID
     * @param model Model to put distribution to
     * @return distribution/view
     */
    @RequestMapping(value = "/distribution/{id}", method = RequestMethod.GET)
    public String getDistribution(@PathVariable("id") long id, Model model) {
        Distribution distribution = distributionService.find(id);
        model.addAttribute("distribution", distribution);

        return "distribution/view";
    }
    
    /**
     * Deletes distribution with specified ID
     *
     * @param id distribution's ID
     * @return redirects to distribution if everything ok
     */
    @RequestMapping(value = "/distribution/remove/{id}", method = RequestMethod.POST)
    public String deleteDistribution(@PathVariable("id") long id) {

        Distribution distribution = distributionService.find(id);
        boolean wasDeleted = distributionService.remove(distribution);

        if (!wasDeleted) {
           // exception to handle
        }

        // everything OK, see remaining distributions
        return "redirect:/distribution";
    }
    
    
}
