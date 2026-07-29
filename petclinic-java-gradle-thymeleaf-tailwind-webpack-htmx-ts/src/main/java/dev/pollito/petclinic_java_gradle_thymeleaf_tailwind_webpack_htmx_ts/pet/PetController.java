package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner.OwnerService;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.type.TypeService;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final TypeService typeService;
    private final OwnerService ownerService;

    public PetController(final PetService petService, final TypeService typeService,
            final OwnerService ownerService) {
        this.petService = petService;
        this.typeService = typeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("typeValues", typeService.getTypeValues());
        model.addAttribute("ownerValues", ownerService.getOwnerValues());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("pets", petService.findAll());
        return "pet/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("pet") final PetDTO petDTO) {
        return "pet/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("pet") @Valid final PetDTO petDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pet/add";
        }
        petService.create(petDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pet.create.success"));
        return "redirect:/pets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("pet", petService.get(id));
        return "pet/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("pet") @Valid final PetDTO petDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "pet/edit";
        }
        petService.update(id, petDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("pet.update.success"));
        return "redirect:/pets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        try {
            petService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("pet.delete.success"));
        } catch (final ReferencedException referencedException) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage(
                    referencedException.getKey(), referencedException.getParams().toArray()));
        }
        return "redirect:/pets";
    }

}
