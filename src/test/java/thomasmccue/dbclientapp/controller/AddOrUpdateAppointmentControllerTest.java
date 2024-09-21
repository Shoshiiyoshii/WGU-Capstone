package thomasmccue.dbclientapp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

class AddOrUpdateAppointmentControllerTest {
    @Test
    void apptTimesInBusinessHours_testCaseOne() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 10, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 11, 0, 0, 0, ZoneId.of("America/New_York"));
        Assertions.assertTrue(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    void apptTimesInBusinessHours_testCaseTwo() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 10, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 23, 0, 0, 0, ZoneId.of("America/New_York"));
        Assertions.assertFalse(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    void apptTimesInBusinessHours_testCaseThree() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 6, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 7, 0, 0, 0, ZoneId.of("America/New_York"));
        Assertions.assertFalse(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    void apptTimesInBusinessHours_testCaseFour() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 6, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 23, 0, 0, 0, ZoneId.of("America/New_York"));
        Assertions.assertFalse(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    void apptTimesInBusinessHours_testCaseFive() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 8, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 22, 0, 0, 0, ZoneId.of("America/New_York"));
        Assertions.assertTrue(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    void apptTimesInBusinessHours_testCaseSix() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 13, 0, 0, 0, ZoneId.of("Europe/London"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 14, 0, 0, 0, ZoneId.of("Europe/London"));
        Assertions.assertTrue(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
    @Test
    public void testApptTimesInBusinessHours_testCaseSeven() {
        ZonedDateTime start = ZonedDateTime.of(2024, 9, 20, 11, 0, 0, 0, ZoneId.of("Europe/London"));
        ZonedDateTime end = ZonedDateTime.of(2024, 9, 20, 11, 30, 0, 0, ZoneId.of("Europe/London"));
        Assertions.assertFalse(AddOrUpdateAppointmentController.apptTimesInBusinessHours(start, end));
    }
}