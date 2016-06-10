/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author debra
 */
@ControllerAdvice
public class ExceptionsController {
    
    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatchException(Exception e, HttpServletRequest request, Model model) {
        // invalid id
        model.addAttribute("errorMsg", "Error: Page not found!");
        return "errorpage";
    }
    
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(Exception e, HttpServletRequest request, Model model) {
        // element with given id doesnt exist
        model.addAttribute("errorMsg", "Error: Page not found!");
        return "errorpage";
    }
    
}
