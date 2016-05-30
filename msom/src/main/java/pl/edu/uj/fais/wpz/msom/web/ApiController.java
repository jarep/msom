/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jarep
 */
@Controller
public class ApiController {

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public @ResponseBody Object showAllControllerUnits(Model model) {
        return null;
    }

}
