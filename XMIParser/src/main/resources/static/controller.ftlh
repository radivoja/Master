package com.project.controller;

import java.util.Optional;

import com.project.dao.[=model.name?cap_first]Dao;
import com.project.service.[=model.name?cap_first]Service;
[#list model.properties as property]
	[#if checkManyToOne(property)]
import com.project.service.[=property.name?cap_first]Service;
import com.project.dao.[=property.name?cap_first]Dao;
	[/#if]
[/#list]
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class [=model.name?cap_first]Controller {

	private final [=model.name?cap_first]Service [=model.name]Service;
[#list model.properties as property]
	[#if checkManyToOne(property)]
	private final [=property.name?cap_first]Service [=property.name]Service;
	[/#if]
[/#list]

	public [=model.name?cap_first]Controller([=model.name?cap_first]Service [=model.name]Service[#list model.properties as property][#if checkManyToOne(property)], [=property.name?cap_first]Service [=property.name]Service[/#if][/#list]){
		this.[=model.name]Service = [=model.name]Service;
[#list model.properties as property]
	[#if checkManyToOne(property)]
		this.[=property.name]Service = [=property.name]Service;
	[/#if]
[/#list]
	}

	// 1. Get
	@GetMapping("/[=model.name]List")
	public String get[=model.name?cap_first]List(Model model[#if pageable(model)],@RequestParam(defaultValue = "[=model.pageable.pageNo!1]") int pageNo, @RequestParam(defaultValue = "[=model.pageable.pageSize!5]") int pageSize [/#if]) {
	    [#if pageable(model)]
	    Page<[=model.name?cap_first]Dao> [=model.name]List = [=model.name]Service.get[=model.name?cap_first]Page(pageNo, pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        [#else]
        List<[=model.name?cap_first]Dao> [=model.name]List = [=model.name]Service.get[=model.name?cap_first]List();
	    [/#if]
		model.addAttribute("[=model.name]List", [=model.name]List);
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
			[/#if]
		[/#list]
		return "[=model.name]List";
	}

    [#if pageable(model)]
	public Page<[=model.name?cap_first]Dao> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNo-1, pageSize);
    	return [=model.name]Service.get[=model.name?cap_first]Page(pageNo, pageSize);
    }
    [/#if]

	// 2.1 Prepare Form
	@GetMapping("/[=model.name]Form")
    public String prepareForm(Model model) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
			[/#if]
		[/#list]
		[=model.name?cap_first]Dao [=model.name] = new [=model.name?cap_first]Dao();
		model.addAttribute("[=model.name]", [=model.name]);
		return "[=model.name]Form";
	}

	// 2.2 Post Form
	@PostMapping("/[=model.name]Form")
	public String formPost(Model model, @Valid @ModelAttribute("[=model.name]") [=model.name?cap_first]Dao [=model.name], BindingResult bindingResult) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
			[/#if]
		[/#list]
	    if(bindingResult.hasErrors()){
			return "[=model.name]Form";
		}

        [=model.name]Service.save[=model.name?cap_first]([=model.name]);
		return "redirect:/[=model.name]List";
	}

    // 3.1 Prepare Edit Form
    @GetMapping("[=model.name]Form/{[=model.name]Id}/edit")
    public String prepareUpdateForm(Model model, @PathVariable("[=model.name]Id") int [=model.name]Id){
        Optional<[=model.name?cap_first]Dao> [=model.name] = [=model.name]Service.find[=model.name?cap_first]ById([=model.name]Id);
        if([=model.name].isPresent()){
			[#list model.properties as property]
				[#if checkManyToOne(property)]
			model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
				[/#if]
			[/#list]
            model.addAttribute("[=model.name]", [=model.name].get());
            return "[=model.name]Form";
        }
        return "[=model.name]List";
    }

    // 3.2 Post Edit Form
    @PostMapping("[=model.name]Form/{[=model.name]Id}/edit")
    public String formUpdatePost(Model model, @Valid @ModelAttribute("[=model.name]") [=model.name?cap_first]Dao [=model.name], BindingResult bindingResult, @PathVariable("[=model.name]Id") int [=model.name]Id){
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
			[/#if]
		[/#list]
        if(bindingResult.hasErrors()){
            return "[=model.name]Form";
        }

        [=model.name].setId([=model.name]Id);
        [=model.name]Service.save[=model.name?cap_first]([=model.name]);
        return "redirect:/[=model.name]List";
    }

	// 4. Delete
	@GetMapping("/delete[=model.name?cap_first]")
	public String delete(@RequestParam(name="id") Optional<Integer> id){
		id.ifPresent(integer -> [=model.name]Service.delete[=model.name?cap_first](id));
		return "redirect:/[=model.name]List";
	}

	[#-- ManyToOne relationships resolver --]
	[#list model.properties as property]
		[#if checkManyToOne(property)]
	private List<[=property.name?cap_first]Dao> get[=property.name]ServiceList() {
		return [=property.name]Service.get[=property.name?cap_first]List();
	}

		[/#if]
	[/#list]
}

[#function checkManyToOne property]
    [#if property.relationship?? && property.relationship == 'ManyToOne']
        [#return true]
    [#else]
        [#return false]
    [/#if]
[/#function]

[#function pageable model]
    [#if model.pageable??]
        [#return true]
    [#else]
        [#return false]
    [/#if]
[/#function]
