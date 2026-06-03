package [=model.uri].controller;


import java.util.Optional;

import [=model.uri].dto.[=model.name?cap_first]Dto;
import [=model.uri].service.[=model.name?cap_first]Service;
[#list model.properties?values as property]
	[#if checkManyToOne(property)]
import [=model.uri].service.[=property.name?cap_first]Service;
import [=model.uri].dto.[=property.name?cap_first]Dto;
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

	private final [=model.name?cap_first]Service [=model.name?uncap_first]Service;
[#list model.properties?values as property]
	[#if checkManyToOne(property)]
	private final [=property.name?cap_first]Service [=property.name]Service;
	[/#if]
[/#list]

	public [=model.name?cap_first]Controller([=model.name?cap_first]Service [=model.name]Service[#list model.properties?values as property][#if checkManyToOne(property)], [=property.name?cap_first]Service [=property.name]Service[/#if][/#list]){
		this.[=model.name?uncap_first]Service = [=model.name]Service;
[#list model.properties?values as property]
	[#if checkManyToOne(property)]
		this.[=property.name]Service = [=property.name]Service;
	[/#if]
[/#list]
	}

	// 1. Get
	@GetMapping("/[=model.name?uncap_first]List")
	public String get[=model.name?cap_first]List(Model model, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "[=(model.listView.pageSize)!"5"]") int pageSize) {
	    Page<[=model.name?cap_first]Dto> [=model.name?uncap_first]List = [=model.name?uncap_first]Service.get[=model.name?cap_first]Page(pageNo, pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);

		model.addAttribute("[=model.name?uncap_first]List", [=model.name?uncap_first]List);
		[#list model.properties?values as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name]ServiceList());
			[/#if]
		[/#list]
		return "[=model.name?uncap_first]List";
	}

	public Page<[=model.name?cap_first]Dto> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNo-1, pageSize);
    	return [=model.name?uncap_first]Service.get[=model.name?cap_first]Page(pageNo, pageSize);
    }

	// 2.1 Prepare Form
	@GetMapping("/[=model.name?uncap_first]Form")
    public String prepareForm(Model model) {
		[#list model.properties?values as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name?uncap_first]List", get[=property.name?uncap_first]ServiceList());
			[/#if]
		[/#list]
		[=model.name?cap_first]Dto [=model.name?uncap_first] = new [=model.name?cap_first]Dto();
		model.addAttribute("[=model.name?uncap_first]", [=model.name?uncap_first]);
		return "[=model.name?uncap_first]Form";
	}

	// 2.2 Post Form
	@PostMapping("/[=model.name?uncap_first]Form")
	public String formPost(Model model, @Valid @ModelAttribute("[=model.name?uncap_first]") [=model.name?cap_first]Dto [=model.name], BindingResult bindingResult) {
		[#list model.properties?values as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name?uncap_first]ServiceList());
			[/#if]
		[/#list]
	    if(bindingResult.hasErrors()){
			return "[=model.name?uncap_first]Form";
		}

        [=model.name?uncap_first]Service.save[=model.name?cap_first]([=model.name]);
		return "redirect:/[=model.name?uncap_first]List";
	}

    // 3.1 Prepare Edit Form
    @GetMapping("[=model.name?uncap_first]Form/{[=model.name]Id}/edit")
    public String prepareUpdateForm(Model model, @PathVariable("[=model.name?uncap_first]Id") int [=model.name]Id){
        Optional<[=model.name?cap_first]Dto> [=model.name] = [=model.name?uncap_first]Service.find[=model.name?cap_first]ById([=model.name]Id);
        if([=model.name].isPresent()){
			[#list model.properties?values as property]
				[#if checkManyToOne(property)]
			model.addAttribute("[=property.name]List", get[=property.name?uncap_first]ServiceList());
				[/#if]
			[/#list]
            model.addAttribute("[=model.name]", [=model.name].get());
            return "[=model.name?uncap_first]Form";
        }
        return "[=model.name?uncap_first]List";
    }


    // 3.2 Post Edit Form
    @PostMapping("[=model.name?uncap_first]Form/{[=model.name]Id}/edit")
    public String formUpdatePost(Model model, @Valid @ModelAttribute("[=model.name?uncap_first]") [=model.name?cap_first]Dto [=model.name], BindingResult bindingResult, @PathVariable("[=model.name?uncap_first]Id") int [=model.name]Id){
		[#list model.properties?values as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", get[=property.name?uncap_first]ServiceList());
			[/#if]
		[/#list]
        if(bindingResult.hasErrors()){
            return "[=model.name?uncap_first]Form";
        }

        [=model.name].setId([=model.name]Id);
        [=model.name?uncap_first]Service.save[=model.name?cap_first]([=model.name]);
        return "redirect:/[=model.name?uncap_first]List";
    }

	// 4. Delete
	@GetMapping("/delete[=model.name?cap_first]")
	public String delete(@RequestParam(name="id") Optional<Integer> id){
		id.ifPresent(integer -> [=model.name?uncap_first]Service.delete[=model.name?cap_first](id));
		return "redirect:/[=model.name?uncap_first]List";
	}

	[#-- ManyToOne relationships resolver --]
	[#list model.properties?values as property]
		[#if checkManyToOne(property)]
	private List<[=property.name?cap_first]Dto> get[=property.name]ServiceList() {
		return [=property.name?uncap_first]Service.get[=property.name?cap_first]List();
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

