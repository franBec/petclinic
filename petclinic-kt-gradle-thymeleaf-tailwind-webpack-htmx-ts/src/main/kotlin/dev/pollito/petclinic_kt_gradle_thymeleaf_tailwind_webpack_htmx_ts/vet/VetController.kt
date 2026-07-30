package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyService
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.SortDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/vets")
class VetController(
    private val vetService: VetService,
    private val specialtyService: SpecialtyService,
    private val vetMapper: VetMapper,
) {

    @ModelAttribute
    fun prepareContext(model: Model) {
        model.addAttribute("vetSpecialtySpecialtiesValues", specialtyService.getSpecialtyValues())
    }

    @GetMapping
    fun list(
        @RequestParam(name = "filter", required = false) filter: String?,
        @SortDefault(sort = ["id"]) @PageableDefault(size = 20) pageable: Pageable,
        model: Model,
    ): String {
        val vets = vetService.findAll(filter, pageable)
        model.addAttribute("vets", vets.map { vetMapper.map(it) })
        model.addAttribute("filter", filter)
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(vets))
        return "vet/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("vet") vetDTO: VetDTO): String = "vet/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("vet") @Valid vetDTO: VetDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "vet/add"
        }
        vetService.create(vetMapper.map(vetDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("vet.create.success"),
        )
        return "redirect:/vets"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("vet", vetMapper.map(vetService.get(id)))
        return "vet/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("vet") @Valid vetDTO: VetDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "vet/edit"
        }
        vetService.update(id, vetMapper.map(vetDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("vet.update.success"),
        )
        return "redirect:/vets"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        vetService.delete(id)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_INFO,
            WebUtils.getMessage("vet.delete.success"),
        )
        return "redirect:/vets"
    }
}
