package com.cap.sprint.BusApp.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cap.sprint.BusApp.entities.Bus;
import com.cap.sprint.BusApp.entities.BusOperator;
import com.cap.sprint.BusApp.services.IBusOperatorService;

@RestController
@RequestMapping(path = "/BusOperator")
public class BusOperatorController {
	
	@Autowired
	IBusOperatorService busOperatorService;
	
	
	//Adding a bus using service with Http Status
	@PostMapping("/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addBus(@RequestBody Bus b) {
		busOperatorService.addBus(b);
	}
	
	//Adding a bus operator using service with Http Status
	@PostMapping("/BusOp/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addBusOperator(@RequestBody BusOperator busOp) {
		busOperatorService.addBusOperator(busOp);
	}
	
	//Getting revenue by bus route using service with Http Status
	@GetMapping("/Revenue/{routeName}")
	public ResponseEntity<Integer> getRevenueByBusRoute(@PathVariable String routeName) {
		ResponseEntity<Integer> re = null;
		int revenue = 0;
		Optional<Integer> rev = Optional.of(busOperatorService.getRevenueByBusRoute(routeName));
		if(rev.isPresent()) {
			revenue = rev.get();
			re = new ResponseEntity<Integer>(revenue, HttpStatus.OK);
		}
		else {
			re = new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		return re;
	}
		
	//Getting revenue by bus route and date using service with Http Status
	@GetMapping("/Revenue/{routeName}/{date}")
	public ResponseEntity<Integer> getRevenueByBusRouteAndDate(@PathVariable String routeName, @PathVariable String date) {
		ResponseEntity<Integer> re = null;
		int revenue = 0;
		LocalDate date1 = LocalDate.parse(date);
		Optional<Integer> rev = Optional.of(busOperatorService.getRevenueByBusRouteAndDate(routeName, date1));
		if(rev.isPresent()) {
			revenue = rev.get();
			re = new ResponseEntity<Integer>(revenue, HttpStatus.OK);
		}
		else {
			re = new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		return re;
	}
	
	//Getting monthly revenue by bus route using service with Http Status
	@GetMapping("/MonthlyRevenue/{routeName}/{month}/{year}")
	public ResponseEntity<Integer> getMonthlyRevenueByBusRoute(@PathVariable String routeName, @PathVariable String month, @PathVariable String year) {
		ResponseEntity<Integer> re = null;
		int revenue = 0;
		Optional<Integer> rev = Optional.of(busOperatorService.getMonthlyRevenueByBusRoute(routeName, month, year));
		if(rev.isPresent()) {
			revenue = rev.get();
			re = new ResponseEntity<Integer>(revenue, HttpStatus.OK);
		}
		else {
			re = new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		return re;
	}
	
	//Getting yearly revenue by bus route using service with Http Status
	@GetMapping("/YearlyRevenue/{routeName}/{year}")
	public ResponseEntity<Integer> getYearlyRevenueByBusRoute(@PathVariable String routeName, @PathVariable String year) {
		ResponseEntity<Integer> re = null;
		int revenue = 0;
		Optional<Integer> rev = Optional.of(busOperatorService.getYearlyRevenueByBusRoute(routeName, year));
		if(rev.isPresent()) {
			revenue = rev.get();
			re = new ResponseEntity<Integer>(revenue, HttpStatus.OK);
		}
		else {
			re = new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		return re;
	}
}