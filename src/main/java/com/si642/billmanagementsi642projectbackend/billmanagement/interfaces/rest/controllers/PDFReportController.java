package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices.PDFReportService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class PDFReportController {

    @Autowired
    private PDFReportService pdfReportService;

    private final WalletQueryService walletQueryService;

    public PDFReportController(WalletQueryService walletQueryService){
        this.walletQueryService = walletQueryService;
    }

    @GetMapping("/pdf/{walletId}")
    public ResponseEntity<String> generatePdf(@PathVariable Long walletId) {
        var wallet = walletQueryService.findById(walletId);

        if (wallet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String pdfBase64 = pdfReportService.generatePdfReport(wallet.get());

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(pdfBase64);
    }
}