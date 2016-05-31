/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.uj.fais.wpz.msom.web.api.PathReport;
import pl.edu.uj.fais.wpz.msom.web.api.ProcessingUnitReport;
import pl.edu.uj.fais.wpz.msom.web.api.SimulationReport;
import pl.edu.uj.fais.wpz.msom.web.api.TaskDispatcherReport;
import pl.edu.uj.fais.wpz.msom.web.api.TaskReport;
import pl.edu.uj.fais.wpz.msom.web.api.TypeReport;

/**
 *
 * @author jarep
 */
@Controller
public class ApiController {

    @RequestMapping(value = "/api/simulation/{simulationId}", method = RequestMethod.GET)
    public @ResponseBody
    SimulationReport getSimulationReport(@PathVariable("simulationId") long simulationId, Model model) {

        return null;
    }

    @RequestMapping(value = "/api/simulation/{simulationId}/task/{id}", method = RequestMethod.GET)
    public @ResponseBody
    TaskReport getTaskReport(@PathVariable("simulationId") long simulationId, @PathVariable("id") long id, Model model) {
        
        return null;
    }

    @RequestMapping(value = "/api/simulation/{simulationId}/type/{id}", method = RequestMethod.GET)
    public @ResponseBody
    TypeReport getTypeReport(@PathVariable("simulationId") long simulationId, @PathVariable("id") long id, Model model) {

        return null;
    }

    @RequestMapping(value = "/api/simulation/{simulationId}/path/{id}", method = RequestMethod.GET)
    public @ResponseBody
    PathReport getPathReport(@PathVariable("simulationId") long simulationId, @PathVariable("id") long id, Model model) {

        return null;
    }

    @RequestMapping(value = "/api/simulation/{simulationId}/taskdispatcher/{id}", method = RequestMethod.GET)
    public @ResponseBody
    TaskDispatcherReport getTaskDispatcherReport(@PathVariable("simulationId") long simulationId, @PathVariable("id") long id, Model model) {

        return null;
    }

    @RequestMapping(value = "/api/simulation/{simulationId}/processingunit/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ProcessingUnitReport getProcessingUnitReport(@PathVariable("simulationId") long simulationId, @PathVariable("id") long id, Model model) {

        return null;
    }

}
