package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/specialties")
class SpecialtyController(
    private val specialtyService: SpecialtyService,
    private val specialtyMapper: SpecialtyMapper,
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("specialties", specialtyService.findAll().map { specialtyMapper.map(it) })
        return "specialty/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("specialty") specialtyDTO: SpecialtyDTO): String = "specialty/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("specialty") @Valid specialtyDTO: SpecialtyDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "specialty/add"
        }
        specialtyService.create(specialtyMapper.map(specialtyDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("specialty.create.success"),
        )
        return "redirect:/specialties"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("specialty", specialtyMapper.map(specialtyService.get(id)))
        return "specialty/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("specialty") @Valid specialtyDTO: SpecialtyDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "specialty/edit"
        }
        specialtyService.update(id, specialtyMapper.map(specialtyDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("specialty.update.success"),
        )
        return "redirect:/specialties"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        specialtyService.delete(id)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_INFO,
            WebUtils.getMessage("specialty.delete.success"),
        )
        return "redirect:/specialties"
    }
}
