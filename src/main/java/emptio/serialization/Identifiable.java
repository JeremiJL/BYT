package emptio.serialization;

import emptio.common.Enviorment;

public interface Identifiable {
    IdService idService = new IdService(Enviorment.GLOBAL);
    int getId();
}
