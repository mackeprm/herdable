package de.mackeprm.herdable

import java.awt.Color
import java.awt.geom.Point2D
import kotlin.math.*


data class RGB(val r: Float, val g: Float, val b: Float)


fun rotate(point: Point2D.Float, degree: Float): Point2D.Float {
    val cosb = cos(Math.toRadians(degree.toDouble()))
    val sinb = sin(Math.toRadians(degree.toDouble()))

    val newX: Double = point.x * cosb - point.y * sinb
    val newY: Double = point.y * cosb + point.x * sinb

    return Point2D.Float(newX.toFloat(), newY.toFloat())
}

fun findDirection(startingPoint: Point2D.Float, targetPoint: Point2D.Float): Double {
    val centeredTarget = Point2D.Float(targetPoint.x - startingPoint.x, targetPoint.y - startingPoint.y)
    val angle = Math.toDegrees(atan2(centeredTarget.x.toDouble(), centeredTarget.y.toDouble()))
    //Correct the angle to match other things in this world:
    return ((180 - angle) + 270) % 360
}

class COLORS {
    companion object {
        val LIGHT_BLUE = Color(140, 184, 255)
        val DARK_BLUE = Color(8, 51, 78)
    }
}
