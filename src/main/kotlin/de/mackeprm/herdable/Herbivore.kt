package de.mackeprm.herdable

import processing.core.PApplet
import java.awt.geom.Point2D
import kotlin.math.atan2
import kotlin.random.Random


enum class HerbivoreMode {
    IDLE, ROAMING, FORAGING, FLEEING, FOLLOWING
}

class Herbivore(private val sketch: PApplet, private var currentPos: Point2D.Float) : Actor {
    private var speed: Float = 10F
    private var direction: Float = 0F
    private val size = 15F
    private val random = Random.Default
    private var currentMode = HerbivoreMode.ROAMING

    init {
        //TODO baseSpeed, topSpeed
        speed = random.nextFloat() * 2
        direction = (random.nextFloat() * 360)
        println("New Herbivore: $speed, $direction")
    }

    private fun calculateNextTargetPoint(): Point2D.Float {
        val velocityVector = Point2D.Float(speed, speed)
        val newVector = rotate(velocityVector, direction)
        return Point2D.Float(
            currentPos.x + newVector.x,
            currentPos.y + newVector.y
        )
    }

    override fun step() {
        when (currentMode) {
            HerbivoreMode.ROAMING -> {
                direction = ((direction + (random.nextFloat() - 0.5F) * 10) % 360)
            }
            else -> throw IllegalStateException("Not Implemented")
        }
        var targetPos = calculateNextTargetPoint()
        while (outOfBounds(targetPos)) {
            direction += ((direction + (random.nextFloat() - 0.5F) * 180) % 360)
            targetPos = calculateNextTargetPoint()
        }
        currentPos = targetPos
    }

    private fun outOfBounds(targetPos: Point2D.Float) =
        targetPos.x > 1000 || targetPos.x < 0 || targetPos.y > 800 || targetPos.y < 0

    override fun render() {
        //TODO color should be set depending on the current mode
        sketch.fill(255F, 255F, 255F)
        sketch.ellipse(currentPos.x, currentPos.y, size, size)

        val targetPos = calculateNextTargetPoint()
        sketch.fill(255F, 0F, 0F)
        sketch.arrow(currentPos.x, currentPos.y, targetPos.x, targetPos.y)
    }

}

fun PApplet.arrow(x1: Float, y1: Float, x2: Float, y2: Float) {
    this.line(x1, y1, x2, y2)
    this.pushMatrix()
    this.translate(x2, y2)
    val a: Float = atan2(x1 - x2, y2 - y1)
    this.rotate(a)
    this.line(0F, 0F, -3F, -3F)
    this.line(0F, 0F, 3F, -3F)
    this.popMatrix()
}