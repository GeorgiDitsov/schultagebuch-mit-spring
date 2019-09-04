package com.proxiad.schultagebuch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proxiad.schultagebuch.entity.Elternteil;
import com.proxiad.schultagebuch.service.ElternteilService;

@Controller
public class ElternteilController {

	@Autowired
	private ElternteilService elternteilService;

	@RequestMapping(value = "/elternteil")
	public ModelAndView home() {
		List<Elternteil> listElternteil = elternteilService.findAll();
		return new ModelAndView("elternteilForm", "listElternteil", listElternteil);
	}
}
