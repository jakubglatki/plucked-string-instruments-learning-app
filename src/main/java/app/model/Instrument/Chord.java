package app.model.Instrument;

import app.model.Instrument.Instrument;
import app.model.Instrument.InstrumentString;

import java.util.ArrayList;

public class Chord {

    private String name;
    private ArrayList<InstrumentString> strings;

    public Chord(){}

    public Chord(String name, Instrument instrument){
        this.name=name;
        this.strings=instrument.getStrings();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<InstrumentString> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<InstrumentString> strings) {
        this.strings = strings;
    }
}
