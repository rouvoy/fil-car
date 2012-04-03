package fil.car.annuaire.tcp {
  import fil.car.annuaire.AnnuaireItf

  object AnnuaireFactory {
    
    def squelette(port: Int, annuaire: AnnuaireItf) = new AnnuaireSkel(port, annuaire)
    
    def souche(ip: String, port: Int) = new AnnuaireStub(ip, port)
  }
}