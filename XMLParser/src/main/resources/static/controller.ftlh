package com.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
<#list list as item>
import com.project.repositories.${item.className}Repository;
import com.project.entities.${item.className};
</#list>

@Controller
public class AppController {

<#list list as item>
	@Autowired
	private ${item.className}Repository ${item.className?lower_case}Repository;
</#list>

<#list list as item>
<#assign name = item.className?lower_case>
	@GetMapping("/${name}List")
	public String get${name?cap_first}List(Model model) {
		model.addAttribute("${name}List", ${name}Repository.findAll());
		return "${name}List";
	}

	// 1.
	@GetMapping("/${name}Form")
	public String ${name?cap_first}Form(Model model) {
		model.addAttribute("${name}", new ${name?cap_first}());
	    return "${name}Form";
	}
	// 2.
	@GetMapping("/${name}Edit/{id}")
	public String ${name}Edit(Model model, @PathVariable long id) {
		${name?cap_first} ${name} = ${name}Repository.findById(id).get();
		model.addAttribute("${name}", ${name});
		return "${name}Form";
	}
	
	// 3.
	@PostMapping("/${name}Post")
	public String ${name}Post(@ModelAttribute("${name}") ${name?cap_first} ${name}) {
		${name}Repository.save(${name});
		return "redirect:/${name}List";
	}
</#list>
}
