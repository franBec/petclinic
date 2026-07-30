package dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet

import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.OwnerService
import dev.pollito.petclinic_kt_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.TypeService
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
@RequestMapping("/pets")
class PetController(
    private val petService: PetService,
    private val typeService: TypeService,
    private val ownerService: OwnerService,
) {

    @ModelAttribute
    fun prepareContext(model: Model) {
        model.addAttribute("typeValues", typeService.getTypeValues())
        model.addAttribute("ownerValues", ownerService.getOwnerValues())
    }

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("pets", petService.findAll())
        return "pet/list"
    }

    @GetMapping("/add")
    fun add(@ModelAttribute("pet") petDTO: PetDTO): String = "pet/add"

    @PostMapping("/add")
    fun add(
        @ModelAttribute("pet") @Valid petDTO: PetDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "pet/add"
        }
        petService.create(petDTO)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("pet.create.success"),
        )
        return "redirect:/pets"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable(name = "id") id: Int, model: Model): String {
        model.addAttribute("pet", petService.get(id))
        return "pet/edit"
    }

    @PostMapping("/edit/{id}")
    fun edit(
        @PathVariable(name = "id") id: Int,
        @ModelAttribute("pet") @Valid petDTO: PetDTO,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes,
    ): String {
        if (bindingResult.hasErrors()) {
            return "pet/edit"
        }
        petService.update(id, petDTO)
        redirectAttributes.addFlashAttribute(
            WebUtils.MSG_SUCCESS,
            WebUtils.getMessage("pet.update.success"),
        )
        return "redirect:/pets"
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: Int, redirectAttributes: RedirectAttributes): String {
        try {
            petService.delete(id)
            redirectAttributes.addFlashAttribute(
                WebUtils.MSG_INFO,
                WebUtils.getMessage("pet.delete.success"),
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
        return "redirect:/pets"
    }
}
