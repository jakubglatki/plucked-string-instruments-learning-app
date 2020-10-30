package app.model.Instrument;

import app.model.Chord.Chord;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "instruments")
public class Instrument {
    private String name;
    private List<Chord> chords;

    public Instrument(String name) {
        this.name = name;
    }
}
