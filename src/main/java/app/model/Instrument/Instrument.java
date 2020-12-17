package app.model.Instrument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Instruments")
public class Instrument {

    @Id
    private String id;
    private String name;
    private ArrayList<InstrumentString> strings;
    private List<Chord> chords;
    private ArrayList<String> stringLayoutClass;

    public Instrument(){}

    public Instrument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public void setChords(List<Chord> chords) {
        this.chords = chords;
    }

    public ArrayList<InstrumentString> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<InstrumentString> strings) {
        this.strings = strings;
    }

    public ArrayList<String> getStringLayoutClass() {
        return stringLayoutClass;
    }

    public void setStringLayoutClass(ArrayList<String> stringLayoutClass) {
        this.stringLayoutClass = stringLayoutClass;
    }
}
