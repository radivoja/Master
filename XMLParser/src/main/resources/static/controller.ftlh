package com.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
<#list list as item>
import com.project.repositories.${item.name}Repository;
import com.project.entities.${item.name};
</#list>

@Controller
public class AppController {

<#list list as item>
	@Autowired
	private ${item.name}Repository ${item.name?lower_case}Repository;
</#list>

<#list list as item>
<#assign name = item.name?lower_case>


	// 1. Get
	@GetMapping("/${name}List")
	public String get${name?cap_first}List(Model model) {
		model.addAttribute("${name}List", ${name}Repository.findAll());
		return "${name}List";
	}

	// 2. Form
	@GetMapping("/${name}Form")
	public String ${name?cap_first}Form(Model model) {
		model.addAttribute("${name}", new ${name?cap_first}());
	    return "${name}Form";
	}
	// 3. Edit
	@GetMapping("/${name}Edit/{id}")
	public String ${name}Edit(Model model, @PathVariable Integer id) {
		${item.name} ${name} = ${name}Repository.findById(id).get();
		model.addAttribute("${name}", ${name});
		return "${name}Form";
	}
	
	// 4. Post
	@PostMapping("/${name}Post")
	public String ${name}Post(@ModelAttribute("${name}") ${name?cap_first} ${name}) {
		${name}Repository.save(${name});
		return "redirect:/${name}List";
	}
</#list>
}
