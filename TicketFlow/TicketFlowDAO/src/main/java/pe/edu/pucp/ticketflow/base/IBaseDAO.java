package pe.edu.pucp.ticketflow.base;

import java.util.List;

public interface IBaseDAO <Tipo, ID>{
    Tipo create(Tipo t);                  // INSERT
    Tipo read(ID id);                   // SELECT por ID
    Tipo update(Tipo t, ID id);      // UPDATE
    void delete(ID id);              // DELETE
    List<Tipo> listAll();               // SELECT *
}
