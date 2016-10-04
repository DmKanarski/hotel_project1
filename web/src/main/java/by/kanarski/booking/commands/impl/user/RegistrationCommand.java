package by.kanarski.booking.commands.impl.user;

import by.kanarski.booking.commands.AbstractCommand;
import by.kanarski.booking.constants.OperationMessageKeys;
import by.kanarski.booking.constants.PagePath;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.entities.User;
import by.kanarski.booking.exceptions.ServiceException;
import by.kanarski.booking.managers.ResourceBuilder;
import by.kanarski.booking.requestHandler.ServletAction;
import by.kanarski.booking.services.impl.UserServiceImpl;
import by.kanarski.booking.utils.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationCommand extends AbstractCommand {
    private User user;

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page = null;
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Parameter.LOCALE);
        ResourceBundle bundle = ResourceBuilder.OPERATION_MESSAGES.setLocale(locale).create();
        try {
            user = RequestParser.parseUser(request);
            if (areFieldsFullStocked()) {
                if (UserServiceImpl.getInstance().checkIsNewUser(user)) {
                    UserServiceImpl.getInstance().registrateUser(user);
                    page = PagePath.REGISTRATION_PAGE_PATH;
                    request.setAttribute(Parameter.OPERATION_MESSAGE, bundle.getString(OperationMessageKeys.SUCCESS_OPERATION));
                } else {
                    page = PagePath.REGISTRATION_PAGE_PATH;
                    request.setAttribute(Parameter.ERROR_USER_EXISTS, bundle.getString(OperationMessageKeys.USER_EXISTS));
                }
            } else {
                request.setAttribute(Parameter.OPERATION_MESSAGE, bundle.getString(OperationMessageKeys.EMPTY_FIELDS));
                page = PagePath.REGISTRATION_PAGE_PATH;
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            handleServiceException(request);
        } catch (NumberFormatException e) {
            String exceptionMessage = bundle.getString(OperationMessageKeys.INVALID_NUMBER_FORMAT);
            request.setAttribute(Parameter.OPERATION_MESSAGE, exceptionMessage);
            page = PagePath.REGISTRATION_PAGE_PATH;
        }
        session.setAttribute(Parameter.CURRENT_PAGE_PATH, page);
        request.setAttribute(Parameter.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

    // TODO javascript???
    private boolean areFieldsFullStocked() {

        boolean isFullStocked = false;
        if (!user.getFirstName().isEmpty()
                & !user.getLastName().isEmpty()
                & !user.getLogin().isEmpty()
                & !user.getPassword().isEmpty()
                & !user.getEmail().isEmpty()) {
            isFullStocked = true;
        }
        return isFullStocked;
    }
}
