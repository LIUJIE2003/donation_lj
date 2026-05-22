package com.donation_wf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BindException.class)
    public RedirectView handleBindException(BindException e, RedirectAttributes redirectAttributes,
                                            HttpServletRequest request) {
        String errors = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getDefaultMessage())
                .collect(Collectors.joining("；"));
        redirectAttributes.addFlashAttribute("errorMsg", errors);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer != null ? referer : "/index");
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception e, RedirectAttributes redirectAttributes) {
        log.error("系统异常", e);
        redirectAttributes.addFlashAttribute("errorMsg", "系统异常，请稍后重试");
        return new RedirectView("/index");
    }
}
