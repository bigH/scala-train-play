/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

// Animal

abstract class Animal {

  def name: String

  def eat(food: SuitableFood): this.type =
    this

  type SuitableFood <: Food
}

case class Bird(name: String) extends Animal {

  type SuitableFood = Grains.type
}

case class Cow(name: String) extends Animal {

  type SuitableFood = Grass.type
}

case class Fish(name: String) extends Animal with Food {

  def swim(): Unit =
    ()

  type SuitableFood = Food {
    def swim(): Unit
  }
}

// Food

trait Food

case object Grains extends Food

case object Grass extends Food

// Feeding

object FeedingAnimals {

  def main(args: Array[String]): Unit = {

    val bill = Bird("Bill")
    bill eat Grains
    //    bill eat Grass // Shouldn't compile

    val cindy = Cow("Cindy")
    cindy eat Grass
    //    cindy eat Grains // Shouldn't compile

    val freddy = Fish("Freddy")
    freddy eat Fish("Frodo") eat Fish("Franky")
    //    freddy eat Grass
  }
}
