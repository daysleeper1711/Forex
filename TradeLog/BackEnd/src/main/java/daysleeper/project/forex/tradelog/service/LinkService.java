package daysleeper.project.forex.tradelog.service;

import daysleeper.project.forex.tradelog.utilies.PaginationLinks;
import javax.ws.rs.core.UriInfo;

public class LinkService {
    
    public String self(UriInfo uriInfo, Class resourceClass, Object id) {
        return uriInfo.getBaseUriBuilder()
                .path(resourceClass)
                .path("{id}")
                .resolveTemplate("id", id)
                .build()
                .toString();
    }
    
    public PaginationLinks pagination(UriInfo uriInfo, Class resourceClass, int totalPages, int page, int size) {
        PaginationLinks paginationLinks = new PaginationLinks();
        if (totalPages == 1) {
            String onePage = uriInfo.getBaseUriBuilder()
                    .path(resourceClass)
                    .queryParam("size", size)
                    .build()
                    .toString();
            paginationLinks.setOnePage(onePage);
            return paginationLinks;
        }
        if (page > 1) {
            String firstPage = uriInfo.getBaseUriBuilder()
                    .path(resourceClass)
                    .queryParam("page", 1)
                    .queryParam("size", size)
                    .build()
                    .toString();
            System.out.println(">>>>>>>>>>first: " + firstPage);
            paginationLinks.setFirstPage(firstPage);
        }
        if (page < totalPages) {
            String lastPage = uriInfo.getBaseUriBuilder()
                    .path(resourceClass)
                    .queryParam("page", totalPages)
                    .queryParam("size", size)
                    .build()
                    .toString();
            System.out.println(">>>>>>>>>>last: " + lastPage);
            paginationLinks.setLastPage(lastPage);
        }
        if (page > 3) {
            String previousPage = uriInfo.getBaseUriBuilder()
                    .path(resourceClass)
                    .queryParam("page", page--)
                    .queryParam("size", size)
                    .build()
                    .toString();
            System.out.println(">>>>>>>>>>previous: " + previousPage);
            paginationLinks.setPreviousPage(previousPage);
        }
        if (page < totalPages - 2) {
            String nextPage = uriInfo.getBaseUriBuilder()
                    .path(resourceClass)
                    .queryParam("page", page++)
                    .queryParam("size", size)
                    .build()
                    .toString();
            System.out.println(">>>>>>>>>>next: " + nextPage);
            paginationLinks.setNextPage(nextPage);
        }
        return paginationLinks;
    }
    
}
