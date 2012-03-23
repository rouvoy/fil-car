package fil.car
import scala.collection.mutable.HashMap

class AnnuaireImpl extends IAnnuaire {
  var adresses = HashMap[String, Adresse]()

  def ajouter(adLogique: String, adPhysique: Adresse) =
    if (adresses contains adLogique)
      false
    else {
      adresses += adLogique -> adPhysique
      true
    }

  def retirer(adLogique: String) =
    if (adresses contains adLogique) {
      adresses -= adLogique
      true
    } else
      false

  def modifier(adLogique: String, adPhysique: Adresse) =
    if (adresses contains adLogique) {
      adresses += adLogique -> adPhysique
      true
    } else
      false

  def lister = adresses.keys

  def chercher(adLogique: String) =
    if (adresses contains adLogique)
      Some(adresses(adLogique))
    else
      None
}