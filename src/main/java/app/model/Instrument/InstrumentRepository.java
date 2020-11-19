package app.model.Instrument;

import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository {
    public Instrument findByName(String name);
}
