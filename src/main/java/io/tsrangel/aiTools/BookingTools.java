package io.tsrangel.aiTools;

import dev.langchain4j.agent.tool.Tool;
import io.tsrangel.domain.dtos.Booking;
import io.tsrangel.domain.enums.Category;
import io.tsrangel.service.apiServices.BookingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BookingTools {
    @Inject
    BookingService bookingService;

    @Tool("Obtém os detalhes completos de uma reserva com base em seu número de identificação (bookingId).")
    public String getBookingDFetails(long bookingId) {
        return bookingService.getBookingDetails(bookingId)
                .map(Booking::toString)
                .orElse(String.format("Reserva com ID %s não encontrada.", bookingId));
    }

    @Tool("""
            Cancela uma reserva existente.
            Para confirmar o cancelamento, é necessário fornecer o ID da reserva (bookingId).
            O usuário deve estar autenticado.
            """)
    public String cancelBooking(long bookingId) {
        return bookingService.cancelBooking(bookingId)
                .map(booking -> String.format("Reserva %s cancelada com sucesso. Status atual: %s", booking.id(), booking.status()))
                .orElse("Não foi possível cancelar a reserva. Verifique se o Id da reserva e se você tem permissão para cancelar.");
    }

    @Tool("Lista os pacotes de viagem disponíveis para uma determinada categoria (ex: ADVENTURE, TREASURE).")
    public String listPackagesByCategory(Category category) {
        List<Booking> bookings = bookingService.listPackagesByCategory(category);

        if (bookings.isEmpty()) {
            return String.format("Nenhum pacote encontrado para a categoria: %s", category);
        }

        return "Pacotes encontrados para a categoria: " + category + ": " + bookings.stream().map(Booking::destination).toList() + ".";
    }
}
