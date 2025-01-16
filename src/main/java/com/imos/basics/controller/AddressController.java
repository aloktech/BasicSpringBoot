package com.imos.basics.controller;

import com.imos.basics.core.FailureResponseMessage;
import com.imos.basics.core.ResponseMessage;
import com.imos.basics.core.SuccessResponseMessage;
import com.imos.basics.dto.AddressDto;
import com.imos.basics.service.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class AddressController TODO
 *
 * @author Alok Ranjan Meher
 * @since 11-01-2025
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

  private final IAddressService addressService;

  @PostMapping(
      value = "/person/{mail-id}",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<? extends ResponseMessage<?>> addAddress(
      @PathVariable("mail-id") String mailId, @RequestBody AddressDto addressDto) {
    try {
      addressService.save(addressDto, mailId);

      log.info("Entity saved successfully");
      return ResponseEntity.ok(
          SuccessResponseMessage.builder().message("Entity saved successfully").build());
    } catch (Exception e) {
      log.error("Error while saving entity: {}", e.getMessage());
      return ResponseEntity.badRequest()
          .body(FailureResponseMessage.builder().errorMessage("Error while saving entity").build());
    }
  }

  @DeleteMapping(
      value = "/person/{mail-id}",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<? extends ResponseMessage<?>> deleteAddress(
      @PathVariable("mail-id") String mailId, @RequestBody AddressDto addressDto) {
    try {
      int rowModified = addressService.delete(addressDto, mailId);
      if (rowModified > 0) {
        log.info("{} Address are deleted by Person MailId: {} successfully", rowModified, mailId);
        return ResponseEntity.ok(
            SuccessResponseMessage.builder()
                .message(
                    "%d Address are deleted by Person MailId: %s successfully"
                        .formatted(rowModified, mailId))
                .build());
      } else {
        log.info("No Address are available");
        return ResponseEntity.ok(
            SuccessResponseMessage.builder().message("No Address are available").build());
      }
    } catch (Exception e) {
      log.error("Error while deleting Address by Person MailId: {} {}", mailId, e.getMessage());
      return ResponseEntity.badRequest()
          .body(
              FailureResponseMessage.builder()
                  .errorMessage(
                      "Error while deleting Address by Person MailId: %s %s"
                          .formatted(mailId, e.getMessage()))
                  .build());
    }
  }
}
