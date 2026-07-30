package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.visit;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.pet.PetService;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;
    private final VisitMapper visitMapper;

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("petValues", petService.getPetValues());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("visits", visitService.findAll().stream().map(visitMapper::map).toList());
        return "visit/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("visit") final VisitDTO visitDTO) {
        return "visit/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("visit") @Valid final VisitDTO visitDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "visit/add";
        }
        visitService.create(visitMapper.map(visitDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("visit.create.success"));
        return "redirect:/visits";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("visit", visitMapper.map(visitService.get(id)));
        return "visit/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("visit") @Valid final VisitDTO visitDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "visit/edit";
        }
        visitService.update(id, visitMapper.map(visitDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("visit.update.success"));
        return "redirect:/visits";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        visitService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("visit.delete.success"));
        return "redirect:/visits";
    }
}
