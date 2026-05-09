package com.cloud.publishing.controller.web;

import com.cloud.publishing.constants.Parameters;
import com.cloud.publishing.constants.Urls;
import com.cloud.publishing.constants.publication.PublicationModelAttrs;
import com.cloud.publishing.constants.publication.PublicationPages;
import com.cloud.publishing.dto.request.PublicationRequest;
import com.cloud.publishing.dto.response.EmployeeShort;
import com.cloud.publishing.dto.response.PublicationGetDTO;
import com.cloud.publishing.mapper.EmployeeMapper;
import com.cloud.publishing.mapper.PublicationMapper;
import com.cloud.publishing.model.PublicationType;
import com.cloud.publishing.model.Type;
import com.cloud.publishing.service.CategoryService;
import com.cloud.publishing.service.EmployeeService;
import com.cloud.publishing.service.PublicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("webPublicationsController")
@PreAuthorize("hasRole('CHIEF_EDITOR')")
@RequestMapping(Urls.WEB_PUBLICATIONS)
public class PublicationsController {
    private final PublicationService publicationService;
    private final EmployeeService employeeService;
    private final CategoryService categoryService;
    private final EmployeeMapper employeeMapper;
    private final PublicationMapper publicationMapper;

    @Autowired
    public PublicationsController(
            PublicationService publicationService,
            EmployeeService employeeService,
            CategoryService categoryService,
            EmployeeMapper employeeMapper,
            PublicationMapper publicationMapper
    ) {
        this.publicationService = publicationService;
        this.employeeService = employeeService;
        this.categoryService = categoryService;
        this.employeeMapper = employeeMapper;
        this.publicationMapper = publicationMapper;
    }

    @GetMapping
    public String getAll(Model model) {
        List<PublicationGetDTO> publications =
                publicationService.getAll().stream()
                        .map(pub -> publicationMapper.toGetDTO(
                                pub,
                                categoryService.getByIds(pub.categories())
                        ))
                        .toList();
        model.addAttribute(PublicationModelAttrs.PUBLICATIONS, publications);
        return PublicationPages.LIST;
    }

    @GetMapping(Urls.NEW)
    public String showAddForm(Model model) {
        model.addAttribute(PublicationModelAttrs.PUBLICATION_REQUEST, PublicationRequest.empty());
        fillCommonModel(model);
        return PublicationPages.NEW;
    }

    @PostMapping
    public String add(
            @Valid @ModelAttribute(PublicationModelAttrs.PUBLICATION_REQUEST)
            PublicationRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            fillCommonModel(model);
            return PublicationPages.NEW;
        }
        publicationService.add(request);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    @GetMapping(Urls.ID + Urls.EDIT)
    public String showUpdateForm(Model model, @PathVariable(Parameters.ID) int id) {
        model.addAttribute(PublicationModelAttrs.PUBLICATION_REQUEST, publicationService.get(id));
        model.addAttribute(PublicationModelAttrs.PUBLICATION_ID, id);
        fillCommonModel(model);
        return PublicationPages.EDIT;
    }

    @PostMapping(Urls.ID)
    public String update(
            @Valid @ModelAttribute(PublicationModelAttrs.PUBLICATION_REQUEST)
            PublicationRequest request,
            BindingResult bindingResult,
            @PathVariable(Parameters.ID) int id,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PublicationModelAttrs.PUBLICATION_ID, id);
            fillCommonModel(model);
            return PublicationPages.EDIT;
        }
        publicationService.update(id, request);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    @DeleteMapping(Urls.ID)
    public String delete(@PathVariable(Parameters.ID) int id) {
        publicationService.delete(id);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    private void fillCommonModel(Model model) {
        List<EmployeeShort> journalists = employeeService.getByType(Type.JOURNALIST).stream()
                .map(employeeMapper::toShort)
                .toList();
        List<EmployeeShort> editors = employeeService.getByType(Type.EDITOR).stream()
                .map(employeeMapper::toShort)
                .toList();
        model.addAttribute(PublicationModelAttrs.PUBLICATION_TYPE, PublicationType.values());
        model.addAttribute(PublicationModelAttrs.CATEGORIES, categoryService.getAll());
        model.addAttribute(PublicationModelAttrs.JOURNALISTS, journalists);
        model.addAttribute(PublicationModelAttrs.EDITORS, editors);
    }
}