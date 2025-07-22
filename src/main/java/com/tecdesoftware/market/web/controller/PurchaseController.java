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
@Tag(name = "Purchase Controller", description = "Manage Purchases in the Store")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @Operation(
            summary = "Get All Purchases",
            description = "Returns a List of All Purchases Made"
    )
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Purchases")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{idClient}")
    @Operation(
            summary = "Get Purchases by Client",
            description = "Returns a List of Purchases Made by a Specific Client"
    )
    @ApiResponse(responseCode = "200", description = "Purchases Found")
    @ApiResponse(responseCode = "404", description = "Client Not Found or No Purchases")
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
            summary = "Save a Purchase",
            description = "Saves a New Purchase and Returns the Created Purchase",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Purchase",
                                    value = """
                                            {
                                              "clientId": "ABC123",
                                              "date": "2025-07-22T10:15:30",
                                              "products": [
                                                {
                                                  "productId": 1,
                                                  "quantity": 3,
                                                  "total": 59.97,
                                                  "active": true
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Purchase Saved Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Purchase Data")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}
