package fil.car.annuaire {

  trait AnnuaireItf {
    
    def ajouter(adLogique: String, adPhysique: Adresse): Boolean

    def retirer(adLogique: String): Boolean

    def modifier(adLogique: String, adPhysique: Adresse): Boolean

    def lister: Iterable[String]

    def chercher(adLogique: String): Option[Adresse]
  }
}