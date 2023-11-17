package com.prakhar.fooddboy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import android.view.View;

public class InvoiceActivity extends AppCompatActivity {

    private EditText etTotal, etGST, etDeliveryCharges, etGrandTotal, etDiscount, etNetPay;
    private Button btnPrintPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        etTotal = findViewById(R.id.etTotal);
        etGST = findViewById(R.id.etGST);
        etDeliveryCharges = findViewById(R.id.etDeliveryCharges);
        etGrandTotal = findViewById(R.id.etGrandTotal);
        etDiscount = findViewById(R.id.etDiscount);
        etNetPay = findViewById(R.id.etNetPay);
        btnPrintPDF = findViewById(R.id.btnPrintPDF);

        btnPrintPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from the EditText fields
                String stotal = etTotal.getText().toString();
                String gst = etGST.getText().toString();
                String dcharges = etDeliveryCharges.getText().toString();
                String gtotal = etGrandTotal.getText().toString();
                String discount = etDiscount.getText().toString();
                String netpay = etNetPay.getText().toString();

                // Generate a PDF with the data
                generatePDF(stotal, gst, dcharges, gtotal, discount, netpay);
            }
        });
    }

    private void generatePDF(String stotal, String gst, String dcharges, String gtotal, String discount, String netpay) {
        // Create a PDF document
        Document document = new Document();

        try {
            // Provide a path to save the PDF
            String pdfFilePath = getFilesDir() + "/invoice.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));

            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Invoice Details"));
            document.add(new Paragraph("Total Value: " + stotal));
            document.add(new Paragraph("GST: " + gst));
            document.add(new Paragraph("Delivery Charges: " + dcharges));
            document.add(new Paragraph("Grand Total: " + gtotal));
            document.add(new Paragraph("Discount: " + discount));
            document.add(new Paragraph("Net Pay: " + netpay));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
