package de.mackeprm.herdable

import processing.core.PApplet
import java.util.*


class Simulator : PApplet() {
    private val actors = ArrayList<Actor>()
    private var running = true

    companion object Factory {
        fun run() {
            val art = Simulator()
            art.setSize(500, 500)
            //art.balls.add(Ball(art, art.width / 2F, art.height / 2F))
            art.runSketch()
        }
    }


    override fun setup() {
        super.setup()
        size(500, 500)
    }

    override fun draw() {
        background(64)

        for (a in actors) {
            if (running) {
                a.step()
            }
            a.render()
        }

        stroke(255);
        rect(200F, 470F, 20F, 20F);
    }

    private fun overRect(x: Int, y: Int, width: Int, height: Int): Boolean {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
    }

    override fun mouseClicked() {
        if (overRect(200,470,20,20)) {
           running = !running;
        } else {
            actors.add(Herbivore(this, mouseX.toFloat(), mouseY.toFloat()))
        }
    }
}

fun main(args: Array<String>) {
    Simulator.run()
}