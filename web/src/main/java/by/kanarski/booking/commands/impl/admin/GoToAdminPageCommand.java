package by.kanarski.booking.commands.impl.admin;

import by.kanarski.booking.commands.ICommand;
import by.kanarski.booking.commands.factory.CommandType;
import by.kanarski.booking.constants.MessageConstants;
import by.kanarski.booking.constants.PagePath;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.constants.Role;
import by.kanarski.booking.dto.OrderDto;
import by.kanarski.booking.entities.Hotel;
import by.kanarski.booking.entities.Room;
import by.kanarski.booking.entities.User;
import by.kanarski.booking.exceptions.ServiceException;
import by.kanarski.booking.managers.MessageManager;
import by.kanarski.booking.requestHandler.ServletAction;
import by.kanarski.booking.services.impl.HotelServiceImpl;
import by.kanarski.booking.services.impl.RoomServiceImpl;
import by.kanarski.booking.services.impl.UserServiceImpl;
import by.kanarski.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Дмитрий on 02.09.2016.
 */
public class GoToAdminPageCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction;
        String page = null;
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(Parameter.USER);
        if (admin.getRole().equals(Role.ADMINISTRATOR)) {
            servletAction = ServletAction.FORWARD_PAGE;
            page = PagePath.ADMIN_MAIN_PAGE_PATH;
        } else {
            request.setAttribute(Parameter.OPERATION_MESSAGE, "иди в жопу хакер сраный");
            servletAction = ServletAction.NO_ACTION;
        }
        session.setAttribute(Parameter.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }
}
