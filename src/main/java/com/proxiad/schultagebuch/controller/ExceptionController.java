package com.proxiad.schultagebuch.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("statusCode", statusCode);
		mav.addObject("errorMessage", Objects.isNull(exception) ? "N/A" : exception.getMessage());
		return mav;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
