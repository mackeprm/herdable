package de.mackeprm.herdable

import processing.core.PApplet

data class Point2D(val x: Float, val y: Float)

class Herbivore(private var sketch: PApplet, private var x: Float, private var y: Float) : Actor {
    var xSpeed: Float;
    var ySpeed: Float;
    var target: Point2D;

    init {
        xSpeed = sketch.random(-10.0F, 10.0F)
        ySpeed = sketch.random(-10.0F, 10.0F)
        target = Point2D(sketch.random(0F, sketch.width.toFloat()), sketch.random(0F, sketch.height.toFloat()))
    }


    override fun step() {
        //TODO: Are we at the target? If not, randomly walk towards it.
        x += xSpeed
        if (x < 0 || x > sketch.width) {
            xSpeed *= -1f
        }
        y += ySpeed
        if (y < 0 || y > sketch.height) {
            ySpeed *= -1f
        }
    }

    override fun render() {
        sketch.fill(255F, 0F, 0F);
        sketch.ellipse(target.x, target.y, 10F, 10F)
        sketch.fill(255F, 255F, 255F);
        sketch.ellipse(x, y, 10F, 10F)
    }

}