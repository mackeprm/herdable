package de.mackeprm.herdable

import java.awt.geom.Point2D
import kotlin.math.cos
import kotlin.math.sin

data class RGB(val r: Float, val g: Float, val b: Float)


fun rotate(point: Point2D.Float, degree: Float): Point2D.Float {
    val cosb = cos(Math.toRadians(degree.toDouble()))
    val sinb = sin(Math.toRadians(degree.toDouble()))

    val newX: Double = point.x * cosb - point.y * sinb
    val newY: Double = point.y * cosb + point.x * sinb

    return Point2D.Float(newX.toFloat(), newY.toFloat());
}
