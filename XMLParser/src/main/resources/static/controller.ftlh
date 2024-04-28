[#function checkManyToOne property ]
    [#if property.relationship?? && property.relationship == 'ManyToOne']
        [#return true]
    [#else]
        [#return false]
    [/#if]
[/#function]
package com.project.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
[#list model.properties as property]
	[#if checkManyToOne(property)]
import com.project.repositories.[=property.name?cap_first]Repository;
import com.project.entities.[=property.name?cap_first];
	[/#if]
[/#list]
import com.project.repositories.[=model.name?cap_first]Repository;
import com.project.entities.[=model.name?cap_first];

@Controller
public class [=model.name?cap_first]Controller {

	private final [=model.name?cap_first]Repository [=model.name]Repository;

[#list model.properties as property]
	[#if checkManyToOne(property)]
	@Autowired
	private [=property.name?cap_first]Repository [=property.name]Repository;

	[/#if]
[/#list]

	public [=model.name?cap_first]Controller([=model.name?cap_first]Repository [=model.name]Repository){
		this.[=model.name]Repository = [=model.name]Repository;
	}

	// 1. Get
	@GetMapping("/[=model.name]List")
	public String getList[=model.name?cap_first]List(Model model) {
		model.addAttribute("[=model.name]List", [=model.name]Repository.findAll());
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", [=property.name]Repository.findAll());
			[/#if]
		[/#list]
		return "[=model.name]List";
	}

	// 2. Form
	@GetMapping("/[=model.name]Form")
    public String prepareForm(Model model, @RequestParam(name="para", required=false) Optional<Integer> para) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]", [=property.name]Repository.findAll());
			[/#if]
		[/#list]

		if (para.isPresent()) {
            Optional<[=model.name?cap_first]> [=model.name] = [=model.name]Repository.findById(para.get());
			model.addAttribute("[=model.name]", [=model.name]);
	    return "[=model.name]Form";
		} else {
			model.addAttribute("[=model.name]", new [=model.name?cap_first]());
		}
		return "[=model.name]Form";
	}
	
	// 3. Post
	@PostMapping("/[=model.name]Post")
	    public String formPost(Model model, @Valid [=model.name?cap_first] [=model.name],
                                 BindingResult bindingResult, @RequestParam(name="para", required=false) Optional<Integer> para) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]", [=property.name]Repository.findAll());
			[/#if]
		[/#list]
		
		if(bindingResult.hasErrors()){
            return "[=model.name]Form";
        }

        if(para.isEmpty() && [=model.name]Repository.existsById([=model.name].getId())){
            bindingResult.addError(new FieldError("[=model.name]", "Id", "Id already in use"));
            return "[=model.name]Form";
        }
		
		[=model.name]Repository.save([=model.name]);
		return "redirect:/[=model.name]List";
	}

	// 4. Delete
	@GetMapping("/delete[=model.name?cap_first]")
	public String delete(@RequestParam(name="id") Optional<Integer> id){
		id.ifPresent(integer -> [=model.name]Repository.deleteById(integer));
		return "redirect:/[=model.name]List";
	}
}
