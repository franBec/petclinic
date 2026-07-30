package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.type;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
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
@RequestMapping("/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("types", typeService.findAll().stream().map(typeMapper::map).toList());
        return "type/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("type") final TypeDTO typeDTO) {
        return "type/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("type") @Valid final TypeDTO typeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "type/add";
        }
        typeService.create(typeMapper.map(typeDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("type.create.success"));
        return "redirect:/types";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("type", typeMapper.map(typeService.get(id)));
        return "type/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("type") @Valid final TypeDTO typeDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "type/edit";
        }
        typeService.update(id, typeMapper.map(typeDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("type.update.success"));
        return "redirect:/types";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        try {
            typeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("type.delete.success"));
        } catch (final ReferencedException referencedException) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage(
                    referencedException.getKey(), referencedException.getParams().toArray()));
        }
        return "redirect:/types";
    }
}
