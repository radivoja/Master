package com.example;
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
</#list>

@Controller
public class AppController {

<#list list as item>
	@Autowired
	private ${item.className}Repository ${item.className}Repository;
</#list>

<#list list as item>
	@GetMapping("/${item.className}")
	public String get${item.className}List(Model model) {
		model.addAttribute("${item.className}List", ${item.className}Repository.findAll());
		return "${item.className?lower_case}Front";
	}
</#list>
	
}
