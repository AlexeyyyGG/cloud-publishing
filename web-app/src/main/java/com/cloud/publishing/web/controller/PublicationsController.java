package com.cloud.publishing.web.controller;

import com.cloud.publishing.common.reference.PublicationType;
import com.cloud.publishing.common.reference.Type;
import com.cloud.publishing.web.client.CategoryClient;
import com.cloud.publishing.web.client.EmployeeClient;
import com.cloud.publishing.web.client.PublicationClient;
import com.cloud.publishing.common.constants.Parameters;
import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.common.constants.publication.PublicationModelAttrs;
import com.cloud.publishing.common.constants.publication.PublicationPages;
import com.cloud.publishing.common.dto.request.PublicationRequest;
import com.cloud.publishing.common.dto.response.EmployeeShort;
import com.cloud.publishing.common.dto.response.PublicationGetDTO;
import com.cloud.publishing.web.mapper.EmployeeWebMapper;
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
    private final PublicationClient publicationClient;
    private final EmployeeClient employeeClient;
    private final CategoryClient categoryClient;
    private final EmployeeWebMapper employeeMapper;

    @Autowired
    public PublicationsController(
            PublicationClient publicationClient,
            EmployeeClient employeeClient,
            CategoryClient categoryClient,
            EmployeeWebMapper employeeMapper
    ) {
        this.publicationClient = publicationClient;
        this.employeeClient = employeeClient;
        this.categoryClient = categoryClient;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public String getAll(Model model) {
        List<PublicationGetDTO> publications = publicationClient.getAll();
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
        publicationClient.add(request);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    @GetMapping(Urls.ID + Urls.EDIT)
    public String showUpdateForm(Model model, @PathVariable(Parameters.ID) int id) {
        model.addAttribute(PublicationModelAttrs.PUBLICATION_REQUEST, publicationClient.get(id));
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
        publicationClient.update(id, request);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    @DeleteMapping(Urls.ID)
    public String delete(@PathVariable(Parameters.ID) int id) {
        publicationClient.delete(id);
        return PublicationPages.REDIRECT_PUBLICATIONS;
    }

    private void fillCommonModel(Model model) {
        List<EmployeeShort> journalists = employeeClient.getAll().stream()
                .filter(e -> e.type() == Type.JOURNALIST)
                .map(employeeMapper::toShort)
                .toList();
        List<EmployeeShort> editors = employeeClient.getAll().stream()
                .filter(e -> e.type() == Type.EDITOR)
                .map(employeeMapper::toShort)
                .toList();
        model.addAttribute(PublicationModelAttrs.PUBLICATION_TYPE, PublicationType.values());
        model.addAttribute(PublicationModelAttrs.CATEGORIES, categoryClient.getAll());
        model.addAttribute(PublicationModelAttrs.JOURNALISTS, journalists);
        model.addAttribute(PublicationModelAttrs.EDITORS, editors);
    }
}