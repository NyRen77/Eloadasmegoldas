package com.example.android.eloadas;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** This method is called when the + button is clicked */
    public void increment(View view) {

        if(quantity+1<101) {
            quantity += 1;
        }
        displayQuantity(quantity);
    }

    /** This method is called when the - button is clicked */
    public void decrement(View view) {
        if(quantity-1>0) {
            quantity -= 1;
        }
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the order button is clicked.
     */

//Órán előadás rész

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedcream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        CheckBox fillerCheckbox = (CheckBox) findViewById(R.id.filler_checkbox);
        boolean hasFiller = fillerCheckbox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price = calculatePrice(hasWhippedcream, hasChocolate, hasFiller);
        displayMessage(createOrderSummary(name, price, hasWhippedcream, hasChocolate));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name, price, hasWhippedcream, hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }


    // Önálló feladat

    public void createEvent(View view){
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "rendeles")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "pannon egyetem")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 700)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 800);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    // Eddig

    private int calculatePrice(boolean whippedcream, boolean chocolate, boolean filler) {
        int price = 5;
        if (whippedcream){
            price+=1;
        }
        if(chocolate){
            price+=2;
        }
        if(filler){
            price+=3;
        }
        price*=quantity;
        return price;
    }

    private String createOrderSummary(String name,int price, boolean addWhippedcream, boolean addChocolate){

        String priceMessage ="Name: " +name;
        priceMessage+= "\nAdd whipped cream? "+ addWhippedcream;
        priceMessage+= "\nAdd chocolate? "+addChocolate;
        priceMessage+= "\n Quantity: "+quantity;
        priceMessage+="\nTotal: $" + price;
        priceMessage+= "\nThank you!";
        return priceMessage;
    }

        private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}


