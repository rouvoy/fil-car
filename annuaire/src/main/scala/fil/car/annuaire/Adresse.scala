package fil.car.annuaire {

  case class Adresse(ip: String, port: Int) {
    def encode = ip + ":" + port
    
    override def toString = encode
  }

  object Adresse {
    def decode(valeur: String) = {
      val parts = valeur split ":"
      Adresse(parts(0), parts(1).toInt)
    }
  }
}