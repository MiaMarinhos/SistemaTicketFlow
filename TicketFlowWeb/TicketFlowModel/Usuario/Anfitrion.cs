using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Usuario
{
    public class Anfitrion : Usuario
    {
        public string razonSocial { get; set; }
        public string ruc { get; set; }
        public string cuentaBancaria { get; set; }

        public Anfitrion() : base(){}
        
        public Anfitrion(string razonSocial, string ruc, 
            string cuentaBancaria)
        {
            this.ruc = ruc;
            this.razonSocial = razonSocial;
            this.cuentaBancaria = cuentaBancaria;
        }
        

    }
}
