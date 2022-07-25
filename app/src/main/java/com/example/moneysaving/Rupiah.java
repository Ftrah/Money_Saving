package com.example.moneysaving;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Rupiah {
    Integer rupiah;

    public Rupiah(
            Integer rupiah
    ){
        this.rupiah = rupiah;
    }

    public String getRupiah(){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(this.rupiah);
    }
}
