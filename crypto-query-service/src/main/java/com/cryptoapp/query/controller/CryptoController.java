package com.cryptoapp.query.controller;

import com.cryptoapp.query.model.CryptoItem;
import com.cryptoapp.query.service.CryptoQueryServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Cryptocurrencies", description = "Consulta de criptomonedas en tiempo real")
@RestController
@RequestMapping("/items")
public class CryptoController {

    private final CryptoQueryServiceInterface queryService;

    public CryptoController(CryptoQueryServiceInterface queryService) {
        this.queryService = queryService;
    }

    // Documentacion en Swagger
    @Operation(
            summary = "Obtener todas las criptomonedas",
            description = "Devuelve la lista de las top 15 criptomonedas por capitalizacion de mercado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "Sin datos disponibles aun")
    })
    @GetMapping
    public ResponseEntity<List<CryptoItem>> getAllItems() {
        List<CryptoItem> items = queryService.getAllItems();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(items); // 200
    }

    @Operation(
            summary = "Obtener criptomoneda por ID",
            description = "Devuelve los datos de una moneda especifica. Ejemplos de ID: bitcoin, ethereum, solana"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criptomoneda encontrada"),
            @ApiResponse(responseCode = "404", description = "Criptomoneda no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CryptoItem> getItemById(
            @Parameter(description = "ID de la criptomoneda (ej: bitcoin, ethereum)")
            @PathVariable String id) {
        return queryService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // 404
    }

}

