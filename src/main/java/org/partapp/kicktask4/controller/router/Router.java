package org.partapp.kicktask4.controller.router;

public class Router {
    private String page;
    private RouterType type;

    public Router() {
    }
    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setForward() {
        this.type = RouterType.FORWARD;
    }

    public void setRedirect() {
        this.type = RouterType.REDIRECT;
    }


}
