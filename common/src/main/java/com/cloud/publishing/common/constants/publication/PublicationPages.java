package com.cloud.publishing.common.constants.publication;

import com.cloud.publishing.common.constants.Urls;

public class PublicationPages {
    private PublicationPages() {
    }

    public static final String LIST = "publications/publications";
    public static final String NEW = "publications/new";
    public static final String EDIT = "publications/edit";
    public static final String REDIRECT_PUBLICATIONS = "redirect:" + Urls.WEB_PUBLICATIONS;
}
