package app.controller;

import app.model.Instrument.Chord;
import app.model.Instrument.InstrumentString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordController {

    public static Chord setGuitarChord(String name, String s1, String s2, String s3, String s4,String s5, String s6){
        Chord chord=new Chord();
        chord.setName(name);
        ArrayList<String> str1= new ArrayList<>(List.of(s1));
        ArrayList<String> str2= new ArrayList<>(List.of(s2));
        ArrayList<String> str3= new ArrayList<>(List.of(s3));
        ArrayList<String> str4= new ArrayList<>(List.of(s4));
        ArrayList<String> str5= new ArrayList<>(List.of(s5));
        ArrayList<String> str6= new ArrayList<>(List.of(s6));

        InstrumentString i1= new InstrumentString();
        i1.setValue(str1);

        InstrumentString i2= new InstrumentString();
        i2.setValue(str2);

        InstrumentString i3= new InstrumentString();
        i3.setValue(str3);

        InstrumentString i4= new InstrumentString();
        i4.setValue(str4);

        InstrumentString i5= new InstrumentString();
        i5.setValue(str5);

        InstrumentString i6= new InstrumentString();
        i6.setValue(str6);

        ArrayList<InstrumentString> instrumentStrings=new ArrayList<>(List.of(i1,i2,i3,i4,i5,i6));
        chord.setStrings(instrumentStrings);
        return chord;
    }


    public static Chord setUkuleleChord(String name, String s1, String s2, String s3, String s4){
        Chord chord=new Chord();
        chord.setName(name);
        ArrayList<String> str1= new ArrayList<>(List.of(s1));
        ArrayList<String> str2= new ArrayList<>(List.of(s2));
        ArrayList<String> str3= new ArrayList<>(List.of(s3));
        ArrayList<String> str4= new ArrayList<>(List.of(s4));

        InstrumentString i1= new InstrumentString();
        i1.setValue(str1);

        InstrumentString i2= new InstrumentString();
        i2.setValue(str2);

        InstrumentString i3= new InstrumentString();
        i3.setValue(str3);

        InstrumentString i4= new InstrumentString();
        i4.setValue(str4);

        ArrayList<InstrumentString> instrumentStrings=new ArrayList<>(List.of(i1,i2,i3,i4));
        chord.setStrings(instrumentStrings);
        return chord;
    }
}
