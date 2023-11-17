package com.prakhar.fooddboy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);
        TextView totalAmountTextView = findViewById(R.id.amounttotal);
        totalAmountTextView.setText("Total Amount: " + totalAmount);








        final TextView upiIdTextView = findViewById(R.id.upiIdTextView);

        LinearLayout gpay = findViewById(R.id.gpay);
        LinearLayout phonepay = findViewById(R.id.phonepe);
        LinearLayout paytm = findViewById(R.id.paytm);

        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upiId = upiIdTextView.getText().toString();
                openGooglePayForPayment(upiId, totalAmount, "INR", "Payment for your service");
            }

        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upiId = upiIdTextView.getText().toString();
                if (isAppInstalled("net.one97.paytm")) {
                    openPaytmForPayment(upiId, totalAmount, "Order123", "TxnToken123");
                } else {
                    Toast.makeText(PaymentActivity.this, "Paytm is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });





        phonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upiId = upiIdTextView.getText().toString();
                if (isAppInstalled("com.phonepe.app")) {
                    openPaymentApp(upiId, "com.phonepe.app", "PhonePe");
                } else {
                    Toast.makeText(PaymentActivity.this, "PhonePe is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });



//        phonepay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String upiId = upiIdTextView.getText().toString();
//
//                if (isGooglePayInstalled()) {
//                    openPaytmForPayment(upiId);
//                } else {
//                    Toast.makeText(PaymentActivity.this, "Google Pay is not installed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        phonepay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String upiId = upiIdTextView.getText().toString();
//                openPaymentApp(upiId, "com.phonepe.app", "PhonePe");
//            }
//        });


        ImageView chat = findViewById(R.id.whatsap);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open WhatsApp with a specific number
                String phoneNumber = "9455000512"; // WhatsApp number
                openWhatsApp(phoneNumber);
            }
        });



        ImageView call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the phone call app to make a call to the specified number
                String phoneNumber = "9455000512"; // Phone number to call
                makePhoneCall(phoneNumber);
            }
        });



        LinearLayout bank = findViewById(R.id.bank);
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBankDialog();
            }
        });


    }

    private void openPaymentApp(String upiId, String packageName, String appName) {
        Uri uri = Uri.parse("upi://pay?pa=" + upiId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(packageName);
        startActivity(intent);
    }

    private void showBankDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bank Transfer Details");
        builder.setMessage("Account No.: 923020020711164\nIFSC: UTIB0005099\nAccount name: A C N Tour And Travels\nBank name: Axis bank");
        builder.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Implement copying logic
                String accountDetails = "Account No.: 923020020711164\nIFSC: UTIB0005099\nAccount name: A C N Tour And Travels\nBank name: Axis bank";
                copyToClipboard(accountDetails);
                Toast.makeText(PaymentActivity.this, "Bank details copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void copyToClipboard(String textToCopy) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("BankDetails", textToCopy);
        clipboard.setPrimaryClip(clip);
    }

    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    // Function to open WhatsApp with a specific number
    private void openWhatsApp(String phoneNumber) {
        // Create the WhatsApp link with the phone number and message
        String message = "Please confirm my booking. \nMy transaction id or UTR number is:";
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private boolean isGooglePayInstalled() {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);

        for (PackageInfo packageInfo : packages) {
            String packageName = packageInfo.packageName;
            if (packageName.contains("google") || packageName.contains("pay")) {
                return true;
            }
        }

        return false;
    }

    private void openGooglePayForPayment(String upiId, double amount, String currency, String note) {
        Uri uri = Uri.parse("upi://pay?pa=" + upiId +
                "&am=" + amount +
                "&cu=" + currency +
                "&tn=" + note);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.nbu.paisa.user");
        startActivity(intent);
    }


    private void openPaytmForPayment(String upiId, double amount, String orderId, String txnToken) {
        Uri uri = Uri.parse("upi://pay?pa=" + upiId +
                "&pn=RecipientName" +
                "&mc=MerchantCode" +
                "&tid=" + txnToken +
                "&tr=TransactionReferenceID" +
                "&tn=PaymentNote" +
                "&am=" + amount +
                "&cu=INR" +
                "&url=LinkToYourCallbackURL" +
                "&pg=IMPS" +
                "&rc=UPICode" +
                "&orgid=OrgID");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("net.one97.paytm");
        startActivity(intent);
    }

    private boolean isAppInstalled(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }



    private void openPaytmForPayment(String upiId) {
        Uri uri = Uri.parse("upi://pay?pa=" + upiId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("net.one97.paytm");
        startActivity(intent);
    }


}
