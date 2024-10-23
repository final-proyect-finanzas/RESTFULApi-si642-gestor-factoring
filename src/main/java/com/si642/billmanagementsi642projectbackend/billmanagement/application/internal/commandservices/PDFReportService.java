package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PDFReportService {

    public String generatePdfReport(Wallet wallet) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        try {
            // Create fonts
            PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont regularFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            // Add company title with style
            Paragraph companyTitle = new Paragraph("Company ID: " + wallet.getCompany().getProfile().getName())
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE);
            document.add(companyTitle);

            // Add wallet details with style
            document.add(new Paragraph("Bank Name: " + wallet.getBank().getName()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Discount Date: " + wallet.getDiscountDate()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Initial Cost: " + wallet.getInitialCost()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Final Cost: " + wallet.getFinalCost()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Net Value: " + wallet.getNetValue()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Total Amount of Bills: " + wallet.getTotalAmountOfBills()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("TCEA: " + wallet.getTCEA()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Amount Received: " + wallet.getAmountReceived()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Amount Delivered: " + wallet.getAmountDelivered()).setFont(regularFont).setFontSize(12));

            // Add a table for the bills with style
            float[] columnWidths = {1, 5, 5, 5}; // Adjust column widths as needed
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.LIGHT_GRAY)));
            table.addHeaderCell(new Cell().add(new Paragraph("Description").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.LIGHT_GRAY)));
            table.addHeaderCell(new Cell().add(new Paragraph("Amount").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.LIGHT_GRAY)));
            table.addHeaderCell(new Cell().add(new Paragraph("Due Date").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.LIGHT_GRAY)));

            wallet.getBills().forEach(bill -> {
                table.addCell(new Cell().add(new Paragraph(bill.getId().toString()).setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getAmount().toString()).setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getDueDate().toString()).setFont(regularFont).setFontSize(12)));
            });

            document.add(table);
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pdfBytes);
    }
}