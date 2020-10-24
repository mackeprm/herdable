package de.mackeprm.herdable

import processing.core.PApplet
import java.awt.Color
import java.awt.geom.Point2D
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.atan2
import kotlin.random.Random

enum class HerbivoreMode(val color: Color) {
    IDLE(Color.LIGHT_GRAY),
    ROAMING(COLORS.LIGHT_BLUE),
    FORAGING(Color.ORANGE),
    FLEEING(Color.RED),
    FOLLOWING(Color.GREEN)
}

class IdleBehaviour(private val startingPos: Point2D.Float) {
    var counter = 10

    init {
        // Calculate a random point within an area of the starting point.
        // This should be bound by the play-area.
    }

    fun step() {
        // TODO("Not yet implemented")
        counter--
    }

    fun getNextPos(): Point2D.Float {
        //TODO("Not yet implemented")
        return startingPos
    }

    fun isFinished(): Boolean {
        return counter <= 0;
    }

}
class HerbivoreMessage() {

}

class Herbivore(private val sketch: Simulator, private var currentPos: Point2D.Float) : Actor {
    private val id: UUID = UUID.randomUUID();
    private var speed: Float = 10F
    private var direction: Float = 0F
    private val size = 15F
    private val random = Random.Default
    private var currentMode = HerbivoreMode.ROAMING
    private var idleBehaviour: IdleBehaviour = IdleBehaviour(currentPos)
    private var idleCounter = 0;
    private var inbox: List<HerbivoreMessage> = ArrayList();

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
        //TODO inbox.
        val surroundingActors = sketch.getSurroundingActors(this)
        if(surroundingActors.size > 0) {
            println("I see somebody: $surroundingActors")
        }

        when (currentMode) {
            HerbivoreMode.ROAMING -> {
                direction = ((direction + (random.nextFloat() - 0.5F) * 10) % 360)
                currentPos = moveForward()
                idleCounter++;
                if (random.nextInt(idleCounter, 10_000) > 9900) {
                    currentMode = HerbivoreMode.IDLE;
                    idleBehaviour = IdleBehaviour(currentPos)
                }
            }
            HerbivoreMode.IDLE -> {
                if (idleBehaviour.isFinished()) {
                    idleCounter = 0;
                    if(random.nextInt(0, 10) > 3) {
                        currentMode = HerbivoreMode.ROAMING
                    } else {
                        idleBehaviour = IdleBehaviour(currentPos)
                    }
                } else {
                    idleBehaviour.step()
                    currentPos = idleBehaviour.getNextPos()
                }
            }
            else -> throw IllegalStateException("Not Implemented")
        }
    }

    private fun moveForward(): Point2D.Float {
        var targetPos = calculateNextTargetPoint()
        while (outOfBounds(targetPos)) {
            direction += ((direction + (random.nextFloat() - 0.5F) * 180) % 360)
            targetPos = calculateNextTargetPoint()
        }
        return targetPos
    }

    private fun outOfBounds(targetPos: Point2D.Float) =
        targetPos.x > 1000 || targetPos.x < 0 || targetPos.y > 800 || targetPos.y < 0

    override fun render() {
        sketch.fill(
            currentMode.color.red.toFloat(),
            currentMode.color.green.toFloat(),
            currentMode.color.blue.toFloat()
        )
        sketch.ellipse(currentPos.x, currentPos.y, size, size)

        val targetPos = calculateNextTargetPoint()
        sketch.fill(255F, 255F, 255F)
        sketch.stroke(255F, 255F, 255F)
        sketch.arrow(currentPos.x, currentPos.y, targetPos.x, targetPos.y)
    }

    override fun getCurrentPosition(): Point2D.Float {
        return currentPos;
    }

    override fun toString(): String {
        return "Herbivore(id=$id)"
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