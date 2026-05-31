using System;
using System.Collections.Generic;
using System.Text;
using TicketFlowModel.Ubicacion;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace TicketFlowModel.Usuario
{
    public class Cliente : Usuario
    {
        public int puntosBonus { get; set; }

        public Cliente() : base(){}

        public Cliente(int puntosBonus) {
            
            this.puntosBonus = puntosBonus;
        }

    }
}
