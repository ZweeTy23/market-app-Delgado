package com.tecdesoftware.market.web.controller;

import com.tecdesoftware.market.domain.Purchase;
import com.tecdesoftware.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase Controller", description = "Manage Purchases made by Clients")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @Operation(
            summary = "Get All Purchases",
            description = "Return a List of All Purchases Made"
    )
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Purchases")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{idClient}")
    @Operation(
            summary = "Get Purchases by Client ID",
            description = "Return All Purchases Associated with a Specific Client"
    )
    @ApiResponse(responseCode = "200", description = "Purchases Found for Client")
    @ApiResponse(responseCode = "404", description = "No Purchases Found for Client")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<List<Purchase>> getByClient(
            @Parameter(description = "Client ID", example = "ABC123", required = true)
            @PathVariable("idClient") String clientId) {
        return purchaseService.getByClient(clientId)
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save a New Purchase",
            description = "Registers a New Purchase with Client, Products and Details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Purchase",
                                    value = """
                                            {
                                              "clientId": "ABC123",
                                              "date": "2025-07-19T13:45:00",
                                              "paymentMethod": "credit",
                                              "comment": "Urgent order",
                                              "state": "completed",
                                              "items": [
                                                {
                                                  "productId": 7,
                                                  "quantity": 3,
                                                  "total": 58.50,
                                                  "active": true
                                                },
                                                {
                                                  "productId": 2,
                                                  "quantity": 1,
                                                  "total": 19.50,
                                                  "active": true
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Purchase Created Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Purchase Data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<Purchase> save(
            @RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}
