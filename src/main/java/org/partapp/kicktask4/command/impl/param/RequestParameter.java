package org.partapp.kicktask4.command.impl.param;

public final class RequestParameter {

    private RequestParameter() {}

    public static final String MAIN_PAGE = "index.jsp";
    public static final String LOGIN_PAGE = "pages/user/login.jsp";
    public static final String REGISTRATION_PAGE = "pages/user/registration.jsp";
    public static final String USERS_PAGE = "pages/user/users.jsp";
    public static final String ITEMS_PAGE = "pages/item/items.jsp";
    public static final String ADD_ITEM_PAGE = "pages/item/addItem.jsp";

    public static final String PARAM_USER = "user";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USER_LIST = "users";

    public static final String PARAM_ITEM = "item";
    public static final String PARAM_ITEM_ID = "itemId";
    public static final String PARAM_ITEM_NAME = "itemName";
    public static final String PARAM_ITEM_DESCRIPTION = "itemDescription";
    public static final String PARAM_ITEM_OWNER_ID = "ownerId";
    public static final String PARAM_ITEM_LIST = "items";

    public static final String PARAM_CURRENT_PAGE = "current_page";
    public static final String PARAM_ERROR_MSG = "error_msg";
    public static final String PARAM_SUCCESS_MSG = "success_msg";

}
