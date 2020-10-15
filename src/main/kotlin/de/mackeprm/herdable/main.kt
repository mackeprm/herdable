package de.mackeprm.herdable

import processing.core.PApplet
import java.awt.geom.Point2D
import java.util.*


class Simulator : PApplet() {
    private val actors = ArrayList<Actor>()
    private val buttons = ArrayList<Button>()

    private var running = true

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
        actors.add(Herbivore(this, Point2D.Float(mouseX.toFloat(), mouseY.toFloat())))
    }
}

fun main(args: Array<String>) {
    Simulator.run(args)
}