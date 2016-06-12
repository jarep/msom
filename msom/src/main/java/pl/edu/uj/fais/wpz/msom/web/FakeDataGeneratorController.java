/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.web.helpers.EntityGenerator;

/**
 *
 * @author jaroslaw
 */
@Controller
public class FakeDataGeneratorController {

    @Autowired
    private EntityGenerator entityGenerator;

    /**
     *
     * @param model to use in method
     * @return  path to view fake data generator
     */
    @RequestMapping(value = "/fakedatagenerator", method = RequestMethod.GET)
    public String generateFakeData(Model model) {
        entityGenerator.deleteDomain();
        entityGenerator.generateDomain();
        return "fakedatagenerator";
    }
}
