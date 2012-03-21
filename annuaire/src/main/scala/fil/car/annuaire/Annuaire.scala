package fil.car

object Annuaire {
  def impl = new AnnuaireImpl

  def skel(port: Int, annuaire: IAnnuaire) = new AnnuaireSkel(port, annuaire)

  def stub(ip: String, port: Int) = new AnnuaireStub(ip, port)
}