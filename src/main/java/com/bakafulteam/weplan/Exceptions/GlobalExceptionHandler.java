package com.bakafulteam.weplan.Exceptions;

import org.dom4j.rule.Mode;
import org.hibernate.loader.custom.Return;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongFileExtensionException.class)
        public ModelAndView wrongExtensionException(){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("Error","Extension exception");
        return mv;
    }

    @ExceptionHandler(AlreadyAddedCollaboratorException.class)
    public ModelAndView alreadyAddedCollaboratorException(){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("Error","Already added collaborator");
        return mv;
    }

    @ExceptionHandler(AlreadyAddedFriendException.class)
    public ModelAndView alreadyAddedFriendException(){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("Error","Already added friend");
        return mv;
    }
    @ExceptionHandler(NonExistentUserException.class)
    public ModelAndView nonExistentUserException(){
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("Error","Non existent user");
        return mv;
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConflict() {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("Error","Data Integrity exception");
        return mv;
        // Nothing to do
    }



}
