package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.vet;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.specialty.SpecialtyService;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;
    private final SpecialtyService specialtyService;

    public VetController(final VetService vetService, final SpecialtyService specialtyService) {
        this.vetService = vetService;
        this.specialtyService = specialtyService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("vetSpecialtySpecialtiesValues", specialtyService.getSpecialtyValues());
    }

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<VetDTO> vets = vetService.findAll(filter, pageable);
        model.addAttribute("vets", vets);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(vets));
        return "vet/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("vet") final VetDTO vetDTO) {
        return "vet/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vet") @Valid final VetDTO vetDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vet/add";
        }
        vetService.create(vetDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vet.create.success"));
        return "redirect:/vets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("vet", vetService.get(id));
        return "vet/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("vet") @Valid final VetDTO vetDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vet/edit";
        }
        vetService.update(id, vetDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vet.update.success"));
        return "redirect:/vets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        vetService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("vet.delete.success"));
        return "redirect:/vets";
    }

}
