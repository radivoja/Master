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
import org.springframework.data.domain.*;
import jakarta.validation.Valid;
import java.util.List;
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
	public String getList[=model.name?cap_first]List(Model model[#if pageable(model)],@RequestParam(defaultValue = "[=model.pageable.pageNo]") int pageNo, @RequestParam(defaultValue = "[=model.pageable.pageSize]") int pageSize [/#if]) {
	    [#if pageable(model)]
	    Page<[=model.name?cap_first]> [=model.name]List = get[=model.name?cap_first]Page(pageNo, pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        [#else]
        List<[=model.name?cap_first]> [=model.name]List = [=model.name]Repository.findAll();
	    [/#if]
		model.addAttribute("[=model.name]List", [=model.name]List);
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]List", [=property.name]Repository.findAll());
			[/#if]
		[/#list]
		return "[=model.name]List";
	}

    [#if pageable(model)]
	public Page<[=model.name?cap_first]> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNo-1, pageSize);
    	return [=model.name]Repository.findAll(pageable);
    }
    [/#if]

	// 2.1 Prepare Form
	@GetMapping("/[=model.name]Form")
    public String prepareForm(Model model) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]", [=property.name]Repository.findAll());
			[/#if]
		[/#list]

		[=model.name?cap_first] [=model.name] = new [=model.name?cap_first]();
		model.addAttribute("[=model.name]", [=model.name]);
		return "[=model.name]Form";
	}
	
	// 2.2 Post Form
	@PostMapping("/[=model.name]Form")
	    public String formPost(Model model, @Valid [=model.name?cap_first] [=model.name], BindingResult bindingResult) {
		[#list model.properties as property]
			[#if checkManyToOne(property)]
		model.addAttribute("[=property.name]", [=property.name]Repository.findAll());
			[/#if]
		[/#list]

	    if(bindingResult.hasErrors()){
			return "[=model.name]Form";
		}

		if([=model.name]Repository.existsById([=model.name].getId())) {
		    bindingResult.addError(new FieldError("[=model.name]", "Id", "Id already in use"));
		    [=model.name].setId(null);
            return "[=model.name]Form";
        }
        [=model.name]Repository.save([=model.name]);
		return "redirect:/[=model.name]List";
	}

    // 3.1 Prepare Edit Form
    @GetMapping("[=model.name]Form/{[=model.name]Id}/edit")
    public String prepareUpdateForm(Model model, @PathVariable("[=model.name]Id") int [=model.name]Id){
        Optional<[=model.name?cap_first]> [=model.name] = [=model.name]Repository.findById([=model.name]Id);
        if([=model.name].isPresent()){
            model.addAttribute("[=model.name]", [=model.name].get());
            return "[=model.name]Form";
        }
        return "[=model.name]List";
    }

    // 3.2 Post Edit Form
    @PostMapping("[=model.name]Form/{[=model.name]Id}/edit")
    public String formUpdatePost(@Valid [=model.name?cap_first] [=model.name], @PathVariable("[=model.name]Id") int [=model.name]Id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "[=model.name]Form";
        }
        [=model.name].setId([=model.name]Id);
        [=model.name]Repository.save([=model.name]);

        return "redirect:/" + "[=model.name]List";
    }

	// 4. Delete
	@GetMapping("/delete[=model.name?cap_first]")
	public String delete(@RequestParam(name="id") Optional<Integer> id){
		id.ifPresent(integer -> [=model.name]Repository.deleteById(integer));
		return "redirect:/[=model.name]List";
	}
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