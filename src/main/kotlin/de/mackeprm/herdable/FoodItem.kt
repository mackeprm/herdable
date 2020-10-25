package de.mackeprm.herdable

import processing.core.PApplet
import java.awt.geom.Point2D

class FoodItem(private val sketch: PApplet, private val currentPos: Point2D.Float) : Actor {
    private val size = 10F

    override fun step() {
        //do nothing.
    }

    override fun render() {
        sketch.fill(255F, 0F, 0F)
        sketch.stroke(255F, 0F, 0F)
        sketch.rect((currentPos.x - (size / 2)), (currentPos.y - (size / 2)), size, size)
    }

    override fun getCurrentPosition(): Point2D.Float {
        return currentPos
    }

}