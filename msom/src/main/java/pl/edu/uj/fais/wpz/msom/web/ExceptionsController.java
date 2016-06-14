/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides CRUD for excepction controller
 * @author debra
 */
@ControllerAdvice
public class ExceptionsController {
    
    /**
     *
     * @param e excepction
     * @param request
     * @param model  to use in method
     * @return path to view error page
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleTypeMismatchException(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("errorMsg", "invalid ID");
        return "errorpage";
    }
    
    /**
     *
     * @param e excepction
     * @param request
     * @param model to use in method
     * @return path to view error page
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNullPointerException(Exception e, HttpServletRequest request, Model model) {
        model.addAttribute("errorMsg", "ID not found");
        return "errorpage";
    }
    
}
