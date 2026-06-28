package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;

public class PruebaReporte {
    static void main(){
        IAdministradorBL administradorBL = new AdministradorBLImpl();
        try{
            administradorBL.generarReporteFidelizacion();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
