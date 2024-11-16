package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
            Paragraph companyTitle = new Paragraph("Empresa: " + wallet.getCompany().getProfile().getName())
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLUE);
            document.add(companyTitle);

            // Add wallet details with style
            document.add(new Paragraph("Cartera descontada con el banco: " + wallet.getBank().getName()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Tasas del banco:").setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Costo inicial por documento: " + wallet.getBank().getInitialCostPerDocument()).setFont(regularFont).setFontSize(12).setMarginLeft(20));
            document.add(new Paragraph("Portes iniciales: " + wallet.getBank().getInitialPortes()).setFont(regularFont).setFontSize(12).setMarginLeft(20));
            document.add(new Paragraph("Comisión final: " + wallet.getBank().getFinalCommission()).setFont(regularFont).setFontSize(12).setMarginLeft(20));
            String typeRate = wallet.getBank().getTypeRate().name();
            document.add(new Paragraph("Tasa (" + typeRate + "): " + wallet.getBank().getRate() + "%").setFont(regularFont).setFontSize(12).setMarginLeft(20));
            if (typeRate.startsWith("TN")) {
                document.add(new Paragraph("Capitalización: " + wallet.getBank().getCapitalization()).setFont(regularFont).setFontSize(12).setMarginLeft(20));
            }
            document.add(new LineSeparator(new SolidLine()));
            document.add(new Paragraph("Moneda: " + wallet.getCurrency().name()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Fecha de descuento: " + wallet.getDiscountDate()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Costos iniciales de la operación: " + wallet.getInitialCost()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Costos finales de la operación: " + wallet.getFinalCost()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Valor Neto: " + wallet.getNetValue()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Facturas/Letras descontadas en la operación: " + wallet.getTotalAmountOfBills()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("TCEA: " + wallet.getTCEA() + "%").setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Monto recibida por la operación: " + wallet.getAmountReceived()).setFont(regularFont).setFontSize(12));
            document.add(new Paragraph("Monto entregado por la operación: " + wallet.getAmountDelivered()).setFont(regularFont).setFontSize(12));

            // Add a table for the bills with style
            float[] columnWidths = {1, 2, 2, 2, 2, 2}; // Adjust column widths as needed
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
            table.addHeaderCell(new Cell().add(new Paragraph("Numero de factura").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE)));
            table.addHeaderCell(new Cell().add(new Paragraph("Monto").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE)));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha de emision").setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE)));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha de vencimiento")).setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE));
            table.addHeaderCell(new Cell().add(new Paragraph("Deudor")).setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE));
            table.addHeaderCell(new Cell().add(new Paragraph("DNI/RUC Deudor")).setFont(boldFont).setFontSize(12).setBackgroundColor(ColorConstants.WHITE));


            wallet.getBills().forEach(bill -> {
                table.addCell(new Cell().add(new Paragraph(bill.getNumber() != null ? bill.getNumber() : "N/A").setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getAmount() != null ? bill.getAmount().toString() : "N/A").setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getIssueDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(bill.getIssueDate()) : "N/A").setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getDueDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(bill.getDueDate()) : "N/A").setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getDebtor() != null && bill.getDebtor().getProfile() != null ? bill.getDebtor().getProfile().getName() : "N/A").setFont(regularFont).setFontSize(12)));
                table.addCell(new Cell().add(new Paragraph(bill.getDebtor() != null && bill.getDebtor().getDocumentIdentifier() != null ? bill.getDebtor().getDocumentIdentifier() : "N/A").setFont(regularFont).setFontSize(12)));
            });

            document.add(table);
            document.add(new Paragraph("Report by: Gestify - 2024").setFont(
                    boldFont).setFontSize(15)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pdfBytes);
    }
}