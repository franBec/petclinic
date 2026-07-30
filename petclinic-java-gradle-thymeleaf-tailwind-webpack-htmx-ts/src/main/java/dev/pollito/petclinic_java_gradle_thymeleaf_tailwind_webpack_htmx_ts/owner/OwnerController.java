package dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.owner;

import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.ReferencedException;
import dev.pollito.petclinic_java_gradle_thymeleaf_tailwind_webpack_htmx_ts.util.WebUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<Owner> owners = ownerService.findAll(filter, pageable);
        model.addAttribute("owners", owners.map(ownerMapper::map));
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(owners));
        return "owner/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("owner") final OwnerDTO ownerDTO) {
        return "owner/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("owner") @Valid final OwnerDTO ownerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "owner/add";
        }
        ownerService.create(ownerMapper.map(ownerDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("owner.create.success"));
        return "redirect:/owners";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("owner", ownerMapper.map(ownerService.get(id)));
        return "owner/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("owner") @Valid final OwnerDTO ownerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "owner/edit";
        }
        ownerService.update(id, ownerMapper.map(ownerDTO));
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("owner.update.success"));
        return "redirect:/owners";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        try {
            ownerService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("owner.delete.success"));
        } catch (final ReferencedException referencedException) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage(
                    referencedException.getKey(), referencedException.getParams().toArray()));
        }
        return "redirect:/owners";
    }
}
