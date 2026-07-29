package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
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
@RequestMapping("/types")
class TypeController(
    private val typeService: TypeService
) {

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("types", typeService.findAll())
        return "type/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("type") typeDTO: TypeDTO): String = "type/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("type") @Valid typeDTO: TypeDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "type/add"
        }
        typeService.create(typeDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("type.create.success"))
        return "redirect:/types"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("type", typeService.get(id))
        return "type/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("type") @Valid typeDTO: TypeDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "type/edit"
        }
        typeService.update(id, typeDTO)
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("type.update.success"))
        return "redirect:/types"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        try {
            typeService.delete(id)
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO,
                    WebUtils.getMessage("type.delete.success"))
        } catch (referencedException: ReferencedException) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedException.key!!,
                    *referencedException.params.toTypedArray()))
        }
        return "redirect:/types"
    }

}
