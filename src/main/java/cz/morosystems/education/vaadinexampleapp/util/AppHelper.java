package cz.morosystems.education.vaadinexampleapp.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.morosystems.education.vaadinexampleapp.entities.ComputerType;
import cz.morosystems.education.vaadinexampleapp.entities.RoleType;

/**
 * AppHelper contains project static properties and static functions
 * 
 * @author radoslav.kuzma
 * 
 */
public class AppHelper {

    public static final int CONTENT_DEVICES = 1;
    public static final int CONTENT_PROFILE = 2;
    public static final int CONTENT_LOGIN = 3;
    public static final int CONTENT_USER = 4;
    public static final int CONTENT_EDIT_USER = 5;
    public static final int CONTENT_ADD_CELLPHONE = 6;
    public static final int CONTENT_ADD_COMPUTER = 7;
    public static final int CONTENT_EDIT_CELLPHONE = 8;
    public static final int CONTENT_EDIT_COMPUTER = 9;
    public static final int CONTENT_ADD_USER = 10;
    public static final int CONTENT_EDIT_FOR_LANG = 11;
    public static final int CONTENT_ADD_FOR_LANG = 12;
    public static final int CONTENT_FOR_LANG = 13;
    public static final int CONTENT_KNOWLEDGE = 14;
    public static final int CONTENT_PROJECT = 15;
    public static final int CONTENT_ADD_PROJECT = 16;
    public static final int CONTENT_EDIT_PROJECT = 17;
    public static final int CONTENT_ADMIN_USERS = 18;
    public static final int CONTENT_ADMIN_PROJECT_ROLES = 19;
    public static final int CONTENT_ADMIN_CUSTOMER_AREAS = 20;
    public static final int CONTENT_ADMIN_CERTIFICATE_TYPES = 21;
    public static final int CONTENT_ADMIN_KNOWLEDGE_TYPES = 22;
    public static final int CONTENT_ADD_PROJECT_ROLES = 23;
    public static final int CONTENT_EDIT_PROJECT_ROLES = 24;
    public static final int CONTENT_ADD_CUSTOMER_AREAS = 25;
    public static final int CONTENT_EDIT_CUSTOMER_AREAS = 26;

    private UserUtil userUtil = UserUtil.getInstance();

    /** Device, Computer and CellPhone create form submit button value */
    public static String FROM_ADD = "Save";
    /** Device edit form submit button value */
    public static String FROM_EDIT = "Update";
    /** Computer and CellPhone create form cancel button name */
    public static String BUTTON_STORNO = "_cancel";
    /** string array of user roles */
    private static String[] userRoles = { RoleType.ROLE_USER.toString() };
    /** string array of user roles */
    private static String[] adminRoles = { RoleType.ROLE_ADMIN.toString() };
    /** User birthday date format */
    public static String DATE_FORMAT = "dd.MM.yyyy";
    private static String MSG_FILE = "cz.morosystems.education.vaadinexampleapp.messages.messages";

    /**
     * Returns whether authenticated user belongs to authority group
     * 
     * @param roles
     *            list of authority roles
     * @return true if authenticated user authority belongs to parameter roles
     */

    public static boolean hasRole(String[] roles) {
        boolean result = false;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null)
            for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
                String userRole = authority.getAuthority();
                for (String role : roles) {
                    if (role.equals(userRole)) {
                        result = true;
                        break;
                    }
                }

                if (result) {
                    break;
                }
            }

        return result;
    }

    /**
     * Returns whether authenticated user belongs to administrator roles
     * 
     * @return true if authenticated user has administrator role, else false
     */
    public static boolean hasAdminRole() {
        return hasRole(adminRoles);
    }

    /**
     * Returns whether authenticated user belongs to user roles
     * 
     * @return true if authenticated user has user role, else false
     */
    public static boolean hasUserRole() {
        return hasRole(userRoles);
    }

    /**
     * users/manageComputer.jsp page computer type map
     * 
     * @return types of computer and description map
     */
    public static Map<String, String> getComputerTypeMap() {
        Map<String, String> computerTypeMap = new LinkedHashMap<String, String>();
        computerTypeMap.put(ComputerType.NOTEBOOK.toString(), "Notebook");
        computerTypeMap.put(ComputerType.PC.toString(), "PC");
        return computerTypeMap;
    }

    /**
     * Gets project pages header login bar parameter
     * 
     * @return authenticated user username
     */
    public static String getUserName() {
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

    /**
     * 
     * Read message properties file
     * 
     */
    public static ResourceBundle getPropertiesFile(String languageCode) {
        ResourceBundle rb = ResourceBundle.getBundle(MSG_FILE, new Locale(languageCode));
        return rb;
    }

    /**
     * 
     * 
     * Format date to String.
     * 
     */
    public static String getCreatedString(DateTime date) {
        String createdString = "";
        if (date != null)
            createdString = org.joda.time.format.DateTimeFormat.forPattern(AppHelper.DATE_FORMAT).print(date);
        return createdString;
    }


}
