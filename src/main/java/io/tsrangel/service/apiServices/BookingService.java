package io.tsrangel.service.apiServices;

import io.tsrangel.context.SecurityContext;
import io.tsrangel.domain.dtos.Booking;
import io.tsrangel.domain.enums.Category;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.tsrangel.domain.enums.BookingStatus.*;
import static io.tsrangel.domain.enums.Category.*;

@ApplicationScoped
public class BookingService {
    private final Map<Long, Booking> bookings;

    public BookingService() {
        bookings  = new HashMap<>();
        bookings.put(123L, new Booking(
                123L,
                "Thiago",
                "Tesouros do Egito",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(14),
                PENDING,
                ADVENTURE
        ));
        bookings.put(456L, new Booking(
                456L,
                "Keli",
                "Aventura Amazônia",
                LocalDate.now().plusMonths(3),
                LocalDate.now().plusMonths(3).plusDays(14),
                CONFIRMED,
                TREASURE
        ));
    }

    public Optional<Booking> getBookingDetails(Long bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public Optional<Booking> cancelBooking(Long bookingId) {
        String currentUser = SecurityContext.getCurrentUser();
        if (bookings.containsKey(bookingId)) {
            Booking booking = bookings.get(bookingId);
            if (booking.customerName().equals(currentUser)) {
                Booking cancelledBooking = new Booking(
                        booking.id(),
                        booking.customerName(),
                        booking.destination(),
                        booking.startDate(),
                        booking.endDate(),
                        CANCELLED,
                        booking.category()
                );
                bookings.put(bookingId, cancelledBooking);
                return Optional.of(cancelledBooking);
            }
        }
        return Optional.empty();
    }

    public List<Booking> listPackagesByCategory(Category category) {
        return bookings.values().stream()
                .filter(booking -> booking.category() == category)
                .toList();
    }
}
