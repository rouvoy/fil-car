package fil.car

object Serveur extends Application {
  println("Serveur starting...")
  Annuaire.skel(9999, Annuaire.impl)
}