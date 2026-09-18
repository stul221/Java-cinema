package com.example.owp.controller;

import com.example.owp.dto.BookingRequest;
import com.example.owp.model.Booking;
import com.example.owp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBooking();
    }

    @RequestMapping("/my")
    public List<Booking> getMyBookings() {
        return bookingService.getMyBooking();
    }

    @DeleteMapping("/my/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
    }

    @PostMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public void createBooking(@Valid @RequestBody BookingRequest request) {
        bookingService.createBooking(request);
    }
}
