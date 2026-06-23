using System;
using System.Collections.Generic;
using System.Text;

namespace Models.banco
{
    public class Banco
    {
        public int id { get; set; }
        public string nombre_largo { get; set; }
        public string nombre_corto { get; set; }
        public Banco() { }
        public Banco(int id, string nombre_largo, string nombre_corto)
        {
            this.id = id;
            this.nombre_largo = nombre_largo;
            this.nombre_corto = nombre_corto;
        }
    }
}
