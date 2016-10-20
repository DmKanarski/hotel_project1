package by.kanarski.booking.commands.impl.user;

import by.kanarski.booking.commands.AbstractCommand;
import by.kanarski.booking.commands.factory.CommandType;
import by.kanarski.booking.constants.PagePath;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.constants.Value;
import by.kanarski.booking.dto.GlobalHotelDto;
import by.kanarski.booking.dto.OrderDto;
import by.kanarski.booking.entities.Hotel;
import by.kanarski.booking.entities.Room;
import by.kanarski.booking.exceptions.ServiceException;
import by.kanarski.booking.requestHandler.ServletAction;
import by.kanarski.booking.services.impl.HotelServiceImpl;
import by.kanarski.booking.services.impl.RoomServiceImpl;
import by.kanarski.booking.utils.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class GoToSelectHotelCommand extends AbstractCommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction;
        String page = null;
        HttpSession session = request.getSession();
        try {
            OrderDto order = RequestParser.parseOrder(request);
            String hotelName = order.getHotel().getHotelName();
            if (!hotelName.equals(Value.HOTEL_ALL_HOTELS)) {
                Hotel hotel = HotelServiceImpl.getInstance().getByHotelName(hotelName);
                order.setHotel(hotel);
                servletAction = ServletAction.CALL_COMMAND;
                servletAction.setCommandName(CommandType.GOTOSELECTROOMS.name());
            } else {
                List<Room> availableRooms = RoomServiceImpl.getInstance().getAvailableRooms(order);
                List<GlobalHotelDto> globalHotelDtoList = getHotelDtoList(availableRooms);
                session.setAttribute(Parameter.HOTEL_DTO_LIST, globalHotelDtoList);
                page = PagePath.SELECT_HOTEL_PATH;
                servletAction = ServletAction.FORWARD_PAGE;
            }
            session.setAttribute(Parameter.ORDER_DTO, order);
        } catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            servletAction = ServletAction.REDIRECT_PAGE;
            handleServiceException(request);
        }
        session.setAttribute(Parameter.CURRENT_PAGE_PATH, page);
        request.setAttribute(Parameter.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

    private List<GlobalHotelDto> getHotelDtoList(List<Room> roomList) {
        List<GlobalHotelDto> hotelList = new ArrayList<>();
        int separator = 0;
        for (int i = 0; i < roomList.size(); i++) {
            if (i < (roomList.size() - 1)) {
                String curHotelName = roomList.get(i).getRoomHotel().getHotelName();
                String nextHotelName = roomList.get(i + 1).getRoomHotel().getHotelName();
                if (!curHotelName.equals(nextHotelName)) {
                    Hotel hotel = roomList.get(i).getRoomHotel();
                    GlobalHotelDto globalHotelDto = new GlobalHotelDto(hotel.getHotelId(), hotel.getHotelLocation(), hotel.getHotelName(),
                            roomList.subList(separator, i + 1));
                    hotelList.add(globalHotelDto);
                    separator = i + 1;
                }
            } else {
                Hotel hotel = roomList.get(i).getRoomHotel();
                GlobalHotelDto globalHotelDto = new GlobalHotelDto(hotel.getHotelId(), hotel.getHotelLocation(), hotel.getHotelName(),
                        roomList.subList(separator, i + 1));
                hotelList.add(globalHotelDto);
                separator = i + 1;
            }
        }
        return hotelList;
    }
}
