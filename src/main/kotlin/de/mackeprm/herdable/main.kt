package de.mackeprm.herdable

import processing.core.PApplet
import java.awt.geom.Point2D
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.reflect.KClass


class Simulator : PApplet() {
    private val actors = ConcurrentLinkedQueue<Actor>()
    private val buttons = ArrayList<Button>()

    private var running = true
    private var shiftPressed = false

    companion object Factory {
        fun run(args: Array<String>) {
            val art = Simulator()
            art.setSize(1200, 800)
            art.runSketch(args)
        }
    }


    override fun setup() {
        super.setup()
        frameRate(30F)
        buttons.add(Button(this, 1050, 200, "Start/Pause") {
            val running = this.running
            this.running = !running
        })
    }

    //TODO can i move this into it's own ActorList class?
    fun removeActor(actor: Actor) {
        this.actors.remove(actor)
    }

    //TODO can i move this into it's own ActorList class?
    //TODO can i get rid of clazz?
    fun <T : Actor> getSurroundingActors(actor: Actor, distance: Float, clazz: KClass<T>): List<T> {
        val currentActorPos = actor.getCurrentPosition()
        val result: ArrayList<T> = ArrayList()
        for (other in this.actors) {
            if (other != actor) {
                val otherActorPos = other.getCurrentPosition()
                val currentDistance = Point2D.distance(
                    currentActorPos.getX(), currentActorPos.getY(),
                    otherActorPos.getX(), otherActorPos.getY()
                )
                if (currentDistance <= distance) {
                    if (clazz.isInstance(other)) {
                        result.add(other as T)
                    }
                }
            }
        }
        return result
    }

    private fun drawMenu() {
        for (b in buttons) {
            b.render()
        }
    }

    private fun drawCanvas() {
        background(64)
        stroke(255)
        fill(255)
        rect(1000F, 0F, 200F, 1000F)
    }

    override fun draw() {
        drawCanvas()
        drawMenu()

        for (a in actors) {
            if (running) {
                a.step()
            }
            a.render()
        }
    }


    override fun mouseClicked() {
        for (b in buttons) {
            if (b.isClicked(this.mouseX, this.mouseY)) {
                b.action()
                return
            }
        }
        if (shiftPressed) {
            actors.add(FoodItem(this, Point2D.Float(mouseX.toFloat(), mouseY.toFloat())))
        } else {
            actors.add(Herbivore(this, Point2D.Float(mouseX.toFloat(), mouseY.toFloat())))
        }
    }

    override fun keyPressed() {
        if (this.key.toInt() == CODED) {
            if (this.keyCode == SHIFT) {
                this.shiftPressed = true
            }
        }
    }

    override fun keyReleased() {
        if (this.key.toInt() == CODED) {
            if (this.keyCode == SHIFT) {
                this.shiftPressed = false
            }
        }
    }
}

fun main(args: Array<String>) {
    Simulator.run(args)
}