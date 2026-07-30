package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException
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
@RequestMapping("/owners")
class OwnerController(
    private val ownerService: OwnerService,
) {

    @GetMapping
    fun list(
        @RequestParam(name = "filter", required = false) filter: String?,
        @SortDefault(sort = ["id"]) @PageableDefault(size = 20) pageable: Pageable,
        model: Model,
    ): String {
        val owners = ownerService.findAll(filter, pageable)
        model.addAttribute("owners", owners)
        model.addAttribute("filter", filter)
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(owners))
        return "owner/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("owner") ownerDTO: OwnerDTO): String = "owner/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("owner") @Valid ownerDTO: OwnerDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "owner/add"
        }
        ownerService.create(ownerDTO)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("owner.create.success"),
        )
        return "redirect:/owners"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("owner", ownerService.get(id))
        return "owner/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("owner") @Valid ownerDTO: OwnerDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "owner/edit"
        }
        ownerService.update(id, ownerDTO)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("owner.update.success"),
        )
        return "redirect:/owners"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        try {
            ownerService.delete(id)
            redirectAttributes.addFlashAttribute(
                WebUtils.MSG_INFO,
                WebUtils.getMessage("owner.delete.success"),
            )
        } catch (referencedException: ReferencedException) {
            redirectAttributes.addFlashAttribute(
                WebUtils.MSG_ERROR,
                WebUtils.getMessage(
                    referencedException.key!!,
                    *referencedException.params.toTypedArray(),
                ),
            )
        }
        return "redirect:/owners"
    }
}
