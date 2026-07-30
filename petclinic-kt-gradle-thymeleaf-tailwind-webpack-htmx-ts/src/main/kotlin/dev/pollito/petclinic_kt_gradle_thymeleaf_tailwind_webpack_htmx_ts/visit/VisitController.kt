package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetService
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
@RequestMapping("/visits")
class VisitController(
    private val visitService: VisitService,
    private val petService: PetService,
    private val visitMapper: VisitMapper,
) {

    @ModelAttribute
    fun prepareContext(model: Model) {
        model.addAttribute("petValues", petService.getPetValues())
    }

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("visits", visitService.findAll().map { visitMapper.map(it) })
        return "visit/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("visit") visitDTO: VisitDTO): String = "visit/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("visit") @Valid visitDTO: VisitDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "visit/add"
        }
        visitService.create(visitMapper.map(visitDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("visit.create.success"),
        )
        return "redirect:/visits"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("visit", visitMapper.map(visitService.get(id)))
        return "visit/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("visit") @Valid visitDTO: VisitDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "visit/edit"
        }
        visitService.update(id, visitMapper.map(visitDTO))
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("visit.update.success"),
        )
        return "redirect:/visits"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        visitService.delete(id)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_INFO,
            WebUtils.getMessage("visit.delete.success"),
        )
        return "redirect:/visits"
    }
}
