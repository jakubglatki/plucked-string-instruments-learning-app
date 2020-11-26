package app.model.Instrument;

import app.model.Chord.Chord;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Instruments")
public class Instrument {
    @Id
    private String Id;
    private String name;
    private List<Chord> chords;

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
}
