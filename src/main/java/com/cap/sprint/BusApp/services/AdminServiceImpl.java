package com.cap.sprint.BusApp.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.sprint.BusApp.entities.Booking;
import com.cap.sprint.BusApp.entities.Bus;
import com.cap.sprint.BusApp.entities.BusOperator;
import com.cap.sprint.BusApp.entities.BusOperatorRequest;
import com.cap.sprint.BusApp.entities.BusRoute;
import com.cap.sprint.BusApp.exception.BusDoesnotExistException;
import com.cap.sprint.BusApp.repos.IAdminRepository;
import com.cap.sprint.BusApp.repos.IBookingRepository;
import com.cap.sprint.BusApp.repos.IBusOperatorRepository;
import com.cap.sprint.BusApp.repos.IBusOperatorRequestRepository;
import com.cap.sprint.BusApp.repos.IBusRepository;
import com.cap.sprint.BusApp.repos.IBusRouteRepository;

@Service
public class AdminServiceImpl implements IAdminService{
	
	@Autowired
	IAdminRepository adminRepository;
	
	@Autowired
	IBusOperatorRepository busOperatorRepository;
	
	@Autowired
	IBookingRepository bookingRepository;
	
	@Autowired
	IBusRepository busRepository;
	
	@Autowired
	IBusOperatorRequestRepository busOperatorRequestRepository;
	
	@Autowired
	IBusRouteRepository busRouteRepository;
	
	@Override
	public List<BusOperator> getAllBusOperator(){
		return busOperatorRepository.findAll();
	}
	
	@Override
	public List<BusOperator> getAllBusOperatorByRoute(String route){
		List<BusOperator> busOperator = busOperatorRepository.findByBusBusRouteRouteName(route);
		return busOperator;
	}
	
	@Override
	public List<BusOperatorRequest> getAllBusOperatorsRequest(){
		List<BusOperatorRequest> busOperatorRequest = busOperatorRequestRepository.findAll();
		return busOperatorRequest;
	}
	
	@Override
	public void updateBusTime(Bus bus,LocalTime startTime, LocalTime endTime) {
		Booking b = bookingRepository.findByBusBusNumber(bus.getBusNumber());
		b.setJourneyStartTime(startTime);
		b.setJourneyEndTime(endTime);
	}
	
	@Override
	public void updateBusRoute(Bus bus, String route) {
		BusRoute b = busRouteRepository.findByBusBusNumber(bus.getBusNumber());
		b.setRouteName(route);
	}
	
	@Override
	public void updateBusFare(Bus bus, int fare) {
		Bus b = busRepository.findByBusNumber(bus.getBusNumber());
		b.setFare(fare);
	}
	
	@Override
	public void deleteBus(Bus bus) {
		Bus b = busRepository.findByBusNumber(bus.getBusNumber());
		if(b !=null ) {
			Booking booking = bookingRepository.findByBusBusNumber(b.getBusNumber());
			bookingRepository.delete(booking);
			//busOperatorRepository.delete(null);
			//busOperatorRequestRepository.delete(null);
			busRepository.deleteById(b.getId());
		} else {
			throw new BusDoesnotExistException("Bus does not exist!!!");
		}
	}
	
	@Override
	public void deleteBusByOperator(String busOperatorUsername) {
		Bus b = busRepository.findByBusOperatorBusOperatorUsername(busOperatorUsername);
		if(b != null) {
			busRepository.delete(b);
		} else {
			throw new BusDoesnotExistException("BusOperator does not exist!!!");
		}
	}
	
	@Override
	public int getRevenueByDate(LocalDate date) {
		int i = 0;
		List<Booking> booking = bookingRepository.findByDate(date);
		for (Booking b : booking) {
			i=i+b.getAmountPaid();
		}
		return i;
	}
	
	@Override
	public int getRevenueByBusRoute(String route) {
		int i = 0;
		List<Booking> booking = bookingRepository.findByBusRouteRouteName(route);
		for (Booking b: booking) {
			i=i+b.getAmountPaid();
		}
		return i;
	}
	
	@Override
	public int getRevenueByBusOperator(String busOperatorUsername) {
		int i = 0;
		List<Booking> booking = bookingRepository.findByBusBusOperatorBusOperatorUsername(busOperatorUsername);
		for (Booking b: booking) {
			System.out.println(b);
			i=i+b.getAmountPaid();
		}
		return i;
	}
	
}

