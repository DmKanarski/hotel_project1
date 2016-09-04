package by.kanarski.booking.commands.impl.user;

import by.kanarski.booking.commands.ICommand;
import by.kanarski.booking.constants.Attribute;
import by.kanarski.booking.constants.MessageConstants;
import by.kanarski.booking.constants.PagePath;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.entities.Bill;
import by.kanarski.booking.entities.User;
import by.kanarski.booking.exceptions.ServiceException;
import by.kanarski.booking.managers.MessageManager;
import by.kanarski.booking.requestHandler.ServletAction;
import by.kanarski.booking.services.impl.BillServiceImpl;
import by.kanarski.booking.utils.BookingSystemLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GoToAccountCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page = null;
        HttpSession session = request.getSession();
        try {

            User user = (User) session.getAttribute(Attribute.USER);
            List<Bill> billList = BillServiceImpl.getInstance().getByUserId(user.getId());
            session.setAttribute(Parameter.BILL_LIST, billList);
            page = PagePath.ACCOUNT_PAGE_PATH;
        } catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            String errorMessage = MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE);
            request.setAttribute(Attribute.ERROR_DATABASE, errorMessage);
            BookingSystemLogger.getInstance().logError(getClass(), errorMessage, e);
        }
        session.setAttribute(Attribute.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

}
